package com.chair.manager.keepalive;

public class ServerDefaultObjectAction implements ServerObjectAction {

	@Override
	public Object doAction(Object rev) {
		System.out.println("处理并返回：" + rev);
		return rev;
	}

}
