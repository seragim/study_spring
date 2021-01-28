package com.hanul.iot;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.CommonService;
import member.MemberServiceImpl;
import member.MemberVO;

@Controller
public class MemberController {
	@Autowired private MemberServiceImpl service;
	
	//아이디 중복확인
	@ResponseBody @RequestMapping("/id_check")
	public boolean id_check(String id) {
		return service.member_id_check(id);
	}
	
	//회원가입화면 요청
	@RequestMapping("/member")
	public String member(HttpSession session) {
		session.setAttribute("category", "join");
		return "member/join";
	}
	
	private String naver_client_id = "jPxaVjEk_r6x4Um2qAq_";
	private String kakao_client_id = "b65584860d08a47acb4e4e6ba518f2fd";
	
	//카카오로그인 화면 요청
	@RequestMapping("/kakaologin")
	public String kakaologin(HttpSession session) {
		// https://kauth.kakao.com/oauth/authorize?client_id={REST_API_KEY}
		//&redirect_uri={REDIRECT_URI}
		//&response_type=code
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);
		
		StringBuffer url = new StringBuffer(
			"https://kauth.kakao.com/oauth/authorize?response_type=code");
		url.append("&client_id=").append( kakao_client_id );
		url.append("&state=").append(state);
		url.append("&redirect_uri=").append("http://localhost/iot/kakaocallback");
		
		return "redirect:" + url.toString();
	}
	
	@RequestMapping("/kakaocallback")
	public String kakaocallback(HttpSession session, String state
								, String code, String error) {
		if( !state.equals( (String)session.getAttribute("state") ) 
				|| error!=null )
			return "redirect:/";
		
		//��ū �߱޹ޱ�
		StringBuffer url = new StringBuffer(
			"https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
		url.append("&client_id=").append(kakao_client_id);
//		url.append("&client_secret=").append("K1N91CKhB2");
		url.append("&code=").append(code);
		url.append("&state=").append(state);
	
		JSONObject json = new JSONObject( common.requestAPI(url) );
		String token_type = json.getString("token_type");
		String access_token = json.getString("access_token");
		
//		curl -v -X POST "https://kauth.kakao.com/oauth/token" \
//		 -d "grant_type=authorization_code" \
//		 -d "client_id={REST_API_KEY}" \
//		 -d "redirect_uri={REDIRECT_URI}" \
//		 -d "code={AUTHORIZATION_CODE}
	
//		curl -v -X GET "https://kapi.kakao.com/v2/user/me" \
//		  -H "Authorization: Bearer {ACCESS_TOKEN}"
		
		//��������� ��������
		url = new StringBuffer("https://kapi.kakao.com/v2/user/me");
		json = new JSONObject(
				common.requestAPI(url, token_type+" "+access_token) );
		if( !json.isEmpty() ) {
			MemberVO vo = new MemberVO();
			vo.setSocial_type("kakao");
			vo.setId( json.get("id").toString() );
			
			json = json.getJSONObject("kakao_account");
			vo.setSocial_email( json.getString("email"));
			String gender 
			= json.has("gender") ? json.getString("gender") : "male";
			vo.setGender( gender.equals("female") ? "��" : "��" );
		
			json = json.getJSONObject("profile");
			vo.setName( json.getString("nickname") );
			//īī�� �α��� ������ DB�� ������ update, ������ insert
			
			if( service.member_social_id(vo) ) { //id�� ������ update
				service.member_social_update(vo);
			}else { //id�� ������ insert
				service.member_social_insert(vo);
			}
			session.setAttribute("loginInfo", vo);
		}
		return "redirect:/";
	}
	
	//네이버 로그인 화면 요청
	@RequestMapping("/naverlogin")
	public String naverlogin(HttpSession session) {
		//https://nid.naver.com/oauth2.0/authorize?
		//response_type=code&client_id=CLIENT_ID
		//&state=STATE_STRING&redirect_uri=CALLBACK_URL
		
		//UUID �� �������ڸ� ����
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);
		
		StringBuffer url = new StringBuffer(
			"https://nid.naver.com/oauth2.0/authorize?response_type=code");
		url.append("&client_id=").append(naver_client_id);
		url.append("&state=").append(state);
		url.append("&redirect_uri=").append("http://localhost/iot/navercallback");
		
		
		return "redirect:" + url.toString();
	}
	
	@Autowired private CommonService common;
	
	@RequestMapping("/navercallback")
	public String navercallback(HttpSession session, String state
								, String code, String error) {
		//���� ��ū�� ��ġ���� �ʰų� �ݹ���з� ���� �߻��� ��ū�� �߱޹��� �� ���� --> Ȩ����
		if( !state.equals((String)session.getAttribute("state"))
				|| error!=null ) return "redirect:/";
		//����ó��: code ���� ����
		//������ū�� �߱޹ޱ� ���� ��û
		//https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
		//&client_id=?&client_secret=?&code=?&state=? 
		StringBuffer url = new StringBuffer(
			"https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append(naver_client_id);
		url.append("&client_secret=").append("q0uZ2EMIne");
		url.append("&code=").append(code);
		url.append("&state=").append(state);
		JSONObject json = new JSONObject( common.requestAPI(url) );
		String access_token = json.getString("access_token");
		String token_type = json.getString("token_type");
		
		//����� ���������� ��ȸ
		//��ûURL: https://openapi.naver.com/v1/nid/me
		//��û���: Authorization: {��ū Ÿ��] {���� ��ū]
		
		url = new StringBuffer("https://openapi.naver.com/v1/nid/me");
		json = new JSONObject( common.requestAPI(url, token_type+" "+access_token) );
		//resultcode, message, response
		
		if( json.getString("resultcode").equals("00")) {
			json = json.getJSONObject("response");
			//email, nickname, ...
			MemberVO vo = new MemberVO();
			vo.setSocial_type("naver");
			vo.setId(json.getString("id"));
			vo.setGender( json.has("gender") 
					    ? ( json.getString("gender").equals("F") ? "��" :"��" ) 
					    : "��");
			vo.setName( json.has("nickname") 
					  ? json.getString("nickname")  
//					  ? ( json.getString("nickname").isEmpty() 
//							  ? json.getString("name") : json.getString("nickname"))
					  : json.getString("name"));
			vo.setSocial_email( json.getString("email") );
			//{  "gender": "F", "nickname": "������"}
			//{ }
			
			
			//���̹��α����� ó���̶�� insert�ϰ�, �ƴϸ� update
			//�ش� ���̹����̵� �����ϴ����� ���� �ľ�
			if( service.member_social_id(vo) ) { //���̵� �����
				service.member_social_update(vo);
			}else {
				service.member_social_insert(vo);	
			}
			session.setAttribute("loginInfo", vo);
		}
		return "redirect:/";
	}
	
	
	
	//로그아웃 화면 요청
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		
		String social 
		= ((MemberVO)session.getAttribute("loginInfo")).getSocial_type();
		session.removeAttribute("loginInfo");
		
//		GET /oauth/logout
		//?client_id=?
		//&logout_redirect_uri=?
		//&state=? HTTP/1.1
//				Host: kauth.kakao.com
		//īī���α����� ��� īī�������� �Բ� �α׾ƿ��ǰ� ����
		if( social!=null && social.equals("kakao") ) {
			StringBuffer url = new StringBuffer(
					"https://kauth.kakao.com/oauth/logout"); 
			url.append("?client_id=").append(kakao_client_id);
			url.append("&logout_redirect_uri=")
						.append("http://localhost/iot");
			return "redirect:" + url.toString();
		}else
			return "redirect:/";
	}
	
	//IoT 자체 로그인
	@ResponseBody @RequestMapping("/iotlogin")
	public boolean login(String id, String pw, HttpSession session) {
		//ȭ�鿡�� �Է��� ���̵�/����� ��ġ�ϴ� ȸ�������� ��ȸ�ؿ´�
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pw", pw);
		MemberVO vo = service.member_login(map);
		//�α����� ȸ�������� ���ǿ� ��Ƶд�
		session.setAttribute("loginInfo", vo);
		return vo==null ? false : true;
	}
	
	//로그인 화면 요청
	@RequestMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("category", "login");
		return "member/login";
	}
	
	
	
	
	
	
	
}
