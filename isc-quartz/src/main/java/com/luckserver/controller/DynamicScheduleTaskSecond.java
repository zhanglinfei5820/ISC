//package com.luckserver.controller;
//
//import java.util.Date;
//import java.util.concurrent.ScheduledFuture;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.Trigger;
//import org.springframework.scheduling.TriggerContext;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class DynamicScheduleTaskSecond {
//	private final static Logger LOGGER = LoggerFactory.getLogger(DynamicScheduleTaskSecond.class);
//	@Autowired
//	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
//	private ScheduledFuture<?> future;
//
//	private String cron = "";
//
//	public String getCron() {
//		return cron;
//	}
//
//	public void setCron(String cron) {
//		this.cron = cron;
//		stopCron();
//		future = threadPoolTaskScheduler.schedule(() ->{
//			LOGGER.info("1");
//		}, (TriggerContext triggerContext) ->{
//			CronTrigger trigger = new CronTrigger("0/2 * * * * ?"); // 定时任务触发，可修改定时任务的执行周期
//			Date nextExecDate = trigger.nextExecutionTime(triggerContext);
//			return nextExecDate;
//		} );
//		System.out.println(future);
//	}
//
//	public void stopCron() {
//		if (future != null) {
//			future.cancel(true);// 取消任务调度
//		}
//	}
//}