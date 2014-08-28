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
package com.springone2gx.dsc.demo.test.support;

import static org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification.createKeyspace;

import java.util.Arrays;
import java.util.List;

import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;

/**
 * Setup any spring configuration for unit tests
 * 
 * @author David Webb
 * @author Matthew T. Adams
 */
@Configuration
public abstract class AbstractIntegrationTestConfig extends AbstractCassandraConfiguration {

	public static final SpringCassandraBuildProperties PROPS = new SpringCassandraBuildProperties();
	public static final int PORT = PROPS.getCassandraPort();
	public static final int RPC_PORT = PROPS.getCassandraRpcPort();

	public String keyspaceName = Utils.randomKeyspaceName();

	@Override
	protected int getPort() {
		return PORT;
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.RECREATE;
	}

	@Override
	protected String getKeyspaceName() {
		return keyspaceName;
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return Arrays.asList(createKeyspace().name(getKeyspaceName()).withSimpleReplication());
	}
}
