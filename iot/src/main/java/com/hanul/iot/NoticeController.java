package com.hanul.iot;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import member.MemberServiceImpl;
import member.MemberVO;
import notice.NoticeServiceImpl;
import notice.NoticeVO;

@Controller
public class NoticeController {
	@Autowired private NoticeServiceImpl service;
	
	//공지글 상세보기화면 요청
	@RequestMapping("/view.no")
	public String view(Model model, int id) {
		//선택한 공지글 정보를 DB에서 조회한 후 상세보기화면에 출력할 수 있도록 model에 데이터를 담는다
		model.addAttribute("vo", service.notice_view(id));
		model.addAttribute("crlf", "\r\n");
		return "notice/view";
	}
	
	//신규공지글 저장처리 요청
	@RequestMapping("/insert.no")
	public String insert(NoticeVO vo, HttpSession session) {
		MemberVO member = (MemberVO)session.getAttribute("loginInfo");
		vo.setWriter(member.getId());
		//화면에서 입력한 정보를 DB에 저장한 후 목록화면연결
		service.notice_insert(vo);
		return "redirect:list.no";
	}
	
	//공지글 신규화면 요청
	@RequestMapping("/new.no")
	public String notice() {
		return "notice/new";
	}
	
	@Autowired private MemberServiceImpl member;

	//공지글 목록 조회
	@RequestMapping("/list.no")
	public String list(Model model, HttpSession session) {
		//임시저장 ------
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "admin");
		map.put("pw", "manager");
		MemberVO vo = member.member_login(map);
		session.setAttribute("loginInfo", vo);
		//---------------
		
		session.setAttribute("category", "no");
		//DB에서 공지글 목록조회한 후 화면에 출력
		model.addAttribute("list", service.notice_list());
		return "notice/list";
	}
}






