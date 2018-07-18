package com.luckserver.config;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.luckserver.service.DataConversionService;
import com.luckserver.service.impl.DataConversionServiceImpl;




/**
 * 
* @ClassName: CxfConfig  
* @Description: cxf核心配置类 
* @author zlf  
* @date 2018年7月6日  
* @modified By
* @modified Date
*
 */
@Configuration
public class CxfConfig {
	
	@Resource
	private DataConversionService conversionService;
	@Resource
	private Bus bus;
	
	@Bean
    public ServletRegistrationBean dispatcherServletCxf() {
        return new ServletRegistrationBean(new CXFServlet(), "/core/*");
    }
	
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    
    @Bean()
    public DataConversionService dataConversion() {
    	return new DataConversionServiceImpl();
    }
    
    @Bean
    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), personService());
        EndpointImpl endpoint = new EndpointImpl(bus, conversionService);
        endpoint.publish("/conversionService");
        return endpoint;
    }
}
