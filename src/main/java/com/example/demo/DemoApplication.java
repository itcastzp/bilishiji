package com.example.demo;

import com.example.demo.bilibili.plugin.web.GoodsController;
import com.example.demo.config.SpringContextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
		System.out.println(run.getBean(GoodsController.class));
		System.out.println(SpringContextUtils.getBean(GoodsController.class));
	}

}
