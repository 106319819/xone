package com.gosun.xone.core.controller;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosun.SpringBootBaseTest;
import com.gosun.xone.core.entity.Organization;


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
			organization.setLevel(0);
			organization.setOrganizationNameEn("上海通联股份有限公司云南分公司");
			organization.setSortNo(0L);
			organization.setComment("comment");
			ObjectMapper mapper = new ObjectMapper();
			String str = mapper.writeValueAsString(organization);
			MockHttpServletRequestBuilder mhr = MockMvcRequestBuilders.post("/admin/organization/create");
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
		try{
			int page=0,size=10;
			PageRequest pageable = PageRequest.of(page, size, Direction.ASC, "organizationId");
			//Sort sort = new Sort(Direction.DESC, "id");
		    //Pageable pageable = new Pageable();
		    
		    
			ObjectMapper mapper = new ObjectMapper();
			String str = mapper.writeValueAsString(pageable);
			str = "{\"page\":1,\"size\":10,\"sort\":[{\"property\":\"sortNo\",\"direction\":\"ASC\"},{\"property\":\"organizationId\",\"direction\":\"ASC\"}]}";
			MockHttpServletRequestBuilder mhr = MockMvcRequestBuilders.get("/admin/organization/find-all");
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
	public void testFindByParentId()
	{
		try{

			MockHttpServletRequestBuilder mhr = MockMvcRequestBuilders.get("/admin/organization/find-by-parent/0");
			mhr.accept(MediaType.APPLICATION_JSON_UTF8);
			mhr.contentType(MediaType.APPLICATION_JSON_UTF8);
			mhr.header(HttpHeaders.AUTHORIZATION,"authorization");

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
}
