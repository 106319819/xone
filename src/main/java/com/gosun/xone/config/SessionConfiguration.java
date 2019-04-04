package com.gosun.xone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableJdbcHttpSession(tableName="${spring.session.jdbc.table-name}")
@Configuration
public class SessionConfiguration {

	
}
