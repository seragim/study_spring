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
	
	
	//유기동물 시도조회 요청
	@ResponseBody @RequestMapping(value="/data/animal/sido"
					, produces="application/json; charset=utf-8")
	public Object animal_sido() {
		StringBuffer url = new StringBuffer( animalUrl + "sido" );
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&numOfRows=30");
		return common.json_list( common.requestAPI(url) );
	}
	
	
	//유기동물정보조회 요청
	@ResponseBody @RequestMapping(value="/data/animal/list"
							, produces="application/json; charset=utf-8")
	public Object animal_list(@RequestBody HashMap<String, Object> map) {
		StringBuffer url = new StringBuffer( animalUrl + "abandonmentPublic");
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append( map.get("pageNo") );
		url.append("&numOfRows=").append( map.get("rows") );
		url.append("&upr_cd=").append( map.get("sido") );
		url.append("&upkind=").append( map.get("upkind") );
		url.append("&kind=").append( map.get("kind") );
		url.append("&org_cd=").append( map.get("sigungu") );
		url.append("&care_reg_no=").append( map.get("shelter") );
		return common.json_list( common.requestAPI(url) );
	}
	
	//유기동물 시도에 따른 시군구조회 요청
	@ResponseBody @RequestMapping(value="data/animal/sigungu"
					, produces="application/json; charset=utf-8")
	public Object animal_sigungu(String sido) {
		StringBuffer url = new StringBuffer( animalUrl + "sigungu" );
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&upr_cd=").append(sido);
		return common.json_list(common.requestAPI(url));
	}
	
	//유기동물 시군구에 따른 보호소조회 요청
	@ResponseBody @RequestMapping(value="/data/animal/shelter"
			, produces="application/json; charset=utf-8")
	public Object animal_shelter(String sido, String sigungu) {
		StringBuffer url = new StringBuffer( animalUrl + "shelter" );
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&upr_cd=").append(sido);
		url.append("&org_cd=").append(sigungu);
		return common.json_list( common.requestAPI(url) );
	}
	
	
	//유기동물 축종에 따른 품종조회 요청
	@ResponseBody @RequestMapping(value="/data/animal/animal_kind"
					, produces="application/json; charset=utf-8")
	public Object animal_kind(String upkind){
		StringBuffer url = new StringBuffer( animalUrl + "kind" );
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&up_kind_cd=").append(upkind);
		return common.json_list(common.requestAPI(url));
	}
	
	
	
	//공공데이터 약국정보조회 요청
	@ResponseBody @RequestMapping(value="/data/pharmacy"
					, produces="application/json; charset=utf-8")
	public Object pharmacy_list(int pageNo
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






