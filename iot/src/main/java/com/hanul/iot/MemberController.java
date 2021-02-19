package com.hanul.iot;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
	
	//회원가입처리 요청
	@ResponseBody @RequestMapping(value="/join"
						, produces="text/html; charset=utf-8")
	public String join(MemberVO vo, HttpSession session, HttpServletRequest request) {
		//화면에서 입력한 회원정보를 DB에 저장한 후 홈으로 연결
		StringBuffer msg = new StringBuffer("<script>");
		if( service.member_join(vo) ) {
			common.sendEmail(session, vo.getEmail(), vo.getName());
			msg.append("alert('회원가입을 축하합니다 ^^'); location='"+
									 request.getContextPath() + "'; ");
//			msg.append("alert('회원가입을 축하합니다 ^^'); location='index'; ");
		}else {
			msg.append("alert('회원가입 실패 ㅠㅠ'); history.go(-1)");
		}
		msg.append("</script>");
		return msg.toString();
	}
	
	
	//아이디중복확인
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
	
	
	
	private String naver_client_id = "ivF0qwh3gHvUWgYSrJUU";
	private String kakao_client_id = "01693bb3e82585002e7051c6a6346c05";
	
	//카카오로그인요청
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
		
		//토큰 발급받기
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
		
		//사용자정보 가져오기
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
			vo.setGender( gender.equals("female") ? "여" : "남" );
		
			json = json.getJSONObject("profile");
			vo.setName( json.getString("nickname") );
			//카카오 로그인 정보가 DB에 있으면 update, 없으면 insert
			
			if( service.member_social_id(vo) ) { //id가 있으면 update
				service.member_social_update(vo);
			}else { //id가 없으면 insert
				service.member_social_insert(vo);
			}
			session.setAttribute("loginInfo", vo);
		}
		return "redirect:/";
	}
	
	//네이버로그인요청
	@RequestMapping("/naverlogin")
	public String naverlogin(HttpSession session) {
		//https://nid.naver.com/oauth2.0/authorize?
		//response_type=code&client_id=CLIENT_ID
		//&state=STATE_STRING&redirect_uri=CALLBACK_URL
		
		//UUID 로 랜덤문자를 생성
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
		//상태 토큰이 일치하지 않거나 콜백실패로 에러 발생시 토큰을 발급받을 수 없다 --> 홈으로
		if( !state.equals((String)session.getAttribute("state"))
				|| error!=null ) return "redirect:/";
		//정상처리: code 값이 있음
		//접근토큰을 발급받기 위한 요청
		//https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
		//&client_id=?&client_secret=?&code=?&state=? 
		StringBuffer url = new StringBuffer(
			"https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append(naver_client_id);
		url.append("&client_secret=").append("K1N91CKhB2");
		url.append("&code=").append(code);
		url.append("&state=").append(state);
		JSONObject json = new JSONObject( common.requestAPI(url) );
		String access_token = json.getString("access_token");
		String token_type = json.getString("token_type");
		
		//사용자 프로필정보 조회
		//요청URL: https://openapi.naver.com/v1/nid/me
		//요청헤더: Authorization: {토큰 타입] {접근 토큰]
		
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
					    ? ( json.getString("gender").equals("F") ? "여" :"남" ) 
					    : "남");
			vo.setName( json.has("nickname") 
					  ? json.getString("nickname")  
//					  ? ( json.getString("nickname").isEmpty() 
//							  ? json.getString("name") : json.getString("nickname"))
					  : json.getString("name"));
			vo.setSocial_email( json.getString("email") );
			//{  "gender": "F", "nickname": "가나다"}
			//{ }
			
			
			//네이버로그인인 처음이라면 insert하고, 아니면 update
			//해당 네이버아이디가 존재하는지를 먼저 파악
			if( service.member_social_id(vo) ) { //아이디 존재시
				service.member_social_update(vo);
			}else {
				service.member_social_insert(vo);	
			}
			session.setAttribute("loginInfo", vo);
		}
		return "redirect:/";
	}
	
	
	
	//로그아웃처리 요청
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
		//카카오로그인인 경우 카카오계정도 함께 로그아웃되게 하자
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
	
	//IoT 자체 로그인처리 요청
	@ResponseBody @RequestMapping("/iotlogin")
	public boolean login(String id, String pw, HttpSession session) {
		//화면에서 입력한 아이디/비번이 일치하는 회원정보를 조회해온다
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pw", pw);
		MemberVO vo = service.member_login(map);
		//로그인한 회원정보를 세션에 담아둔다
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
