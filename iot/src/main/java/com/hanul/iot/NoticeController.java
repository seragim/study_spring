package com.hanul.iot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {

	//공지글목록 조회
	@RequestMapping("/list.no")
	public String list() {
		return "notice/list";
	}
}






