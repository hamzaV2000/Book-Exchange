package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Random;


@SpringBootApplication
@EnableJpaRepositories("com.example.repositories")
@EntityScan("com.example.entity")
@EnableTransactionManagement
@ComponentScan("com.example")
public class DemoApplication {
	public final static int randomGlobal = new Random().nextInt();
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

}
