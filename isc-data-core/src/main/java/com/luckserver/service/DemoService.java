package com.luckserver.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;


@Aspect
@RestController
public class DemoService {
	@Autowired
    private EntityManager entityManager;
	
	@GetMapping(value = "/getAll")
	public String getALl() throws Throwable {
		List resultList = this.entityManager.createNativeQuery("select * from sys_user where sname = '测试人员1'").getResultList();
		System.out.println(JSON.toJSONString(resultList));
//		((DemoService)AopContext.currentProxy()).getALl();
//		new DemoService2().getALl();
		return JSON.toJSONString(resultList);
	}
}
