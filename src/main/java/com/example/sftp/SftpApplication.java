package com.example.sftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories({"com.example.sftp.repositories"})
@EnableTransactionManagement
public class SftpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SftpApplication.class, args);
	}

}
