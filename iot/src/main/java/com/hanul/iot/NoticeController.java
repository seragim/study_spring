package com.hanul.iot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {

	//�����۸�� ��ȸ
	@RequestMapping("/list.no")
	public String list() {
		return "notice/list";
	}
}






