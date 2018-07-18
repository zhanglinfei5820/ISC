package com.luckserver.core;
 
import javax.persistence.EntityManager;

import com.luckserver.job.ChickenJob;
 
public class Holiday {
	
	@Resource(value="chickenJob")
	private ChickenJob chickenJob;


	public ChickenJob getChickenJob() {
		return chickenJob;
	}


	public void setChickenJob(ChickenJob chickenJob) {
		this.chickenJob = chickenJob;
	}


	public void haveFun(EntityManager entityManager) {
		chickenJob.getUser(entityManager).forEach(System.out::println);
	}

	
}
