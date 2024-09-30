package ru.shestakov.book;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import ru.shestakov.book.security.SecurityConstants;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(SecurityConstants.class)
public class BookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
