package com.simplemall.offer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 启动类
 *
 * @author guooo
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class OfferServApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfferServApplication.class, args);
	}

}
