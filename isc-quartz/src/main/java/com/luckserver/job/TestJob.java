//package com.luckserver.job;
//
//import java.util.List;
//
//import com.luckserver.service.UserService;
//
///**
// * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
// */
//public class TestJob{
//	   
//    
//	public List<?> getUser() {
//		System.out.println(123);
//		UserService userService = new UserService();
//		userService.findAll().forEach(n ->System.out.println(n));
//		return userService.findAll();
////		List resultList = entityManager.createNativeQuery("select * from user").getResultList();
////		for (Object object : resultList) {
////			System.out.println(JSON.toJSONString(object));
////		}
//	}
//}
