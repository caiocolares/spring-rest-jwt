package com.opustech;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

import com.opustech.conf.WebMvcConf;
import com.opustech.service.AuthorizationService;
import com.opustech.storage.StorageProperties;
import com.opustech.storage.StorageService;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackageClasses={WebMvcConf.class,AuthorizationService.class,StorageService.class})
@EnableConfigurationProperties(StorageProperties.class)
public class CatalogApplication extends SpringBootServletInitializer {
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(clazz);
	}

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}
	
	private static Class<CatalogApplication> clazz = CatalogApplication.class;
	

    //@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
}
