package com.springone2gx.sdc.demo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.data.cassandra.repository.support.BasicMapId.id;
import static com.springone2gx.sdc.demo.test.Sensor.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.springone2gx.sdc.demo.domain.SensorReading;
import com.springone2gx.sdc.demo.repo.SensorReadingRepository;
import com.springone2gx.sdc.demo.test.support.AbstractSpringDataEmbeddedCassandraIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSensorReadingIT extends AbstractSpringDataEmbeddedCassandraIntegrationTest {

	@Autowired
	SensorReadingRepository repo;

	@Autowired
	CassandraTemplate template;

	@Before
	public void beforeEach() {
		assertNotNull(repo);
		assertNotNull(template);
		deleteAllEntities();
	}

	@Test
	public void testSave() {
		String id = uuid();
		Date time = new Date();
		String data = uuid();
		repo.save(new SensorReading(id, time, data));

		SensorReading found = repo.findOne(id("sensorId", id).with("time", time));
		assertNotNull(found);

		assertEquals(id, found.getSensorId());
		assertEquals(time, found.getTime());
		assertEquals(data, found.getData());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testQuery() {

		Date now = new Date();
		Date today = new Date(now.getYear(), now.getMonth(), now.getDate());
		Date tomorrow = new Date(today.getYear(), today.getMonth(), today.getDay() + 1);

		Map<Sensor, Integer> counts = new HashMap<Sensor, Integer>();
		for (Sensor sensor : Sensor.values()) {
			counts.put(sensor, 0);
		}

		List<List<?>> rows = new ArrayList<List<?>>();
		for (int i = 0; i < (48 * 60 * 60) + 1; i++) {
			List<Object> row = new ArrayList<Object>(3);
			rows.add(row);

			Sensor sensor = randomSensor();
			row.add(sensor.name()); // sensorId

			Date date = new Date(tomorrow.getTime() - (i * 1000));
			row.add(date); // time

			row.add(uuid(true)); // data

			if (date.getTime() >= today.getTime() && date.getTime() < tomorrow.getTime()) {
				counts.put(sensor, counts.get(sensor) + 1);
			}
		}
		template.ingest("INSERT INTO sensorreading (sensorid, time, data) VALUES (?, ?, ?)", rows);

		try {
			Thread.sleep(1000); // HACK
		} catch (InterruptedException e) {}

		for (Sensor sensor : Sensor.values()) {
			List<SensorReading> readings = repo.findSensorReadingsInDateRange(sensor.name(), today, tomorrow);
			assertEquals(sensor + " " + counts.get(sensor), sensor + " " + readings.size());
		}
	}
}
