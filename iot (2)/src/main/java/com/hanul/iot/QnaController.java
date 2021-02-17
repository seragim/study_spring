package com.hanul.iot;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import member.MemberVO;
import qna.QnaPage;
import qna.QnaServiceImpl;
import qna.QnaVO;

@Controller
public class QnaController {
	@Autowired private QnaServiceImpl service;
	@Autowired private QnaPage page;
	@Autowired private CommonService common;
	
	//질문글 삭제처리 요청
	@RequestMapping("/delete.qa")
	public String delete(int id) {
		service.qna_delete(id);
		return "redirect:list.qa";
	}
	
	//질문글 수정처리 요청
	@RequestMapping("/update.qa")
	public String update(QnaVO vo) {
		service.qna_update(vo);
		return "redirect:view.qa?id=" + vo.getId();
	}
	
	//질문글 수정화면 요청
	@RequestMapping("/modify.qa")
	 public String modify(int id, Model model) {
		model.addAttribute("vo", service.qna_view(id));
		 return "qna/modify";
	 }
	
	//질문글 보기화면 요청
	@RequestMapping("/view.qa")
	public String view(Model model, int id) {
		model.addAttribute("vo", service.qna_view(id));
		return "qna/view";
	}
	
	//질문글 저장처리 요청
	@RequestMapping("/insert.qa")
	public String insert(QnaVO vo, HttpSession session, MultipartFile file) {
		if(!file.isEmpty()) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload(session, file, "qna") );
		}
		MemberVO user = (MemberVO)session.getAttribute("loginInfo");
		vo.setWriter(user.getId());
		service.qna_insert(vo);
		return "redirect:list.qa";
	}
	
	//질문글 신규작성화면 요청
	@RequestMapping("/new.qa")
	public String qna() {
		return "qna/new";
	}

	//질문게시판 글목록 조회
	@RequestMapping("/list.qa")
	public String list(HttpSession session, Model model,@RequestParam(defaultValue="1") int curPage, String search, String keyword) {
		session.setAttribute("category", "qa");
		
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		
		model.addAttribute("page", service.qna_list(page));
		
		return "qna/list";
	}
}
