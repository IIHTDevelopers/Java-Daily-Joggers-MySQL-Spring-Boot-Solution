package com.dailyjoggers.functional;

import static com.dailyjoggers.utils.TestUtils.businessTestFile;
import static com.dailyjoggers.utils.TestUtils.currentTest;
import static com.dailyjoggers.utils.TestUtils.testReport;
import static com.dailyjoggers.utils.TestUtils.yakshaAssert;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("qa")
public class QAEnvironmentEndpointControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	public void setup() {
		// Resetting any previously set profiles or properties if necessary
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	private String getEndpointUrl() {
		return "http://localhost:" + port + "/dailyjogger/actuator/env";
	}

	@Test
	public void qaPropertiesActive() throws IOException {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
		assertThat(entity.getStatusCodeValue()).isEqualTo(200); // OK status
		Map<String, Object> body = entity.getBody();
		List<Map<String, Object>> propertySources = (List<Map<String, Object>>) body.get("propertySources");
		boolean serverPortFound = false;

		for (Map<String, Object> propertySource : propertySources) {
			if ("Config resource 'class path resource [application-qa.properties]' via location 'optional:classpath:/'"
					.equals(propertySource.get("name"))) {
				Map<String, Object> properties = (Map<String, Object>) propertySource.get("properties");
				Map<String, Object> serverPort = (Map<String, Object>) properties.get("server.port");
				if (serverPort != null && "8081".equals(serverPort.get("value"))) {
					serverPortFound = true;
					break;
				}
			}
		}
		yakshaAssert(currentTest(), serverPortFound ? "true" : "false", businessTestFile);
	}

	@Test
	public void qaContextPathIsSetCorrectly() throws IOException {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
		// Assertions and test logic for context path
		boolean contextPathFound = checkProperty(entity, "server.servlet.context-path", "/dailyjogger");
		yakshaAssert(currentTest(), contextPathFound ? "true" : "false", businessTestFile);
	}

	@Test
	public void qaDataSourceUrlIsSetCorrectly() throws IOException {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
		// Assertions and test logic for data source URL
		boolean dataSourceUrlFound = checkProperty(entity, "spring.datasource.url",
				"jdbc:h2:mem:dailyjogger;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		yakshaAssert(currentTest(), dataSourceUrlFound ? "true" : "false", businessTestFile);
	}

	@Test
	public void qaHibernateDialectIsSetCorrectly() throws IOException {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
		// Assertions and test logic for Hibernate dialect
		boolean hibernateDialectFound = checkProperty(entity, "spring.jpa.database-platform",
				"org.hibernate.dialect.H2Dialect");
		yakshaAssert(currentTest(), hibernateDialectFound ? "true" : "false", businessTestFile);
	}

	// Utility method to check property value
	private boolean checkProperty(ResponseEntity<Map> entity, String propertyName, String expectedValue) {
		Map<String, Object> body = entity.getBody();
		assertThat(body).isNotNull();
		List<Map<String, Object>> propertySources = (List<Map<String, Object>>) body.get("propertySources");

		for (Map<String, Object> propertySource : propertySources) {
			if (propertySource.get("properties") != null) {
				Map<String, Object> properties = (Map<String, Object>) propertySource.get("properties");
				if (properties.containsKey(propertyName)) {
					Map<String, Object> property = (Map<String, Object>) properties.get(propertyName);
					if (expectedValue.equals(property.get("value"))) {
						return true;
					}
				}
			}
		}
		return false;
	}
}