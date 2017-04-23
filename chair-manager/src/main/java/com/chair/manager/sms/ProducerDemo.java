package com.chair.manager.sms;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;

public class ProducerDemo {
	
	
	
	
	
    public static void main(String[] args) {
    	PropertiesConfig prop = PropertiesConfig.getInstance();
    	System.out.println(PropertiesConfig.getInstance().getValue("sms.accessId"));
    	System.out.println(PropertiesConfig.getInstance().getValue("sms.accessId"));
    	System.out.println(PropertiesConfig.getInstance().getValue("sms.accessId"));
    	System.out.println(PropertiesConfig.getInstance().getValue("sms.accessId"));
    	System.out.println(PropertiesConfig.getInstance().getValue("sms.accessId"));
    	
    	
    	
    	
        CloudAccount account = new CloudAccount(prop.getValue("sms.accessId"), prop.getValue("sms.accessKey"), prop.getValue("sms.MNSEndpoint"));
        //这个client仅初始化一次
        MNSClient client = account.getMNSClient(); 
        //循环发送10条消息
        try{
            //TestQueue是你的测试队列，请提前创建
            CloudQueue queue = client.getQueueRef("TestQueue");
            for (int i = 0; i < 10; i++)
            {
                Message message = new Message();
                message.setMessageBody("I am test message " + i);
                message.setPriority(8);
                Message putMsg = queue.putMessage(message);
                System.out.println("Send message id is: " + putMsg.getMessageId());
            }
        } catch (ClientException ce)
        {
            System.out.println("Something wrong with the network connection between client and MNS service."
                    + "Please check your network and DNS availablity.");
            ce.printStackTrace();
        } catch (ServiceException se)
        {
            se.printStackTrace();
            System.err.println("MNS exception requestId:" + se.getRequestId()+" --- "+se);
            if (se.getErrorCode() != null) {
                if (se.getErrorCode().equals("QueueNotExist"))
                {
                    System.out.println("Queue is not exist.Please create before use");
                } else if (se.getErrorCode().equals("TimeExpired"))
                {
                    System.out.println("The request is time expired. Please check your local machine timeclock");
                }
            /*
            you can get more MNS service error code from following link:
            https://help.aliyun.com/document_detail/mns/api_reference/error_code/error_code.html
            */
            }
        } catch (Exception e)
        {
            System.out.println("Unknown exception happened!");
            e.printStackTrace();
        }
        client.close();
    }
}