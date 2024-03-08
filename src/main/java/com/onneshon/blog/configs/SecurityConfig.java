package com.onneshon.blog.configs;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.onneshon.blog.configs.jwt.JwtAuthenticationEntryPoint;
import com.onneshon.blog.configs.jwt.JwtAuthenticationFilter;
import com.onneshon.blog.exceptions.CustomAccessDeniedHandler;




@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity

//@EnableWebMvc//during api doc

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint authEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authFilter;

	// STEP 1: Default Configures of security
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()
		.authorizeHttpRequests(auth ->
				auth.
					requestMatchers("/api/v1/auth/login").permitAll()
					.requestMatchers("/api/v1/auth/register").permitAll()
					.requestMatchers("/api/v3/api-docs").permitAll()
					.requestMatchers("/api/v2/api-docs").permitAll()
					.requestMatchers("/swagger-resources/**").permitAll()
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/webjars/**").permitAll()
					.requestMatchers(HttpMethod.GET).permitAll()
					.anyRequest().authenticated()					
				)		
		//JWT Config er somoy korsi eta
		.exceptionHandling().authenticationEntryPoint(authEntryPoint)
		.accessDeniedHandler(myAccessDeniedHandler())
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		.and().httpBasic() //basic login er jonno
		
		
		
		//step 5: http ke boltesi j Data authentication hobe Database theke
		http.authenticationProvider(this.authenticationProvider());
		
		//JWT STEP :
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		DefaultSecurityFilterChain myConfigure = http.build();
		return myConfigure;
	}
	
	
	@Autowired
	private UserDetailServiceImple userDetailsServiceImple;
	
	//step 6:
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsServiceImple);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	
	//step 7: password encoder banano
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	//JWT STEP
	//eta kaj hocche User login er somoy validation korbe
//	@Bean
	//eta o kaj kore
//	AuthenticationManager authenticationManagerBean () throws Exception {
//		return new AuthenticationManager() {
//			@Override
//			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
//	}
	
	//FROM CHAT GPT
 	@Bean
    public AuthenticationManager authenticationManager() {
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(authenticationProvider()));
        return providerManager;
    }

 	
 	
 	
 	
 	
 	
 	
 	
 	//JWT te jokhn access denied korte chaitasi 
 	@Bean
 	AccessDeniedHandler myAccessDeniedHandler() {
 		return new CustomAccessDeniedHandler();
 	}
 	
 	
 	
 	
 	
 	//CORS POLICY THIK KORAR JONNO 
 	
 	//DURGESH ER REACT VIDEO
 	//https://www.youtube.com/watch?v=TZaWdbcMroo&list=PL0zysOflRCekAvE0nXWobPCgW0ets6s5o&index=9&t=1259s
 	
// 	@Bean
// 	public FilterRegistrationBean corsFilter() {
// 		
// 		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
// 		
// 		CorsConfiguration corsConfiguration = new CorsConfiguration();
// 		corsConfiguration.setAllowCredentials(true);
// 		
// 		//ekhane amar url pass korbo
// 		source.registerCorsConfiguration("/**",corsConfiguration);
// 		
//// 												org.springframework.web.filter.CorsFilter; 
// 		FilterRegistrationBean register = new FilterRegistrationBean(new CorsFilter(null));
// 		return register;
// 	} 	
 	
 	
 	

}
