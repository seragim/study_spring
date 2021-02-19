package com.hanul.iot;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.CommonService;

@Controller
public class DataController {

	//공공데이터 화면 요청
	@RequestMapping("/list.da")
	public String data(HttpSession session) {
		session.setAttribute("category", "da");
		return "data/list";
	}
	private String key 
	= "FPgj2NXbJw46TcGkmAfZEiYFDbxilys7KLjk3KaB7AfeJE00ZhPNM0M8unwbsI69fSmT8SNfVEimE6ZZ2U14hA%3D%3D"; 

	@Autowired private CommonService common;
	
	private String animalUrl 
	= "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/";
	
	//유기동물정보조회 요청
	@ResponseBody @RequestMapping(value="/data/animal/list"
							, produces="application/json; charset=utf-8")
	public String animal_list(@RequestBody HashMap<String, Object> map) {
		StringBuffer url = new StringBuffer( animalUrl + "abandonmentPublic");
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append( map.get("pageNo") );
		url.append("&numOfRows=").append( map.get("rows") );
		return common.json_list( common.requestAPI(url) );
	}
	
	
	//공공데이터 약국정보조회 요청
	@ResponseBody @RequestMapping(value="/data/pharmacy"
					, produces="application/json; charset=utf-8")
	public String pharmacy_list(int pageNo
								, @RequestParam(defaultValue = "10") int rows) {
		StringBuffer url = new StringBuffer(
				"http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList"
				);
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append(pageNo);
		url.append("&numOfRows=").append(rows);
		
		return common.json_list( common.requestAPI(url) );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}






