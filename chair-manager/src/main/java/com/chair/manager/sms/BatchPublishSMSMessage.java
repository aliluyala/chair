package com.chair.manager.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;

/**
 * 批量短信服务
 * 
 * @author yaoyuming
 *
 */
public class BatchPublishSMSMessage {
	private static String accessID = "LTAIimiClUITexrE";
	private static String accessKey = "7CFzv3Z5shJHa0TxlNJRhMLh8AN0f5";
	private static String mnsEndpoint = "http://1123289024100286.mns.cn-hangzhou.aliyuncs.com/";
	private static String Topic = "sms.topic-cn-hangzhou";
	private static String signName = "豪艺盛智能科技";
	private static String smsTemplateCode = "SMS_62045122";
	private static String smsMessage = "";

	private static String smsTemplateKey1 = "customer";
	private static String smsTemplateValue1 = "zly，爱你哟~";

	private static String receiverPhoneNO1 = "13530380829";

	/**
	 * 发送短信
	 * @param smsContentMap
	 * @param recevierList
	 */
	public static void sendSMS(Map<String, String> smsContentMap, List<String> recevierList) {
		PropertiesConfig prop = PropertiesConfig.getInstance();
        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount(prop.getValue("sms.accessId"), prop.getValue("sms.accessKey"), prop.getValue("sms.MNSEndpoint"));
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef(prop.getValue("sms.topic"));
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
        batchSmsAttributes.setFreeSignName(prop.getValue("sms.signName"));
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode(prop.getValue("sms.templateCode"));
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        smsReceiverParams.setParam("code", smsContentMap.get("code"));
        smsReceiverParams.setParam("product", smsContentMap.get("product"));
        
        // 3.4 增加接收短信的号码
        for (int i = 0; i < recevierList.size(); i++) {
        	batchSmsAttributes.addSmsReceiver(recevierList.get(i), smsReceiverParams);
		}
//        batchSmsAttributes.addSmsReceiver(receiverPhoneNO1, smsReceiverParams);
        
//        batchSmsAttributes.addSmsReceiver("$YourReceiverPhoneNumber2", smsReceiverParams);
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        try {
            /**
             * Step 4. 发布SMS消息
             */
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
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

	public static void main(String[] args) {
		Map<String, String> templateMap = new HashMap<String, String>();
		templateMap.put("code", "5555");
		templateMap.put("product", "H5555");
		List<String> recevierList = new ArrayList<String>();
		recevierList.add("13530380829");
		sendSMS(templateMap, recevierList);
	}
	
	
//	public static void main(String[] args) {
//		PropertiesConfig prop = PropertiesConfig.getInstance();
//        /**
//         * Step 1. 获取主题引用
//         */
//        CloudAccount account = new CloudAccount(prop.getValue("sms.accessId"), prop.getValue("sms.accessKey"), prop.getValue("sms.MNSEndpoint"));
//        MNSClient client = account.getMNSClient();
//        CloudTopic topic = client.getTopicRef(prop.getValue("sms.topic"));
//        /**
//         * Step 2. 设置SMS消息体（必须）
//         *
//         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
//         */
//        RawTopicMessage msg = new RawTopicMessage();
//        msg.setMessageBody("sms-message");
//        /**
//         * Step 3. 生成SMS消息属性
//         */
//        MessageAttributes messageAttributes = new MessageAttributes();
//        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
//        // 3.1 设置发送短信的签名（SMSSignName）
//        batchSmsAttributes.setFreeSignName(prop.getValue("sms.signName"));
//        // 3.2 设置发送短信使用的模板（SMSTempateCode）
//        batchSmsAttributes.setTemplateCode(prop.getValue("sms.templateCode"));
//        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
//        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
//        smsReceiverParams.setParam(smsTemplateKey1, smsTemplateValue1);
////        smsReceiverParams.setParam("$YourSMSTemplateParamKey2", "$value2");
//        // 3.4 增加接收短信的号码
//        batchSmsAttributes.addSmsReceiver(receiverPhoneNO1, smsReceiverParams);
////        batchSmsAttributes.addSmsReceiver("$YourReceiverPhoneNumber2", smsReceiverParams);
//        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
//        try {
//            /**
//             * Step 4. 发布SMS消息
//             */
//            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
//            System.out.println("MessageId: " + ret.getMessageId());
//            System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
//        } catch (ServiceException se) {
//            System.out.println(se.getErrorCode() + se.getRequestId());
//            System.out.println(se.getMessage());
//            se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        client.close();
//    }
}
