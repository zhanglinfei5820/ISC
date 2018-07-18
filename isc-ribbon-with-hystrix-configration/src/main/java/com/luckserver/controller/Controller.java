package com.luckserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * 
 * @author zlf
 *
 */
@RestController
public class Controller {
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * SEMAPHORE 信号量隔离只是限制了总的并发数，服务使用主线程进行同步调用，即没有线程池。
	 * 因此，如果只是想限制某个服务的总并发调用量或者调用的服务不涉及远程调用的话，可以使用轻量级的信号量来实现。 
	 * @param id
	 * @return
	 */
	@GetMapping("/movie/{id}")
	@HystrixCommand(fallbackMethod = "findByIdFallBack",commandProperties = @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"))
	public String findById(@PathVariable Long id) {
		return this.restTemplate.getForObject("http://isc-server/simple/" + id, String.class);
	}

	public String findByIdFallBack(Long id) {
		return "";
	}
	
	
}
