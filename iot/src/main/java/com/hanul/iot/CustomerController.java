package com.hanul.iot;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import customer.CustomerServiceImpl;
import customer.CustomerVO;

@Controller
public class CustomerController {
	@Autowired private CustomerServiceImpl service;
	
	//고객정보삭제처리 요청
	@RequestMapping("/delete.cu")
	public String delete(int id) {
		//해당 고객정보를 DB에서 삭제한다
		service.customer_delete(id);
		//목록화면으로 연결
		return "redirect:list.cu";
	}
	
	
	//고객정보변경저장처리 요청
	@RequestMapping("/update.cu")
	public String update(CustomerVO vo) {
		//화면에서 변경입력한 정보를 DB에 변경저장한다
		service.customer_update(vo);
		//상세화면으로 연결
		return "redirect:detail.cu?id=" + vo.getId();
	}
	
	
	//고객정보수정화면 요청
	@RequestMapping("/modify.cu")
	public String modify(int id, Model model) {
		//해당 고객정보를 DB에서 조회한다.
		//조회한 데이터를 수정화면에 출력한다
		model.addAttribute("vo", service.customer_detail(id));
		return "customer/modify";
	}
	
	//고객상세정보화면 요청
	@RequestMapping("/detail.cu")
	public String detail(Model model, int id) {
		//해당 고객정보를 DB에서 조회한다.
		//상세화면에 조회한 고객정보를 출력할 수 있도록 데이터를 담는다
		model.addAttribute("vo", service.customer_detail(id) );
		return "customer/detail";
	}
	
	
	//고객정보저장처리 요청
	@RequestMapping("/insert.cu")
	public String insert(CustomerVO vo) {
		//화면에서 입력한 고객정보를 DB에 저장한다
		service.customer_insert(vo);
		//목록화면으로 연결
		return "redirect:list.cu";
	}
	
	
	//신규고객입력화면 요청
	@RequestMapping("/new.cu")
	public String customer() {
		return "customer/new";
	}
	
	
	//고객목록화면 요청
	@RequestMapping("/list.cu")
	public String list(Model model, HttpSession session) {
		session.setAttribute("category", "cu");
		//DB에서 고객목록을 조회해와 목록화면에 출력할 수 있도록 한다
		model.addAttribute("list"
				, service.customer_list() );
		return "customer/list";
	}
	
}
