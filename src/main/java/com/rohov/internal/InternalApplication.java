package com.rohov.internal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories("com.rohov.internal.repository.jpa")
@EnableElasticsearchRepositories("com.rohov.internal.repository.elastic")
public class InternalApplication {
	public static void main(String[] args) {
		SpringApplication.run(InternalApplication.class, args);
	}
}
