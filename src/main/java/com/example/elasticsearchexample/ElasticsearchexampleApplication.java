package com.example.elasticsearchexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.example.elasticsearchexample.config","com.example.elasticsearchexample.client","com.example.elasticsearchexample.api","com.example.elasticsearchexample.model"})
public class ElasticsearchexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchexampleApplication.class, args);
	}

}
