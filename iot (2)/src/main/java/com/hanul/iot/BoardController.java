package com.hanul.iot;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import board.BoardPage;
import board.BoardServiceImpl;
import board.BoardVO;
import common.CommonService;
import member.MemberVO;

@Controller
public class BoardController {
	@Autowired private BoardServiceImpl service;
	@Autowired private BoardPage page;
	@Autowired private CommonService common;
	
	//첨부파일 다운로드 요청
	@ResponseBody @RequestMapping("/download.bo")
	public void download(int id, HttpSession session, HttpServletResponse response) {
		BoardVO vo = service.board_view(id);
		common.fileDownload(vo.getFilename(), vo.getFilepath(), session, response);
	}
	
	//방명록 글내용상세화면 요청
	@RequestMapping("/view.bo")
	public String view(int id, Model model) {
		service.board_read(id);
		//선택한 글의 정보를 DB에서 조회해와 보기화면에 출력
		model.addAttribute("vo", service.board_view(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "board/view";
	}
	
	//방명록 글저장처리 요청
	@RequestMapping("/insert.bo")
	public String insert(BoardVO vo, MultipartFile file, HttpSession session) {
		//화면에서 입력한 정보를 DB에 저장한 후 목록화면으로 연결
		if( !file.isEmpty() ) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload(session, file, "board") );
		}
		MemberVO user = (MemberVO)session.getAttribute("loginInfo");
		vo.setWriter(user.getId());
		service.board_insert(vo);
		return "redirect:list.bo";
	}
	
	//방명록 글쓰기 화면 요청
	@RequestMapping("/new.bo")
	public String board() {
		return "board/new";
	}

	//방명록 목록 조회 요청
	@RequestMapping("/list.bo")
	public String list(HttpSession session, Model model, String search, String keyword ,
			@RequestParam(defaultValue ="1") int curPage,@RequestParam(defaultValue ="10") int pageList, @RequestParam(defaultValue ="list") String viewType) {
		session.setAttribute("category", "bo");
		//DB에서 방명록 목록을 조회해와 목록화면에 출력
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		page.setPageList(pageList);
		page.setViewType(viewType);
		model.addAttribute("page", service.board_list(page));
		
		return "board/list";
	}
	
}
