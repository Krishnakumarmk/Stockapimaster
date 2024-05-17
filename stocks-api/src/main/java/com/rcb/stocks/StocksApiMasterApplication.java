package com.rcb.stocks;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication
public class StocksApiMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksApiMasterApplication.class, args);
	}

	
	 @Bean
	 public ThreadPoolTaskExecutor taskExecutor() {
	  ThreadPoolTaskExecutor tpexetr = new ThreadPoolTaskExecutor();
	  tpexetr.setCorePoolSize(2);
	  tpexetr.setMaxPoolSize(2);
	  tpexetr.setQueueCapacity(400);
	  tpexetr.setThreadNamePrefix("stockdata");
	  tpexetr.initialize();
	  return tpexetr;
	 }
}
