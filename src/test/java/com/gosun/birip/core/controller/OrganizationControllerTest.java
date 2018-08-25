package com.gosun.birip.core.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosun.birip.SpringBootBaseTest;
import com.gosun.birip.core.entity.Organization;

import ch.qos.logback.classic.pattern.Util;

public class OrganizationControllerTest extends SpringBootBaseTest
{

	@Before
	public void setUp() throws Exception
	{
		super.setUp();
	}
	
	@Test
	public void testCreate()
	{
		try{
			Organization organization = new Organization();
			organization.setOrganizationCode("00001");
			organization.setOrganizationName("上海通联股份有限公司云南分公司");
			organization.setParentId(0L);
			organization.setCreateTime(System.currentTimeMillis());
			organization.setLevel(0);
			organization.setOrganizationNameEn("上海通联股份有限公司云南分公司");
			organization.setSortNo(0L);
			organization.setComment("comment");
			ObjectMapper mapper = new ObjectMapper();
			String str = mapper.writeValueAsString(organization);
			MockHttpServletRequestBuilder mhr = MockMvcRequestBuilders.post("/organization/create");
			mhr.accept(MediaType.APPLICATION_JSON_UTF8);
			mhr.contentType(MediaType.APPLICATION_JSON_UTF8);
			mhr.header(HttpHeaders.AUTHORIZATION,"authorization");
			mhr.content(str);
			ResultActions actions = mvc.perform(mhr);
			actions.andExpect(MockMvcResultMatchers.status().isOk());
			actions.andDo(MockMvcResultHandlers.print());
			actions.andReturn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	@Test
	public void testFindAll()
	{
		fail("Not yet implemented");
	}

}
