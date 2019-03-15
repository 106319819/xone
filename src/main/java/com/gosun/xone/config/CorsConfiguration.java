package com.gosun.xone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

	@Value("${service.cros.registry.mapping}")
	public String mapping;
	@Value("${service.cros.registry.origins}")
	public String origins;
	@Value("${service.cros.registry.headers}")
	public String headers;
	@Value("${service.cros.registry.methods}")
	public String methods;
	@Value("${service.cros.registry.credentials}")
	public Boolean credentials;
	@Value("${service.cros.registry.maxage}")
	public int maxage;
	@Value("${service.cros.registry.exposed.headers}")
	public String exposedHeaders;
	
	


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addCorsMappings(registry);
		String[] maps = mapping.split(",");
		for(String tmp : maps ) {
			registry.addMapping(tmp.trim())
			.allowedOrigins(origins)
			.allowedHeaders(headers)
			.allowedMethods(methods)
			.allowCredentials(credentials)
			.maxAge(maxage)
			.exposedHeaders(exposedHeaders);
		}
	}

//
//
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				String[] maps = mapping.split(",");
//				for(String tmp : maps ) {
//					registry.addMapping(tmp)
//					.allowedOrigins(origins)
//					.allowedHeaders(headers)
//					.allowedMethods(methods)
//					.allowCredentials(credentials)
//					.maxAge(maxage)
//					.exposedHeaders(exposedHeaders);
//				}
//			}
//		};
//		
//	}
}
