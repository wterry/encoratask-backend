package com.skytouch.task.backend;

import com.skytouch.task.commons.model.Product;
import com.skytouch.task.commons.model.RestockInvoice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.skytouch.task.backend.*", "com.skytouch.task.commons.config.kafka", "com.skytouch.task.commons.event.services.*"})
@EntityScan(basePackageClasses = {Product.class, RestockInvoice.class})
@EnableKafka
@EnableJpaRepositories
public class PersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersistenceApplication.class, args);
	}

}
