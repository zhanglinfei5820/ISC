package com.lucksoft;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import zipkin.server.EnableZipkinServer;
import zipkin.storage.mysql.MySQLStorage;


@EnableDiscoveryClient // 配置本应用将使用服务注册和服务发现
@EnableZipkinServer // 启动Zipkin服务
@SpringBootApplication
public class Application {

	@Bean
    public MySQLStorage mySQLStorage(DataSource datasource) {
        return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
