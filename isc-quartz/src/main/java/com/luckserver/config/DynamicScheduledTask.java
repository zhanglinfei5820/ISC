package com.luckserver.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: DynamicScheduledTask  
* @Description: 定时任务调度器  
* @author zlf  
* @date 2018年7月12日  
* @modified By
* @modified Date
*
 */
@Component
public class DynamicScheduledTask extends ScheduledTaskRegistrar {

	private Map<Object,Object> scheduleTriggerTasks = new HashMap<>();
	private List<TriggerTask> triggerTasks = new ArrayList<>();
	
	/**
	 * 关闭所有定时任务 
	 */
	@Override
	public void destroy() {
		scheduleTriggerTasks.forEach((k,v) -> {
			ScheduledTask task = (ScheduledTask) k;
			task.cancel();
		});
//		super.destroy();
	}
	
	/**
	 * 
	* @Title: destroy  
	* @Description: 暂停该任务
	* @param @param key    参数  
	* @return int    返回类型result 返回1表示成功 0表示异常
	*
	 */
	public int destroy(Object hashCode) {
		int result = -1;
		try {
			scheduleTriggerTasks.forEach((k,v) ->{
				ScheduledTask task = (ScheduledTask) k;
				if(hashCode.equals(task.hashCode())) {
					task.cancel();
				}
			});
			result = 1;
		} catch (Exception e) {
			return 0;
		}
		return result;
	}
	
	/**
	 * 
	* @Title: destroy  
	* @Description: 暂停任务集合  
	* @param @param hashCodes
	* @param @return    参数  
	* @return int    返回类型result 返回1表示成功 0表示异常
	*
	 */
	public int destroy(List<Object> hashCodes) {
		int result = -1;
		try {
			scheduleTriggerTasks.forEach((k,v) ->{
				ScheduledTask task = (ScheduledTask) k;
				hashCodes.forEach(n ->{
					if(n.equals(task.hashCode())) {
						task.cancel();
					}
				});
			});
			result = 1;
		} catch (Exception e) {
			return 0;
		}
		return result;
	}
	
	/**
	 * 
	* @Title: addTask  
	* @Description: 将定时任务加入任务调度器 返回hashcode保存key方便后面的暂停和修改
	* @param @param task
	* @param @return    参数  
	* @return int    返回类型  
	*
	 */
	public int addTask(TriggerTask task) {
		int result = -1;
		try {
			ScheduledTask scheduleTriggerTask = scheduleTriggerTask(task);
			this.scheduleTriggerTasks.put(scheduleTriggerTask, task);
			result = scheduleTriggerTask.hashCode();
		} catch (Exception e) {
			return 0;
		}
		return result;
	}
	
	@Override
	public ScheduledTask scheduleTriggerTask(TriggerTask task) {
		return super.scheduleTriggerTask(task);
	}
	/**
	 * 
	* @Title: startTaskScheduler  
	* @Description: 重新启动定时任务 根据hashcode
	* @param @param hashCode    参数  
	* @return int    返回类型result 返回1表示成功 0表示异常
	*
	 */
	public int startTaskScheduler(Object hashCode) {
		int result = -1;
		try {
			scheduleTriggerTasks.forEach((k,v) -> {
				ScheduledTask task = (ScheduledTask) k;
				if(hashCode.equals(task.hashCode())) {
					TriggerTask tTask = (TriggerTask) v;
					scheduleTriggerTask(tTask);
				}
			});
			result = 1;
		} catch (Exception e) {
			return 0;
		}
		return result;
	}
	/**
	 * 
	 * @Title: startTaskScheduler  
	 * @Description: 重新启动定时任务 根据hashcode
	 * @param @param hashCode    参数  
	 * @return int    返回类型result 返回1表示成功 0表示异常
	 *
	 */
	public int startTaskScheduler(List<Object> hashCodes) {
		int result = -1;
		try {
			scheduleTriggerTasks.forEach((k,v) -> {
				ScheduledTask task = (ScheduledTask) k;
				hashCodes.forEach(n -> {
					if(n.equals(task.hashCode())) {
						TriggerTask tTask = (TriggerTask) v;
						scheduleTriggerTask(tTask);
					}
				});
			});
			result = 1;
		} catch (Exception e) {
			return 0;
		}
		return result;
	}

	public Map<Object, Object> getScheduleTriggerTasks() {
		return scheduleTriggerTasks;
	}

	public void setScheduleTriggerTasks(Map<Object, Object> scheduleTriggerTasks) {
		this.scheduleTriggerTasks = scheduleTriggerTasks;
	}
	
	
}
