package com.luckserver.core;
 
import com.luckserver.job.ChickenJob;
 
@Configuration
public class ScheduleTaskBeans {
	
	@Bean(value="chickenJob")
	public ChickenJob chickenJob() {
		return new ChickenJob();
	}
}
