package com.jsq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jsq.mapper") //扫描mapper，把mapper文件加到配置中
@SpringBootApplication
public class JsqApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsqApplication.class, args);
	}
}
