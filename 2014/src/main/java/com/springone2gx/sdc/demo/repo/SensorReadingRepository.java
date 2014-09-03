package com.springone2gx.sdc.demo.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.springone2gx.sdc.demo.domain.SensorReading;

public interface SensorReadingRepository extends CassandraRepository<SensorReading> {

	@Query("SELECT * FROM sensorreading WHERE sensorid = ?0 AND time >= ?1 AND time < ?2")
	List<SensorReading> findSensorReadingsInDateRange(String sensorId, Date beginInclusive, Date endExclusive);
}
