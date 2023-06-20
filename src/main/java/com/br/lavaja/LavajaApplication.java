package com.br.lavaja;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class LavajaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LavajaApplication.class, args);
	}

}