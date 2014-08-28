package com.springone2gx.sdc.demo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.data.cassandra.repository.support.BasicMapId.id;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springone2gx.sdc.demo.domain.SensorReading;
import com.springone2gx.sdc.demo.repo.SensorReadingRepository;
import com.springone2gx.sdc.demo.test.support.AbstractSpringDataEmbeddedCassandraIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSensorReadingIT extends AbstractSpringDataEmbeddedCassandraIntegrationTest {

	@Autowired SensorReadingRepository repo;

	@Before
	public void beforeEach() {
		assertNotNull(repo);
	}

	@Test
	public void test() {
		String id = UUID.randomUUID().toString();
		Date time = new Date();
		String data = UUID.randomUUID().toString();
		repo.save(new SensorReading(id, time, data));

		SensorReading found = repo.findOne(id("sensorId", id).with("time", time));
		assertNotNull(found);

		assertEquals(id, found.getSensorId());
		assertEquals(time, found.getTime());
		assertEquals(data, found.getData());
	}
}
