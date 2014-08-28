package com.springone2gx.sdc.demo.test;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springone2gx.sdc.demo.domain.SensorReading;
import com.springone2gx.sdc.demo.repo.SensorReadingRepository;
import com.springone2gx.sdc.demo.test.support.AbstractIntegrationTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SensorReadingJavaConfigIT extends AbstractSensorReadingIT {

	@Configuration
	@EnableCassandraRepositories(basePackageClasses = SensorReadingRepository.class)
	public static class Config extends AbstractIntegrationTestConfig {
		@Override
		public String[] getEntityBasePackages() {
			return new String[] { SensorReading.class.getPackage().getName() };
		}
	}
}
