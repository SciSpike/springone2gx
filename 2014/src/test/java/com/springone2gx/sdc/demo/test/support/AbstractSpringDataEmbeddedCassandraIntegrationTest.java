/*
 * Copyright 2013-2014 the original author or authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springone2gx.sdc.demo.test.support;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.mapping.CassandraPersistentEntity;

public abstract class AbstractSpringDataEmbeddedCassandraIntegrationTest extends
		AbstractEmbeddedCassandraIntegrationTest {

	static {
		SpringCassandraBuildProperties props = new SpringCassandraBuildProperties();
		CASSANDRA_NATIVE_PORT = props.getCassandraPort();
	}

	public static String uuid() {
		return uuid(false);
	}

	public static String uuid(boolean removeDashes) {
		String string = UUID.randomUUID().toString();
		return removeDashes ? string.replace("-", "") : string;
	}

	public Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	public CassandraOperations template;

	public void deleteAllEntities() {

		for (CassandraPersistentEntity<?> entity : template.getConverter().getMappingContext().getPersistentEntities()) {
			template.truncate(entity.getTableName());
		}
	}
}
