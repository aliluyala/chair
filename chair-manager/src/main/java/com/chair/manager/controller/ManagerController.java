package com.chair.manager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.pojo.Manager;
import com.chair.manager.service.ManagerService;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIResult login(Manager manager,HttpSession session){
		EasyUIResult rs= new EasyUIResult();
		Manager user=managerService.login(manager);
		if(user!=null){
			session.setAttribute("user", user);
		}
		else{
			rs.setStatus(404L);
		}
		return rs;
	}

	@RequestMapping(value="out",method=RequestMethod.GET)
	@ResponseBody
	public EasyUIResult out(HttpSession session){
		session.removeAttribute("user");
		return  new EasyUIResult();
	}
}
