package com.luckserver.core;
 
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

 @Configuration
public class ApplicationContext {
	
	private Class<?> configClass;
	
	private Map<String,Method> mapMethods;
	
	//构造函数,
	public ApplicationContext(Class<?> conClass) {
		this.configClass = conClass;
		if(checkConfigClass()) {
			mapMethods = getDeclaredMethods(configClass);
		}
	}
 
	//判断配置文件是否使用@Configuration注解
	private boolean checkConfigClass() {
		boolean useConfiguration = false;
		useConfiguration = configClass.isAnnotationPresent(Configuration.class);
		if(!useConfiguration) {
			System.out.println("配置文件类 :" + configClass.getName() + "缺少@Configuration注解");
		}
		return useConfiguration;
	}
	
	//获取一个类中所有使用@Bean注解的方法
	private Map<String,Method> getDeclaredMethods(Class<?> clazz) {
		Map<String,Method> resultMap = new HashMap<String, Method>();
		try {
			for(Method m : clazz.getDeclaredMethods()) {
				//获取每个方法的@bean注解
				Bean beanMethod = m.getAnnotation(Bean.class);
				if(null != beanMethod) {
					String annoName = beanMethod.value();
					//@Bean如果没有自定义value,使用方法名作为value
					annoName = ("".equals(annoName)) ? m.getName() : annoName;
					resultMap.put(annoName,m);
				}
			}
		} catch (SecurityException e) {
			System.out.println("读取配置文件错误" + e);
		}
		
		return resultMap;
	}
	
	//获取类实例
	private <T> T getInstance(Class<T> clazz) {
		T instance = null;
		try {
			instance = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return instance;
	}
	
	
	//根据bean名称获取bean
	public  <T> T getBean(String beanName) {
		//Bean实例
		T instance = null;
		
		//配置文件类的实例
		Object configObj = getInstance(configClass);
		
		//遍历所有定义的Bean
		Set<String> annoNames = mapMethods.keySet();
		
		for(String annoName : annoNames) {
			Method m = mapMethods.get(annoName);
			
			if(beanName.equals(annoName)) {
				try {
					instance = (T) m.invoke(configObj);
					
					//实例对象对应的Class类型
					Class<?> instanceClass = instance.getClass();
					
					for(Field field : instanceClass.getDeclaredFields()) {
						//读取属性上的@Resouce注解信息
						Resource res = field.getAnnotation(Resource.class);
						
						if(null != res) {
							//获取注解值
							String resValue = res.value();
							
							//判断是否已在配置文件类中已注册
							Method refM = mapMethods.get(resValue);
							if(null != refM) {
								//获取属性对应的标准setter方法名称
								String setterName = "set" + field.getName().substring(0, 1).toUpperCase() 
										+ field.getName().substring(1);
								Method setter = instanceClass.getMethod(setterName,field.getType());
								
								//执行setter方法
								setter.invoke(instance,refM.invoke(configObj));
							}
							
						}
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
		return instance;
		
	}
	
	
}
