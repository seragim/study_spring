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
	
	//����������ó�� ��û
	@RequestMapping("/delete.cu")
	public String delete(int id) {
		//�ش� �������� DB���� �����Ѵ�
		service.customer_delete(id);
		//���ȭ������ ����
		return "redirect:list.cu";
	}
	
	
	//��������������ó�� ��û
	@RequestMapping("/update.cu")
	public String update(CustomerVO vo) {
		//ȭ�鿡�� �����Է��� ������ DB�� ���������Ѵ�
		service.customer_update(vo);
		//��ȭ������ ����
		return "redirect:detail.cu?id=" + vo.getId();
	}
	
	
	//����������ȭ�� ��û
	@RequestMapping("/modify.cu")
	public String modify(int id, Model model) {
		//�ش� �������� DB���� ��ȸ�Ѵ�.
		//��ȸ�� �����͸� ����ȭ�鿡 ����Ѵ�
		model.addAttribute("vo", service.customer_detail(id));
		return "customer/modify";
	}
	
	//��������ȭ�� ��û
	@RequestMapping("/detail.cu")
	public String detail(Model model, int id) {
		//�ش� �������� DB���� ��ȸ�Ѵ�.
		//��ȭ�鿡 ��ȸ�� �������� ����� �� �ֵ��� �����͸� ��´�
		model.addAttribute("vo", service.customer_detail(id) );
		return "customer/detail";
	}
	
	
	//����������ó�� ��û
	@RequestMapping("/insert.cu")
	public String insert(CustomerVO vo) {
		//ȭ�鿡�� �Է��� �������� DB�� �����Ѵ�
		service.customer_insert(vo);
		//���ȭ������ ����
		return "redirect:list.cu";
	}
	
	
	//�ű԰��Է�ȭ�� ��û
	@RequestMapping("/new.cu")
	public String customer() {
		return "customer/new";
	}
	
	
	//�����ȭ�� ��û
	@RequestMapping("/list.cu")
	public String list(Model model, HttpSession session) {
		session.setAttribute("category", "cu");
		//DB���� ������� ��ȸ�ؿ� ���ȭ�鿡 ����� �� �ֵ��� �Ѵ�
		model.addAttribute("list"
				, service.customer_list() );
		return "customer/list";
	}
	
}
