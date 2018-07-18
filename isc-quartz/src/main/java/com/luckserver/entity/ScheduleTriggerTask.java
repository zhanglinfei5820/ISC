package com.luckserver.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
* @ClassName: QuartzEntity  
* @Description: 定时任务实体类
* @author zlf  
* @date 2018年7月12日  
* @modified By
* @modified Date
*
 */
@Entity
@Table(name = "Schedule_Trigger_Task")
public class ScheduleTriggerTask{
	
	private Long indocno;//主键
	private String jobName;//任务名称
	private Integer hashCode;//任务分组
	private String description;//任务描述
	private String jobClassName;//执行类
	private String jobClassMethod;//执行方法
	private String cronExpression;//执行时间
	private Integer triggerState;//任务状态 0 暂停 1 执行 2删除
	private Date timestamp = new Date();
	
	public ScheduleTriggerTask() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	public Long getIndocno() {
		return indocno;
	}

	public void setIndocno(Long indocno) {
		this.indocno = indocno;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getHashCode() {
		return hashCode;
	}

	public void setHashCode(Integer hashCode) {
		this.hashCode = hashCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getJobClassMethod() {
		return jobClassMethod;
	}

	public void setJobClassMethod(String jobClassMethod) {
		this.jobClassMethod = jobClassMethod;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(Integer triggerState) {
		this.triggerState = triggerState;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}



	@Override
	public String toString() {
		return "QuartzEntity [indocno=" + indocno + ", jobName=" + jobName + ", hashCode=" + hashCode + ", description="
				+ description + ", jobClassName=" + jobClassName + ", jobClassMethod=" + jobClassMethod
				+ ", cronExpression=" + cronExpression + ", triggerState=" + triggerState + "]";
	}
	

}
