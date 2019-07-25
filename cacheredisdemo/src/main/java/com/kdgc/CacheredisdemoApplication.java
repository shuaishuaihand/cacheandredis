package com.kdgc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@Controller
@SpringBootApplication
@MapperScan("com.kdgc.hand.mapper")
@EnableCaching //开启缓存注解
/*@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})*/
public class CacheredisdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run( CacheredisdemoApplication.class, args);
	}

	@RequestMapping("/")
	public String toIndex(){
		return "index";
	}
}
