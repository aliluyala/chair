package com.chair.manager.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chair.manager.enums.PayStatus;
import com.chair.manager.exception.ChairException;
import com.chair.manager.mapper.RechargePackageMapper;
import com.chair.manager.mapper.RechargeRecordMapper;
import com.chair.manager.mapper.UserAccountMapper;
import com.chair.manager.pojo.RechargePackage;
import com.chair.manager.pojo.RechargeRecord;
import com.chair.manager.pojo.UserAccount;

@Service
public class UserAccountService extends BaseService<UserAccount> {
	@Autowired
	private RechargePackageMapper rechargePackageMapper;
	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;
	@Autowired
	private UserAccountMapper userAccountMapper;
	/**
	 * 用户充值
	 * @param phoneNumber 用户手机号
	 * @param packageID 套餐Id
	 * @return
	 */
	public String recharge(String phoneNumber, Integer packageID) {
		UserAccount uaParam=new UserAccount();
		uaParam.setPhoneNumber(phoneNumber);
		List<UserAccount> uaResult=super.queryList(uaParam);
		if(uaResult==null||uaResult.size()==0){
			throw new ChairException("1001","该手机号不存在账户");
		}
		UserAccount account=uaResult.get(0);
		//更具套餐id查询充值信息
		//新增一条充值记录
		//跟新账户的可以时长
		RechargePackage rp=rechargePackageMapper.selectByPrimaryKey(packageID);
		RechargeRecord rr=new RechargeRecord();
		Long times=System.currentTimeMillis();
		Date date=new Date(times);
		rr.setBatchNo(createBatchNo(account.getId()));
		rr.setPhoneNumbe(phoneNumber);
		rr.setRechargePackageId(packageID);
		rr.setRechargeAmount(rp.getRechargeAmoun());
		rr.setRechargeDuration(rp.getRechargeDuration());
		rr.setRechargeTime(date);
		rr.setPayStatu(PayStatus.PAY_FAIL.getValue());
		rr.setCreateTime(date);
		rr.setLastUpdate(date);
		rechargeRecordMapper.insertSelective(rr);
		return rr.getBatchNo();
	}
	
	private String createBatchNo(Integer accountId){
		Integer batchNo=10000+accountId;
		return batchNo+""+System.currentTimeMillis();
	}
	
	
	/**
	 * 支付回调方法
	 * @param batchNo 支付批次号
	 * @return
	 */
	public Integer updateRechargeStatus(String batchNo){
		RechargeRecord recordPraam=new RechargeRecord();
		recordPraam.setBatchNo(batchNo);
		List<RechargeRecord> recordResult=rechargeRecordMapper.select(recordPraam);
		if(recordResult==null||recordResult.size()==0){
			throw new ChairException("1002","支付批次号有误");
		}
		RechargeRecord record=recordResult.get(0);
		RechargeRecord r=new RechargeRecord();
		r.setId(record.getId());
		r.setPayStatu(PayStatus.PAY_SUCCESS.getValue());
		//跟新充值状态(由未支付变为已支付)
		rechargeRecordMapper.updateByPrimaryKeySelective(r);
		UserAccount uaParam=new UserAccount();
		uaParam.setPhoneNumber(record.getPhoneNumbe());
		UserAccount account=super.queryList(uaParam).get(0);
		UserAccount ua=new UserAccount();
		ua.setId(account.getId());
		ua.setAmount((account.getAmount()!=null?account.getAmount():new BigDecimal(0)).add(record.getRechargeAmount()));
		ua.setRestDuration((account.getRestDuration()!=null?account.getRestDuration():0)+record.getRechargeDuration());
		ua.setLastUpdate(new Date()); 
		return super.updateSelective(ua);
		
	}

	public void saveOrUpdate(UserAccount userAccount) {
		userAccountMapper.saveOrUpdate(userAccount);
	}
	
	/**
	 * 查询预支付信息
	 * @param phoneNumber
	 * @param batchNo
	 * @return
	 */
	public RechargeRecord queryPrepayInfo(String phoneNumber, String batchNo) {
		RechargeRecord recordPraam=new RechargeRecord();
		recordPraam.setPhoneNumbe(phoneNumber);
		recordPraam.setBatchNo(batchNo);
		recordPraam.setPayStatu(PayStatus.PAY_FAIL.getValue());
		List<RechargeRecord> records = rechargeRecordMapper.select(recordPraam);
		if(records.size() <= 0) return null;
		System.out.println("---record---"+records.get(0));
		return records.get(0);
	}
	

}
