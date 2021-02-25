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
	
	//TOP3부서의 년도별 채용인원수조회 요청
	@ResponseBody @RequestMapping("/visual/top3/year")
	public List<LowerKeyMap> hirement_analysis_top3_year(){
		return service.hirement_analysis_top3_year();
	}
	
	//TOP3부서의 월별 채용인원수조회 요청
	@ResponseBody @RequestMapping("/visual/top3/month")
	public List<LowerKeyMap> hirement_analysis_top3_month(){
		return service.hirement_analysis_top3_month();
	}
	
	//년도별 채용인원수조회 요청
	@ResponseBody @RequestMapping("/visual/year")
	public List<LowerKeyMap> hirement_analysis_year(){
		return service.hirement_analysis_year();
	}
	
	//월별 채용인원수조회 요청
	@ResponseBody @RequestMapping("/visual/month")
	public List<LowerKeyMap> hirement_analysis_month(){
		return service.hirement_analysis_month();
	}

	
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
