package com.hanul.iot;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import qna.QnaPage;
import qna.QnaServiceImpl;

@Controller
public class QnaController {

	//질문게시판 이동
	@RequestMapping("/list.qa")
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "qa");
		
		return "qna/list";
	}
}
