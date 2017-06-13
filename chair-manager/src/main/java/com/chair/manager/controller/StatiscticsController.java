package com.chair.manager.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.EasyUIResult;
import com.chair.manager.bean.ResponseResult;
import com.chair.manager.pojo.Manager;
import com.chair.manager.pojo.ManagerDto;
import com.chair.manager.pojo.ProxyStatisctics;
import com.chair.manager.service.ManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 统计控制层
 * 
 * @author yaoyuming
 *
 */
@Controller
@RequestMapping("/statisctics")
public class StatiscticsController {
	private Logger logger = Logger.getLogger(StatiscticsController.class);

	@Autowired
	private ManagerService managerService;
	
	/**
	 * 代理页面的统计，分页（管理台前端）
	 * 
	 * @param manager
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listProxyIncomeForPage", method = RequestMethod.POST)
	private EasyUIResult queryProxyListForPage(HttpSession session, @RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("from") String from, @RequestParam("to") String to ) {
		Manager manager = (Manager) session.getAttribute("user");
		System.err.println("-----manager------"+manager);
		if(manager.getType() != 2){
			logger.error("---listProxyIncomeForPage()---manager type is not match---");
			return null;
		}
		
		//查询该代理下的所有商家
		ManagerDto shopManager = new ManagerDto();
		shopManager.setProxyId(manager.getId());
		shopManager.setType(3);	//商家类型
		//设置查询条件，开始日期
		if(StringUtils.isEmpty(from)){
			try {
				shopManager.setFrom(DateUtils.parseToFormatString(new Date(), DateUtils.SIMPLE_DATE_STR));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			shopManager.setFrom(from);
		}
		//设置查询条件，结束日期
		if(StringUtils.isEmpty(to)){
			try {
				shopManager.setTo(DateUtils.parseToFormatString(new Date(), DateUtils.SIMPLE_DATE_STR));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			shopManager.setTo(to);
		}
		
		
		// 设置分页参数
        PageHelper.startPage(page, rows);
        //分页查询
        List<ProxyStatisctics> proxyStatiscticsList = managerService.queryProxyStatisctics(shopManager);
		System.out.println(proxyStatiscticsList.size());
		for (int i = 0; i < proxyStatiscticsList.size(); i++) {
			System.out.println(proxyStatiscticsList.get(i));
		}
		
        
		PageInfo<ProxyStatisctics> pageInfo = new PageInfo<ProxyStatisctics>(proxyStatiscticsList);
		
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}
	
	/**
	 * 代理页面的统计，顶部基本数据
	 * 
	 * @param manager
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryProxyBaseInfo", method = RequestMethod.POST)
	private ResponseResult queryProxyBaseInfo(HttpSession session) {
		Manager manager = (Manager) session.getAttribute("user");
		System.err.println("-----manager------"+manager);
		if(manager.getType() != 2){
			logger.error("---listProxyIncomeForPage()---manager type is not match---");
			return null;
		}
		Manager proxyManager = new Manager();
		proxyManager.setProxyId(manager.getId());
		proxyManager.setType(3);
		//总收益
		//商铺数
		//设备数
		//今日收益
		ProxyStatisctics proxyStatisctics = managerService.queryBaseStatisctics(proxyManager);
		System.err.println("-----ProxyStatisctics------"+proxyStatisctics);
		
		return new ResponseResult(proxyStatisctics);
	}
}
