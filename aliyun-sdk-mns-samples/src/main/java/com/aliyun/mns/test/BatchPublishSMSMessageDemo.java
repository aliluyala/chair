package com.aliyun.mns.test;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;

/**
 * 短信demo
 * @author Administrator
 *
 */

public class BatchPublishSMSMessageDemo {
	//短信配置
	private static final String  AccessId= "LTAIimiClUITexrE";
	private static final String  AccessKey= "7CFzv3Z5shJHa0TxlNJRhMLh8AN0f5";
	private static final String  MNSEndpoint= "http://1123289024100286.mns.cn-hangzhou.aliyuncs.com";
	private static final String  TOPIC= "sms.topic-cn-hangzhou";
	private static final String  SignName= "";	//待补充
	private static final String  SMSTemplateCode= "SMS_62045122";	//短信模版
	private static final String  key1= "code";	//短信模版key1
	private static final String  value1= "1234";	//短信模版value1
	private static final String  key2= "product";	//短信模版key2
	private static final String  value2= "【测试短信】";	//短信模版value2
	private static final String  ReceiverPhoneNumber1= "13530380829";	//测试手机号
	
	public static void main(String[] args) {
		System.out.println("----");
		/**
		 * Step 1. 获取主题引用
		 */
		CloudAccount account = new CloudAccount(AccessId, AccessKey, MNSEndpoint);
		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef(TOPIC);
		/**
		 * Step 2. 设置SMS消息体（必须）
		 *
		 * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
		 */
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("sms-message");
		/**
		 * Step 3. 生成SMS消息属性
		 */
		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		// 3.1 设置发送短信的签名（SMSSignName）
		batchSmsAttributes.setFreeSignName(SignName);
		// 3.2 设置发送短信使用的模板（SMSTempateCode）
		batchSmsAttributes.setTemplateCode(SMSTemplateCode);
		// 3.3 设置接收短信的手机号码（在短信模板中定义的）
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
		smsReceiverParams.setParam(key1, value1);
		smsReceiverParams.setParam(key2, value2);
		// 3.4 增加接收短信的号码
		batchSmsAttributes.addSmsReceiver(ReceiverPhoneNumber1, smsReceiverParams);
//		batchSmsAttributes.addSmsReceiver("$YourReceiverPhoneNumber2", smsReceiverParams);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
		try {
			/**
			 * Step 4. 发布SMS消息
			 */
			TopicMessage ret = topic.publishMessage(msg, messageAttributes);
			System.out.println("---ret---"+ret);
			System.out.println("MessageId: " + ret.getMessageId());
			System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
		} catch (ServiceException se) {
			System.out.println(se.getErrorCode() + se.getRequestId());
			System.out.println(se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.close();
	}
}