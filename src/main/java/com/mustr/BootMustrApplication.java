package com.mustr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.mustr.*.dao")
public class BootMustrApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootMustrApplication.class, args);
	}
}
