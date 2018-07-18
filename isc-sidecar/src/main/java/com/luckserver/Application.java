package com.luckserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

/**
 * 
* @ClassName: Application  
* @Description: 启动类 sidecar注解  
* @author zlf  
* @date 2018年5月21日  
* @modified By
* @modified Date
*
 */
@SpringBootApplication
@EnableSidecar
public class Application {
	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
