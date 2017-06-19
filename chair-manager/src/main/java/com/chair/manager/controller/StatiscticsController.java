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
import com.chair.manager.pojo.Statisctics;
import com.chair.manager.pojo.dto.ConsumedDetailsDto;
import com.chair.manager.pojo.dto.RechargeRecordDto;
import com.chair.manager.service.ConsumedDetailsService;
import com.chair.manager.service.ManagerService;
import com.chair.manager.service.RechargeRecordService;
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
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private ConsumedDetailsService consumedDetailsService;
	
	/**
	 *  查询消费统计的基本信息
	 *	@since 2017年6月15日
	 *	@author yaoym
	 *	@return
	 */
	@ResponseBody
	@RequestMapping(value = "queryConsumeBaseInfo", method = RequestMethod.POST)
	private ResponseResult queryConsumeBaseInfo() {
		//总消费时长 、今日消费时长
		return new ResponseResult(consumedDetailsService.queryConsumedDetailsBaseInfo());
	}
	
	/**
	 * 查询消费记录列表，分页（管理台前端）
	 *	@since 2017年6月15日
	 *	@author yaoym
	 *	@param page
	 *	@param rows
	 *	@param from
	 *	@param to
	 *	@return
	 */
	@RequestMapping(value = "listConsumeForPage", method = RequestMethod.POST)
	@ResponseBody
	public EasyUIResult queryConsumeList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("from") String from, @RequestParam("to") String to) {
		ConsumedDetailsDto consumeDto = new ConsumedDetailsDto();
		//设置查询条件，开始日期
		consumeDto.setFrom(getStrDate(from));
		consumeDto.setTo(getStrDate(to));
		return consumedDetailsService.queryPage(consumeDto, page, rows);
	}

	/**
	 * 查询充值记录列表，分页（管理台前端）
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "listRechargeRecordForPage", method = RequestMethod.POST)
	@ResponseBody
	public EasyUIResult queryRechargeRecordList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("from") String from, @RequestParam("to") String to) {
		RechargeRecordDto recordDto = new RechargeRecordDto();
		//设置查询条件，开始日期
		recordDto.setFrom(getStrDate(from));
		recordDto.setTo(getStrDate(to));
		
		return rechargeRecordService.queryPage(recordDto, page, rows);
	}
	
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
		System.err.println("-----代理页面的统计，分页（管理台前端）manager------"+manager);
		if(manager.getType() != 2){
			logger.error("---listProxyIncomeForPage()---manager type is not match---");
			return null;
		}
		
		//查询该代理下的所有商家
		ManagerDto shopManager = new ManagerDto();
		shopManager.setProxyId(manager.getId());
		shopManager.setType(3);	//商家类型
		//设置查询条件，开始日期
		shopManager.setFrom(getStrDate(from));
		shopManager.setTo(getStrDate(to));
		
		System.err.println("---shopManager----"+shopManager);
		
		
		// 设置分页参数
        PageHelper.startPage(page, rows);
        //分页查询
        List<Statisctics> proxyStatiscticsList = managerService.queryProxyStatisctics(shopManager);
        
		PageInfo<Statisctics> pageInfo = new PageInfo<Statisctics>(proxyStatiscticsList);
		
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
		if(manager.getType() != 2){
			logger.error("---queryProxyBaseInfo()---manager type is not match---");
			return null;
		}
		Manager proxyManager = new Manager();
		proxyManager.setId(manager.getId());
		proxyManager.setType(3);
		Statisctics proxyStatisctics = managerService.queryBaseStatisctics(proxyManager);
		
		return new ResponseResult(proxyStatisctics);
	}
	
	/**
	 * 代理页面的统计，顶部今日收益
	 * 
	 * @param manager
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryDayIncomeForProxy", method = RequestMethod.POST)
	private ResponseResult queryDayIncomeForProxy(HttpSession session) {
		Manager manager = (Manager) session.getAttribute("user");
		if(manager.getType() != 2){
			logger.error("---queryProxyBaseInfo()---manager type is not match---");
			return null;
		}
		Manager proxyManager = new Manager();
		proxyManager.setProxyId(manager.getId());
		proxyManager.setType(3);
		Statisctics proxyStatisctics = managerService.queryDayIncomeForProxy(proxyManager);
		return new ResponseResult(proxyStatisctics);
	}
	
	
	
	/*-----------------------------------------------------------------------------------------------------*/
	/**
	 * 商家页面的统计，分页（管理台前端）
	 * 
	 * @param manager
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listShopIncomeForPage", method = RequestMethod.POST)
	private EasyUIResult queryShopListForPage(HttpSession session, @RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("from") String from, @RequestParam("to") String to ) {
		Manager manager = (Manager) session.getAttribute("user");
		if(manager.getType() != 3){
			logger.error("---listShopIncomeForPage()---manager type is not match---");
			return null;
		}
		
		//查询商家下所有收益
		ManagerDto shopManager = new ManagerDto();
		shopManager.setShopId(manager.getId());
		shopManager.setType(3);	//商家类型
		//设置查询条件，开始日期
		shopManager.setFrom(getStrDate(from));
		shopManager.setTo(getStrDate(to));
		
		// 设置分页参数
        PageHelper.startPage(page, rows);
        //分页查询
        List<Statisctics> shopStatiscticsList = managerService.queryShopStatisctics(shopManager);
        
		PageInfo<Statisctics> pageInfo = new PageInfo<Statisctics>(shopStatiscticsList);
		
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
	@RequestMapping(value = "queryShopBaseInfo", method = RequestMethod.POST)
	private ResponseResult queryShopBaseInfo(HttpSession session) {
		Manager manager = (Manager) session.getAttribute("user");
		if(manager.getType() != 3){
			logger.error("---queryShopBaseInfo()---manager type is not match---");
			return null;
		}
		Manager shopManager = new Manager();
		shopManager.setShopId(manager.getId());
		shopManager.setType(3);
		//总收益
		//今日收益
		Statisctics shopStatisctics = managerService.queryShopBaseStatisctics(shopManager);
		
		return new ResponseResult(shopStatisctics);
	}
	
	/**
	 *  获取开始日期/结束日期
	 *	@since 2017年6月15日
	 *	@author yaoym
	 *	@param time
	 *	@return
	 */
	private String getStrDate(String date){
		String rs = null;
		if(StringUtils.isEmpty(date)){
			try {
				rs = DateUtils.parseToFormatString(new Date(), DateUtils.SIMPLE_DATE_STR);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			rs = date;
		}
		return rs;
	}
	
}
