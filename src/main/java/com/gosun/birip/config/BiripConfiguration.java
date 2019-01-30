package com.gosun.birip.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration
public class BiripConfiguration implements WebMvcConfigurer {

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		//WebMvcConfigurer.super.extendMessageConverters(converters);
		/**
	     * 排除掉原来的MappingJackson2HttpMessageConverter
	     */
	    List<MappingJackson2HttpMessageConverter> originalConverters = new ArrayList<>();
	    for (HttpMessageConverter<?> converter : converters) {
	        if (converter instanceof MappingJackson2HttpMessageConverter) {
	            originalConverters.add((MappingJackson2HttpMessageConverter) converter);
	        }
	    }
	    if (CollectionUtils.isNotEmpty(originalConverters)) {
	        converters.removeAll(originalConverters);
	    }
	    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
	    ObjectMapper objectMapper = new ObjectMapper();

	    /**
	     * 将long类型的数据转为String类型
	     */
	    SimpleModule simpleModule = new SimpleModule();
	    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
	    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
	    simpleModule.addSerializer(long.class, ToStringSerializer.instance);
	    objectMapper.registerModule(simpleModule);
	    jackson2HttpMessageConverter.setObjectMapper(objectMapper);
	    converters.add(jackson2HttpMessageConverter);

	}

}
