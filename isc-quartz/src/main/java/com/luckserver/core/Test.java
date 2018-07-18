package com.luckserver.core;

import java.lang.reflect.Method;

import com.luckserver.service.UserService;

public class Test {
	
	public static void main(String[] args) {
		//加载配置文件并获取上下文
		ApplicationContext ctx = new ApplicationContext(ScheduleTaskBeans.class);
		
		Holiday holiday = ctx.getBean("chickenJob");
//		holiday.haveFun();
		
	}
 
}
