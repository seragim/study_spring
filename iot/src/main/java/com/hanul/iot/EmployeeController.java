package com.hanul.iot;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import employee.EmployeeServiceImpl;

@Controller
public class EmployeeController {
	@Autowired private EmployeeServiceImpl service;
	
	//사원정보 조회
	@RequestMapping("/detail.hr")
	public String detail(Model model, int id) {
		//선택한 사원의 정보를 조회해와 사원상세정보화면에 출력
		model.addAttribute("vo", service.employee_detail(id));
		return "employee/detail";
	}
	
	//사원목록 조회
	@RequestMapping("/list.hr")
	public String list(Model model, String depts, HttpSession session) {
		session.setAttribute("category", "hr");
		//사원이 소속된 부서정보 조회하여 사원상세정보화면에 출력
		model.addAttribute("depts", service.employee_department());
		//DB에서 사원목록을 조회해와 목록화면에 출력
		//부서를 선택한 경우 해당 부서에 속한 사원목록을 조회한다.
		//null: depts 파라미터가 없는 경우
		//all: depts 파라미터가 문자열인 전체인 경우
		//그 외는 숫자인 부서코드에 해당하는 경우
		if( depts == null || depts.equals("all") )
			model.addAttribute("list", service.employee_list() );
		else
			model.addAttribute("list", service.employee_list(depts) );

		model.addAttribute("dept_id", 
				(depts == null || depts.equals("all")) ? "all" : depts);
		return "employee/list";
	}
}






