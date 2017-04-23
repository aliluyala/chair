package com.chair.manager.sms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.util.StringUtils;

public class PropertiesConfig {

	private static PropertiesConfig instance;
	private static Map<String, String> propMap = new HashMap<String, String>();

	private PropertiesConfig() {
		intiConfig();
	}

	public static PropertiesConfig getInstance() {
		if (instance == null) {
			instance = new PropertiesConfig();
		}
		return instance;
	}

	/**
	 * 初始化properties配置文件
	 * 
	 * @return
	 */
	private void intiConfig() {
		// 1.读取配置文件
		Properties prop = new Properties();
		try {
			prop.load(PropertiesConfig.class.getClassLoader().getResourceAsStream("sms.properties"));
			System.out.println("---prop---" + prop);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<Object> keys = prop.keySet();
		for (Object key : keys) {
			System.out.println("--key--" + key+" -- "+prop.get(key));
			if (key == null || StringUtils.isEmpty(prop.get(key)))
				continue;
			propMap.put(key.toString(), prop.get(key).toString());
		}
	}

	public String getValue(String key) {
		return propMap.get(key);
	}

	public static void main(String[] args) {
		new PropertiesConfig();
	}
}
