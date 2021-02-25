package com.hanul.iot;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.LowerKeyMap;
import visual.VisualServiceImpl;

@Controller
public class VisualizationController {
	@Autowired private VisualServiceImpl service;
	
	//부서별 사원수 조회 요청
	@ResponseBody @RequestMapping("/visual/department")
	public List<LowerKeyMap> department_analysis() {
		return service.department_analysis();
	}

	@RequestMapping("/list.vi")
	public String list(HttpSession session) {
		session.setAttribute("category", "vi");
		return "visual/list";
	}
	
}
