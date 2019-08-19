package com.wy.yubiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan("com.wy.*")
public class ThreadApplication {

	public static void main(String[] args) {
	 	SpringApplication.run(ThreadApplication.class, args);
	}
}
