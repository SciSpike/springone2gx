package com.springone2gx.sdc.demo.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.springone2gx.sdc.demo.domain.SensorReading;

public interface SensorReadingRepository extends CassandraRepository<SensorReading> {}
