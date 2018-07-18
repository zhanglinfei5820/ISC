package com.luckserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;

@RestController
public class DemoController {
	
	
	@Autowired
	private EurekaClient discoveryClient;
	
	@RequestMapping("/simple/{id}")
	public String findOne(@PathVariable Long id,String user) {
//		System.out.println("获取的user信息为：" + user.toString());
//		return userService.findOne(id).toString();
		return "1231231";
	}
	
//	@GetMapping("/eureka-intance")
//	public String serviceUrl() {
//	    InstanceInfo instance = discoveryClient.getNextServerFromEureka("microservice-weixin-server", false);
//	    return instance.getHomePageUrl();
//	}

}
