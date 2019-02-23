package com.gosun.xone.core.utils;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.springframework.stereotype.Component;

import com.gosun.common.SpringContextUtil;
import com.gosun.uid.impl.CachedUidGenerator;
@Component
public class BiripIdentityGenerator extends IdentityGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		// TODO Auto-generated method stub
		CachedUidGenerator cachedUidGenerator = SpringContextUtil.getBean(CachedUidGenerator.class);
		 Long id =  cachedUidGenerator.getUID();
         if (id != null) {
             return id;
         }
      	 return super.generate(session, object);
	}

	
}
