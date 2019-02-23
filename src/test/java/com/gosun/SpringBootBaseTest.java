package com.gosun;



import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@EnableJpaAuditing
public class SpringBootBaseTest
{
	@Autowired
    protected WebApplicationContext context;
	protected MockMvc mvc;
	@Before
	public void setUp() throws Exception{
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
}
