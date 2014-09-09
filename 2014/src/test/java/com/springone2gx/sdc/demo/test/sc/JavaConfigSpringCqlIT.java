package com.springone2gx.sdc.demo.test.sc;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.springone2gx.sdc.demo.test.support.AbstractIntegrationTestConfig;

@ContextConfiguration
public class JavaConfigSpringCqlIT extends AbstractSpringCqlIT {

	@Configuration
	public static class Config extends AbstractIntegrationTestConfig {}

}
