package io.akash.openbanking.accountaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * Purpose: This is Main class for running the current project  .
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */

@SpringBootApplication
@EnableCaching
public class AccountAggregatorApp {
	public static void main(String[] args) {
		SpringApplication.run(AccountAggregatorApp.class, args);
	}
}
