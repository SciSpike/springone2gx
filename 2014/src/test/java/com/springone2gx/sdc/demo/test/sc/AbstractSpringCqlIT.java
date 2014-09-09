package com.springone2gx.sdc.demo.test.sc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.cassandra.core.keyspace.CreateTableSpecification.createTable;
import static com.datastax.driver.core.DataType.*;
import static org.springframework.cassandra.core.PrimaryKeyType.*;
import static org.springframework.cassandra.core.Ordering.*;
import static org.springframework.cassandra.core.keyspace.TableOption.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.CqlOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;
import com.springone2gx.sdc.demo.test.support.AbstractEmbeddedCassandraIntegrationTest;

import org.springframework.cassandra.core.RowMapper;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSpringCqlIT extends AbstractEmbeddedCassandraIntegrationTest {

	@Autowired
	CqlOperations template;

	@Before
	public void beforeEach() {
		assertNotNull(template);
	}

	public static class Entry {
		public String id;
		public String name;
		public String data;
	}

	@Test
	public void testTable() {
		template.execute(createTable("foobar").partitionKeyColumn("id", text()).clusteredKeyColumn("name", text())
				.column("data", text()).ifNotExists().with(COMPACT_STORAGE));

		String id = uuid();
		String name = "aName";
		String data = uuid();

		template.execute(String.format("INSERT INTO foobar (id, name, data) VALUES ('%s', '%s', '%s')", id, name, data));

		Entry entry = template.queryForObject(
				String.format("SELECT * FROM foobar WHERE id = '%s' AND name = '%s'", id, name), new RowMapper<Entry>() {

					@Override
					public Entry mapRow(Row row, int rowNum) throws DriverException {
						Entry e = new Entry();
						e.id = row.getString("id");
						e.name = row.getString("name");
						e.data = row.getString("data");
						return e;
					}
				});

		assertEquals(id, entry.id);
		assertEquals(name, entry.name);
		assertEquals(data, entry.data);
	}
}
