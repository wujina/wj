package com.gm.wj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages = {"com.gm.*"})
@EnableTransactionManagement
@MapperScan("com.gm.wj.dao")
public class WjApplication {

	public static void main(String[] args) {
		SpringApplication.run(WjApplication.class, args);
	}
}
