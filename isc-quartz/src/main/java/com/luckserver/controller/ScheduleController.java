package com.luckserver.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.luckserver.config.DynamicScheduledTask;
import com.luckserver.config.SpringUtil;
import com.luckserver.core.ApplicationContext;
import com.luckserver.core.Holiday;
import com.luckserver.core.ScheduleTaskBeans;
import com.luckserver.entity.ScheduleTriggerTask;
import com.luckserver.job.ChickenJob;
import com.luckserver.service.UserService;

@RestController
public class ScheduleController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleController.class);

	@Resource
	private DynamicScheduledTask dynamicScheduledTask;
	
	@Autowired
	private UserService userService;
	@Autowired
    private EntityManager entityManager;
	
	@Async
	@GetMapping("/add")
	public void save() throws Exception {
//		userService.findAll().forEach(System.out::println);
		ScheduleTriggerTask scheduleTriggerTask = new ScheduleTriggerTask();
		MyClassLoader myClassLoader = new MyClassLoader();
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<TriggerTask> triggerTasks = new ArrayList<>();
		TriggerTask triggerTask = new TriggerTask(() -> threadPool.execute(() -> {
//			Object bean = SpringUtil.getBean("getUser");
//			System.out.println(JSON.toJSONString(bean));
//			List<?> users = job.getUser();
//			users.forEach(System.out::println);
			//加载配置文件并获取上下文
//			SpringUtil springUtil = new SpringUtil();
//			ApplicationContext applicationContext = springUtil.getApplicationContext();
//			System.out.println("spring对象bean:" + JSON.toJSONString(applicationContext.getBean("com.luckserver.job.ChickenJob")));
//			myClassLoader.execute("com.luckserver.job.ChickenJob", "execute");
			try {
				// 传入的类名和方法名
				String className = "com.luckserver.job.ChickenJob";
				String methodName = "getUser";
				Class c = Class.forName(className);
				Method ms[] = c.getDeclaredMethods();
				for (Method m : ms) {
					// 遍历找到所需要的方法
					if (m.getName().equals(methodName)) {
						// 传入参数，执行方法(参数我就不写了)
						m.invoke(c.newInstance(),entityManager);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			LOGGER.info("3");
		}), (TriggerContext triggerContext) -> {
			String cron = "0/2 * * * * ?";
			CronTrigger trigger = new CronTrigger(cron); // 定时任务触发，可修改定时任务的执行周期
			Date nextExecDate = trigger.nextExecutionTime(triggerContext);
			return nextExecDate;
		});
		TriggerTask triggerTask2 = new TriggerTask(() -> threadPool.execute(() -> {
//			myClassLoader.execute("com.luckserver.job.ChickenJob", "execute");
			LOGGER.info("4");
		}), (TriggerContext triggerContext) -> {
			String cron = "0/3 * * * * ?";
			CronTrigger trigger = new CronTrigger(cron); // 定时任务触发，可修改定时任务的执行周期
			Date nextExecDate = trigger.nextExecutionTime(triggerContext);
			return nextExecDate;
		});
		TriggerTask triggerTask3 = new TriggerTask(() -> threadPool.execute(() -> {
//			myClassLoader.execute("com.luckserver.job.ChickenJob", "getUser");
			LOGGER.info("5");
		}), (TriggerContext triggerContext) -> {
			String cron = "0/5 * * * * ?";
			CronTrigger trigger = new CronTrigger(cron); // 定时任务触发，可修改定时任务的执行周期
			Date nextExecDate = trigger.nextExecutionTime(triggerContext);
			return nextExecDate;
		});
		int stHashCode = dynamicScheduledTask.addTask(triggerTask);
		int stHashCode2 = dynamicScheduledTask.addTask(triggerTask2);
		int stHashCode3 = dynamicScheduledTask.addTask(triggerTask3);
		
		Thread.sleep(10000);
		dynamicScheduledTask.destroy(Arrays.asList(stHashCode2,stHashCode3));
		System.out.println(123);
		Thread.sleep(10000);
		System.out.println("取消所有任务调度");
		dynamicScheduledTask.destroy();
		System.out.println("开启任务调度");
		dynamicScheduledTask.startTaskScheduler(Arrays.asList(stHashCode2,stHashCode3));
	}
}
