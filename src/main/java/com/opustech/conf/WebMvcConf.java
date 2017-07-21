package com.opustech.conf;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.opustech.controller.AuthenticationController;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses=AuthenticationController.class)
public class WebMvcConf extends WebMvcConfigurerAdapter{
	
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToBigDecimal());
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorParameter(true).favorPathExtension(true).parameterName("media-type")
				.ignoreAcceptHeader(false).useJaf(false)
				.mediaType(".json", MediaType.APPLICATION_JSON_UTF8);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
		
	@Bean
	@Primary
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager){
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(new JsonViewResolver());
		//resolvers.add(new MarshallingXmlViewResolver());
		
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);
		return resolver;
	}
	
	@Bean
	public Filter hiddenHttpMethodFilter() {
	   HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
	   return filter;
	}
	/*
	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
	}

	@Value("classpath:schema.sql")
	private Resource schemaScript;

	@Value("classpath:data.sql")
	private Resource dataScript;
	
	private DatabasePopulator databasePopulator() {
	    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(schemaScript);
	    populator.addScript(dataScript);
	    return populator;
	}
	*/
}
