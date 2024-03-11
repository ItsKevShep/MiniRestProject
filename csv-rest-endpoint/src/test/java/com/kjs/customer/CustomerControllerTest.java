package com.kjs.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class CustomerControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc controllerMvc;

	private IDatabaseConnection databaseConnection;

	@BeforeEach
	public void setUp() throws Exception {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:databaseName");
		dataSource.setUser("sa");
		dataSource.setPassword("password");

		this.databaseConnection = new DatabaseConnection(dataSource.getConnection());

		FlatXmlDataSet xmlDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/dataset.xml"));
		DatabaseOperation.CLEAN_INSERT.execute(this.databaseConnection, xmlDataSet);
		this.controllerMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void verifyDataBootstrapped() throws Exception {
		ITable expected = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/dataset.xml"))
				.getTable("Customers");
		Assertion.assertEquals(expected, this.databaseConnection.createDataSet().getTable("Customers"));
	}

	@Test
	public void verifyCanRetrieveAllCustomers() throws Exception {
		MvcResult result = this.controllerMvc
				.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		result.getResponse().getContentAsString();
		List<Map<String, Object>> map = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<Map<String, Object>>>() {
				});

		assertEquals(3, map.size());
		assertEquals("Customer One", map.get(0).get("name"));
		assertEquals("Customer Two", map.get(1).get("name"));
		assertEquals("Customer Three", map.get(2).get("name"));
	}

	@Test
	public void verifyCanRetrieveSingleCustomer() throws Exception {
		MvcResult result = this.controllerMvc
				.perform(MockMvcRequestBuilders.get("/abc").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		assertEquals(200, result.getResponse().getStatus());

		result.getResponse().getContentAsString();
		Map<String, Object> map = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
				new TypeReference<Map<String, Object>>() {
				});

		assertEquals("Customer Two", map.get("name"));
	}

	@Test
	public void verifyCanAddCustomer() throws Exception {
		CustomerDto newCustomer = new CustomerDto().setReference("aaaaa").setName("New Customer")
				.setAddressOne("Address One").setAddressTwo("Address Two").setTown("Town").setCounty("County")
				.setCountry("Country").setPostcode("Postcode");

		MvcResult result = this.controllerMvc
				.perform(MockMvcRequestBuilders.post("/")
						.content(new ObjectMapper().writer().writeValueAsString(newCustomer))
						.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(200, result.getResponse().getStatus());

		ITable expected = new FlatXmlDataSetBuilder()
				.build(getClass().getResourceAsStream("/results/datasetWithNewCustomer.xml")).getTable("Customers");
		Assertion.assertEquals(expected, this.databaseConnection.createDataSet().getTable("Customers"));
	}
}
