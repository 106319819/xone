package com.gosun.xone.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gosun.xone.security.AuthenticationFilter;
import com.gosun.xone.security.AuthenticationProvider;


// Auth: 督军
// Date: 2018-12-03

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	@Value("${service.sercurity.permit-all}")
	public String permitAll;
	
	@Autowired
	private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder())
        .and().authenticationProvider(new AuthenticationProvider(userDetailsService));
//    	auth.authenticationProvider(new AuthenticationProvider(userDetailsService));
     // 使用自定义身份验证组件
       
    }
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		String[] maps = permitAll.split(",");
		for(String tmp : maps ) {
			http.cors().and().csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST).permitAll()
			.antMatchers(HttpMethod.GET).permitAll()
			.antMatchers(HttpMethod.DELETE).permitAll()
			.antMatchers(HttpMethod.HEAD).permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers(HttpMethod.PUT).permitAll()
			.antMatchers(HttpMethod.TRACE).permitAll()
			.antMatchers(HttpMethod.PATCH).permitAll()
			.antMatchers(tmp).permitAll()
			.anyRequest().authenticated();
		}
//        http.cors().and().csrf().disable().
//                authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login/").permitAll()
//              .antMatchers("/api/**").permitAll()
//                	// 其他所有请求需要身份认证
//                .anyRequest().authenticated();

//        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
     // token验证过滤器
        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }
}
