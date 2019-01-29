package com.gosun.birip.core.utils;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.gosun.birip.uid.impl.CachedUidGenerator;
@Configuration
@Component
public class BiripIdentityGenerator extends IdentityGenerator {

	@Autowired
	CachedUidGenerator cachedUidGenerator;
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		// TODO Auto-generated method stub
		 Long id =  cachedUidGenerator.getUID();
         if (id != null) {
             return id;
         }
      	 return super.generate(session, object);
	}

	
}
