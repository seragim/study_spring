package com.hanul.iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import notice.NoticeServiceImpl;

@Controller
public class NoticeController {
	@Autowired private NoticeServiceImpl service;

	//공지글 목록 조회
	@RequestMapping("/list.no")
	public String list(Model model) {
		//DB에서 공지글 목록조회한 후 화면에 출력
		model.addAttribute("list", service.notice_list());
		return "notice/list";
	}
}






