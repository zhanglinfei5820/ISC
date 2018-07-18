package com.luckserver.job;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.luckserver.dao.UserDao;
import com.luckserver.service.UserService;
/**
 * 
* @ClassName: ChickenJob  
* @Description: 定时任务类
* @author zlf  
* @date 2018年7月13日  
* @modified By
* @modified Date
*
 */
public class ChickenJob{
    
	private static final Logger logger = LoggerFactory.getLogger(ChickenJob.class);
	private static final long serialVersionUID = 1L;
	private static Integer state = 0;
	
	@Autowired
    private EntityManager entityManager;

	public void execute(){
		System.out.println("任务执行成功");
	}
	
	public void setEntityManager(EntityManager entityManager) {
		if(this.entityManager == null) {
			this.entityManager = entityManager;
		}
	}
	
	public List<?> getUser(EntityManager entityManager) {
		List<?> resultList = null;
		resultList = entityManager.createNativeQuery("select * from user").getResultList();
		for (Object object : resultList) {
			System.out.println(JSON.toJSONString(object));
		}
		return resultList;
	}
//	@Bean(name = "demo")
	public String demo() {
		try {
			// 传入的类名和方法名
			String className = "com.luckserver.job.TestJob";
			String methodName = "getUser";
			Class c = Class.forName(className);
			Method ms[] = c.getDeclaredMethods();
			for (Method m : ms) {
				// 遍历找到所需要的方法
				if (m.getName().equals(methodName)) {
					// 传入参数，执行方法(参数我就不写了)
					m.invoke(c.newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
