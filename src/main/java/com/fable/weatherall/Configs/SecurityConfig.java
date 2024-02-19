package com.fable.weatherall.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



 
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Autowired
	CustomSuccessHandler customSuccessHandler;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http.csrf(c -> c.disable())

	        .authorizeHttpRequests(request -> request
//	        	.requestMatchers("/admin/static/css/**", "/admin/static/plugins/**", "/admin/static/js/**", "/admin/static/images/**",  "/admin/static/webjars/**").permitAll()
//	        	.requestMatchers("/user/static/**", "/user/static/js/**","/user/static/css/**").permitAll()
//	        	.requestMatchers("/static/css/assets/**","static/css/vendors/**").permitAll()
	        		
//	        	.requestMatchers("/admin/admin_dashboard/**").permitAll()
//		        .requestMatchers("/user/static/**", "/user/static/js/**","/user/static/css/**").permitAll()	
		        
	        	.requestMatchers("/Homepage/**","/Signup/**","/Login/**").permitAll()
	        		
	        	
	        	.requestMatchers("/admin/view_adminprofile","/admin/update_admin","/pages-profile").hasAuthority("admin")
	        	.requestMatchers("/admin/getUsers","/admin/getAllfood","/admin/getClothItems","/admin/getOutActs","/admin/getTrNms").hasAuthority("admin")
        	
	            .requestMatchers("/user_dash","/admin/api/wapiobj").hasAuthority("user")
	            
	            .requestMatchers("/home","/admin/api/view","/admin/api/config","/admin/api/wapiobj","/admin/api/wview","/comlogin","/forgetPassword","/signup","/about","/submit-feedback").permitAll() 
	            .requestMatchers("/checkEmailExists/**","/sendOtp/**","/admin/registerAdmin","/save","/verify-otp","/saveEmailAndOTP","/resetPassword").permitAll()
	            // .requestMatchers("/viewProfileDetail").hasAuthority("ADMIN")  // Allow admins to access viewProfileDetail
	            //.requestMatchers("/admin/static/**").permitAll()  // Allow access to static resources under /admin/static/**
	            .anyRequest().authenticated())  // Require authentication for all other requests

	        .formLogin(form -> form
	            .loginPage("/comlogin").loginProcessingUrl("/comlogin")
	            .successHandler(new CustomSuccessHandler()).permitAll())

	        .logout(form -> form
	            .invalidateHttpSession(true).clearAuthentication(true)
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/comlogin").permitAll());
	    
	    return http.build();
	}

		
	@Autowired
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

 
}
