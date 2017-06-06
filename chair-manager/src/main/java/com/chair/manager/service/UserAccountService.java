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
	 * 
	 * @param phoneNumber
	 *            用户手机号
	 * @param packageID
	 *            套餐Id
	 * @return
	 */
	public String recharge(String phoneNumber, Integer packageID, String openID, String batchNO) {
		UserAccount uaParam = new UserAccount();
		uaParam.setPhoneNumber(phoneNumber);
		List<UserAccount> uaResult = super.queryList(uaParam);
		if (uaResult == null || uaResult.size() == 0) {
			throw new ChairException("1010", "根据手机号【"+phoneNumber+"】查询不到对应的账户。");
		}
		UserAccount account = uaResult.get(0);
		// 更具套餐id查询充值信息
		// 新增一条充值记录
		// 跟新账户的可以时长
		RechargePackage rp = rechargePackageMapper.selectByPrimaryKey(packageID);
		RechargeRecord rr = new RechargeRecord();
		Long times = System.currentTimeMillis();
		Date date = new Date(times);
		rr.setOpenId(openID);
		// rr.setBatchNo(createBatchNo(account.getId()));
		rr.setBatchNo(batchNO);
		// rr.setTransactionId(transactionID);
		rr.setPhoneNumber(phoneNumber);
		rr.setRechargePackageId(packageID);
		rr.setRechargeAmount(rp.getRechargeAmoun());
		rr.setRechargeDuration(rp.getRechargeDuration());
		rr.setRechargeTime(date);
		rr.setPayStatus(PayStatus.PAY_FAIL.getValue());
		rr.setCreateTime(date);
		rr.setLastUpdate(date);
		rechargeRecordMapper.insertSelective(rr);
		return rr.getBatchNo();
	}


	/**
	 * 支付回调方法
	 * 
	 * @param batchNo
	 *            支付批次号
	 * @return
	 */
	public Integer updateRechargeStatus(String phoneNumber, String batchNO, String transactionID) {
		RechargeRecord recordPraam = new RechargeRecord();
		recordPraam.setBatchNo(batchNO);
		List<RechargeRecord> recordResult = rechargeRecordMapper.select(recordPraam);
		if (recordResult == null || recordResult.size() == 0) {
			throw new ChairException("1012", "支付批次号【"+batchNO+"】有误");
		}
		RechargeRecord record = recordResult.get(0);
		RechargeRecord r = new RechargeRecord();
		r.setId(record.getId());
		r.setTransactionId(transactionID);
		r.setLastUpdate(new Date());
		r.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
		// 更新充值状态(由未支付变为已支付)
		rechargeRecordMapper.updateByPrimaryKeySelective(r);

		// 更新账户余额
		UserAccount uaParam = new UserAccount();
		uaParam.setPhoneNumber(record.getPhoneNumber());
		UserAccount account = super.queryList(uaParam).get(0);
		UserAccount ua = new UserAccount();
		ua.setId(account.getId());
		ua.setAmount((account.getAmount() != null ? account.getAmount() : new BigDecimal(0))
				.add(record.getRechargeAmount()));
		ua.setTotalDuration(
				(account.getTotalDuration() != null ? account.getTotalDuration() : 0) + record.getRechargeDuration());
		ua.setRestDuration(
				(account.getRestDuration() != null ? account.getRestDuration() : 0) + record.getRechargeDuration());
		ua.setLastUpdate(new Date());
		System.err.println("------【支付回调】更新账户信息------" + ua);
		// 更新账户余额
		// UserAccount userAccount = new UserAccount();
		// userAccount.setLastUpdate(new Date());
		// userAccount.setAmount(record.getRechargeAmount());
		// userAccount.setTotalDuration(record.getRechargeDuration());
		// userAccount.setRestDuration(record.getRechargeDuration());
		// return userAccountMapper.updateUserAccountByUnique(userAccount);
		return super.updateSelective(ua);

	}

	public void saveOrUpdate(UserAccount userAccount) {
		userAccountMapper.saveOrUpdate(userAccount);
	}

	/**
	 * 查询预支付信息
	 * 
	 * @param phoneNumber
	 * @param batchNo
	 * @return
	 */
	public RechargeRecord queryPrepayInfo(String phoneNumber, String batchNo) {
		RechargeRecord recordPraam = new RechargeRecord();
		recordPraam.setPhoneNumber(phoneNumber);
		recordPraam.setBatchNo(batchNo);
		recordPraam.setPayStatus(PayStatus.PAY_FAIL.getValue());
		List<RechargeRecord> records = rechargeRecordMapper.select(recordPraam);
		if (records.size() <= 0)
			return null;
		System.out.println("---record---" + records.get(0));
		return records.get(0);
	}

	/**
	 * 根据openID和用户手机号查询账户信息
	 * 
	 * @param openID
	 * @param phoneNumber
	 */
	public UserAccount queryAccountInfo(String openID, String phoneNumber) {
		UserAccount ua = new UserAccount();
		ua.setOpenId(openID);
//		ua.setPhoneNumber(phoneNumber);
		List<UserAccount> userAccounts = userAccountMapper.select(ua);
		if(userAccounts == null || userAccounts.size() < 0){
			throw new ChairException("1010", "根据openID:【"+openID+"】查询不到对应的账户。");
		}
		return userAccounts.get(0);
//		UserAccount userAcount = userAccountMapper.queryAccountInfoByUnique(ua);
//		if (userAcount == null)
//			throw new ChairException("1010", "根据手机号【"+phoneNumber+"】查询不到对应的账户。");
//		return userAcount;
	}

}
