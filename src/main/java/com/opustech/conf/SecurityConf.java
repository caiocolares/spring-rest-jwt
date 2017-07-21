package com.opustech.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter {

	@Autowired(required=false)
	UserDetailsService userDetailService;
	
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.
		csrf().disable().
		
		exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().
		
		//sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
	    
		authorizeRequests()
	            .antMatchers("/auth/**","/auth","/image","/image/**").permitAll()
	            .anyRequest().fullyAuthenticated()
	            .and()
	            
	            .logout()
	            .logoutUrl("/logout")
	            .deleteCookies("remember-me")
	            .logoutSuccessUrl("/")
	            
	            .permitAll()
	            .and()
	            .rememberMe();
		
		 // Custom JWT based security filter
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("user").password("pass").roles("USER");      
        auth.userDetailsService(userDetailService).passwordEncoder(new Md5PasswordEncoder());
        
    }
}
