package com.hanul.iot;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import member.MemberServiceImpl;
import member.MemberVO;
import notice.NoticePage;
import notice.NoticeServiceImpl;
import notice.NoticeVO;

@Controller
public class NoticeController {
	@Autowired private NoticeServiceImpl service;
	
	//답글저장처리 요청
	@RequestMapping("/reply_insert.no")
	public String reply_insert(NoticeVO vo, MultipartFile file
								, HttpSession session) {
		//화면에서 입력한 답글정보를 DB에 저장한 후 목록화면으로 연결
		if( !file.isEmpty() ) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload(session, file, "notice") );
		}
		MemberVO user = (MemberVO)session.getAttribute("loginInfo");
		vo.setWriter( user.getId() );
		service.notice_reply_insert(vo);
		return "redirect:list.no";
	}
	
	
	
	//답글쓰기화면 요청
	@RequestMapping("/reply.no")
	public String reply(int id, Model model) {
		//답글저장시 원글의 정보가 필요하므로 원글정보를 조회
	 	model.addAttribute("vo", service.notice_view(id) );
		return "notice/reply";
	}
	
	
	
	//첨부파일 다운로드처리 요청
	@ResponseBody @RequestMapping("/download.no")
	public void download(int id, HttpSession session
							, HttpServletResponse response) {
		//해당 공지글에 첨부된 파일을 서버로부터 다운로드한다
		NoticeVO vo = service.notice_view(id);
		common.fileDownload(vo.getFilename(), vo.getFilepath(), session, response);
	}
	
	
	//공지글수정처리 요청
	@RequestMapping("/update.no")
	public String update(NoticeVO vo, MultipartFile file, String filename
						, HttpSession session) {
		NoticeVO notice = service.notice_view(vo.getId());
		String realFile = session.getServletContext().getRealPath("resources")
							+ "/" + notice.getFilepath();
		
		if( !file.isEmpty() ) { 
			//파일을 첨부한 경우: 원래 없었는데 신규 첨부, 원래 있었던거 바꿔 첨부
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload(session, file, "notice"));
			//원래 첨부된 파일이 있다면 물리적영역에서 파일을 삭제
			if( notice.getFilename()!=null ) {
				File f = new File(realFile);
				if( f.exists() ) f.delete();
			}
			
		}else {
			//파일을 첨부하지 않은 경우
			if( filename.isEmpty() ) {
				//원래 첨부된 파일을 삭제한 경우
				if( notice.getFilename()!=null ) {
					File f = new File(realFile);
					if( f.exists() ) f.delete();
				}
				
			}else {
				//원래 첨부된 파일을 그대로 사용하는 경우
				vo.setFilename( notice.getFilename() );
				vo.setFilepath( notice.getFilepath() );
			}
		}
		
		//화면에서 변경입력한 정보를 DB에 저장한 후 보기화면으로 연결
		service.notice_update(vo);
		return "redirect:view.no?id=" + vo.getId() ;
	}
	
	//공지글수정화면 요청
	@RequestMapping("/modify.no")
	public String modify(int id, Model model) {
		//해당 공지글을 DB에서 조회해와 수정화면에 출력
		model.addAttribute("vo", service.notice_view(id) );
		return "notice/modify";
	}
	
	
	//공지글삭제처리 요청
	@RequestMapping("/delete.no")
	public String delete(int id) {
		//해당 공지글을 DB에서 삭제한 후 목록화면으로 연결
		service.notice_delete(id);
		return "redirect:list.no";
	}
	
	
	//공지글상세보기화면 요청
	@RequestMapping("/view.no")
	public String view(Model model, int id) {
		//조회수 증가처리
		service.notice_read(id);
		
		//선택한 공지글정보를 DB에서 조회한 후 상세보기화면에 출력할 수 있도록 model에 담는다
		model.addAttribute("vo", service.notice_view(id) );
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("page", page);
		return "notice/view";
	}
	
	@Autowired private CommonService common;
	
	//신규공지글 저장처리 요청
	@RequestMapping("/insert.no")
	public String insert(NoticeVO vo, MultipartFile file, HttpSession session) {
		MemberVO member = (MemberVO)session.getAttribute("loginInfo");
		vo.setWriter(  member.getId()  );
		//첨부된 파일이 있다면 데이터객체에 파일정보를 담는다
		if( ! file.isEmpty() ) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload(session, file, "notice") );
		}
		//화면에서 입력한 정보를 DB에 저장한 후 목록화면으로 연결
		service.notice_insert(vo);
		return "redirect:list.no";
	}
	
	
	//공지글 신규화면 요청
	@RequestMapping("/new.no")
	public String notice() {
		return "notice/new";
	}
	
	@Autowired private MemberServiceImpl member;
	@Autowired private NoticePage page;
	
	//공지글목록 조회
	@RequestMapping("/list.no")
	public String list(Model model, HttpSession session
						, @RequestParam(defaultValue = "1") int curPage
						, String search, String keyword
						) {
//		//임시저장 -----
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("id", "admin");
//		map.put("pw", "manager");
//		MemberVO vo = member.member_login(map);
//		session.setAttribute("loginInfo", vo);
//		//------------
		
		session.setAttribute("category", "no");
		//DB에서 공지글목록을 조회한후 목록화면에 출력
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		model.addAttribute("page", service.notice_list(page) );
		return "notice/list";
	}
}






