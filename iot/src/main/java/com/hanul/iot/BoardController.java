package com.hanul.iot;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import board.BoardCommentVO;
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
	
	//방명록 댓글삭제처리 요청
	@ResponseBody @RequestMapping("/board/comment/delete/{id}")
	public void comment_delete(@PathVariable int id) {
		service.board_comment_delete(id);
	}
	
	
	//방명록 댓글목록 조회 요청
	@RequestMapping("/board/comment/{pid}")
	public String comment_list(@PathVariable int pid, Model model) {
		model.addAttribute("list", service.board_comment_list(pid) );
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "board/comment/comment_list";
	}
	
	//방명록 댓글변경저장처리 요청
	@ResponseBody @RequestMapping(value="/board/comment/update"
							, produces="application/text; charset=utf-8")
	public String comment_update( @RequestBody BoardCommentVO vo) {
		//화면에서 변경입력한 정보를 DB에 저장한 후 호출한 곳으로 간다
		return service.board_comment_update(vo) > 0 ? "성공^^" : "실패ㅠㅠ";
	}
	
	
	
	
	//방명록 댓글 저장처리 요청
	@ResponseBody @RequestMapping("/board/comment/insert")
	public boolean comment_insert(BoardCommentVO vo, HttpSession session) {
		//화면에서 입력한 댓글정보를 DB에 저장한 후 
		MemberVO user = (MemberVO)session.getAttribute("loginInfo");
		vo.setWriter( user.getId() );
		return service.board_comment_insert(vo) > 0 ? true : false;
	}
	
	
	//방명록 삭제 처리 요청
	@RequestMapping("/delete.bo")
	public String delete(int id, HttpSession session, Model model) {
		//첨부파일이 있는 글의 경우 물리적 서버의 영역에서 파일을 삭제
		BoardVO vo = service.board_view(id);
		if( vo.getFilename()!=null  ) {
			File file = new File( session.getServletContext()
									.getRealPath("resources")
											+ "/" + vo.getFilepath() );
			if( file.exists() ) file.delete();
		}
		//선택한 글을 DB에서 삭제한 후 목록화면으로 연결
		service.board_delete(id);
		
		model.addAttribute("page", page);
		model.addAttribute("url", "list.bo");
		return "board/redirect";
//		return "redirect:list.bo";
	}
	
	//첨부파일 다운로드 요청
	@ResponseBody @RequestMapping("/download.bo")
	public void download(int id, HttpSession session
							, HttpServletResponse response) {
		BoardVO vo = service.board_view(id);
		common.fileDownload( vo.getFilename(), vo.getFilepath(), session, response );
	}
	
	//방명록 수정 저장처리 요청
	@RequestMapping("/update.bo")
	public String update(BoardVO vo, String attach
							, HttpSession session
							, MultipartFile file, Model model) {
		
		BoardVO board = service.board_view( vo.getId() );
		String uuid = session.getServletContext().getRealPath("resources")
						+ "/" + board.getFilepath();
		//첨부파일 관련처리
		if( ! file.isEmpty() ) { //첨부파일 있는 경우
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload(session, file, "board") );
			
			//원래 첨부된 파일이 있었다면 서버에서 삭제
			if( board.getFilename() != null ) {
				File f = new File( uuid );
				if( f.exists() ) f.delete();
			}
		}else {
			//원래 첨부된 파일을 삭제/ 원래부터 첨부파일이 없는 경우
			if( attach.isEmpty() ) {
				if( board.getFilename() != null ) {
					File f = new File( uuid );
					if( f.exists() ) f.delete();
				}
				
			}else {
				//원래 첨부된 파일을 그대로 사용하는 경우
				vo.setFilename( board.getFilename() );
				vo.setFilepath( board.getFilepath() );
			}
		}
		
		//화면에서 입력한 정보를 DB에 변경저장한 후 보기화면으로 연결
		service.board_update(vo);
		
		model.addAttribute("url", "view.bo");
		model.addAttribute("id", vo.getId());
		return "board/redirect";
	}
	
	
	
	//방명록 수정화면 요청
	@RequestMapping("/modify.bo")
	public String modify(int id, Model model) {
		//해당 글의 정보를 DB에서 조회해와 수정화면에 출력
		model.addAttribute("vo", service.board_view(id));
		return "board/modify";
	}
	
	
	//방명록 글내용상세화면 요청
	@RequestMapping("/view.bo")
	public String view(int id, Model model) {
		service.board_read(id);
		//선택한 글의 정보를 DB에서 조회해와 보기화면에 출력
		model.addAttribute("vo", service.board_view(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		model.addAttribute("page", page);
		return "board/view";
	}
	
	
	//방명록 글저장처리 요청
	@RequestMapping("/insert.bo")
	public String insert(BoardVO vo, MultipartFile file, HttpSession session) {
		//화면에서 입력한 정보를 DB에 저장한 후 목록화면으로 연결
		if( ! file.isEmpty() ) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload(session, file, "board") );
		}
		MemberVO user = (MemberVO)session.getAttribute("loginInfo");
		vo.setWriter( user.getId() );
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
	public String list(HttpSession session, Model model
						, String search, String keyword
						, @RequestParam(defaultValue="10") int pageList
						, @RequestParam(defaultValue="list") String viewType
						, @RequestParam(defaultValue="1") int curPage ) {
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







