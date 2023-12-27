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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EnvironmentEndpointControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	public void setup() {
		System.clearProperty("spring.profiles.active");
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	private String getEndpointUrl() {
		return "http://localhost:" + port + "/dailyjogger/actuator/env";
	}

//	@Test
//	public void defaultPropertiesActive() throws IOException {
//		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
//		assertThat(entity.getStatusCodeValue()).isEqualTo(200); // OK status
//		Map<String, Object> body = entity.getBody();
//		List<Map<String, Object>> propertySources = (List<Map<String, Object>>) body.get("propertySources");
//		boolean serverPortFound = propertySources.stream().anyMatch(
//				ps -> "Config resource 'class path resource [application.properties]' via location 'optional:classpath:/'"
//						.equals(ps.get("name"))
//						&& "8081".equals(((Map) ((Map) ps.get("properties")).get("server.port")).get("value")));
//		yakshaAssert(currentTest(), serverPortFound == true ? "true" : "false", businessTestFile);
//	}

	@Test
	public void isHealthEndpointEnabled() throws IOException {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
		boolean hibernateDialectFound = checkProperty(entity, "management.endpoint.health.show-details", "always");
		yakshaAssert(currentTest(), hibernateDialectFound ? "true" : "false", businessTestFile);
	}

	@Test
	public void isActuatorSet() throws IOException {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
		boolean hibernateDialectFound = checkProperty(entity, "management.endpoints.web.exposure.include", "*");
		yakshaAssert(currentTest(), hibernateDialectFound ? "true" : "false", businessTestFile);
	}

	@Test
	public void isBeansEndpointDisabled() throws IOException {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
		boolean hibernateDialectFound = checkProperty(entity, "management.endpoints.web.exposure.exclude", "beans");
		yakshaAssert(currentTest(), hibernateDialectFound ? "true" : "false", businessTestFile);
	}

	@Test
	public void isContextPathSet() throws IOException {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity(getEndpointUrl(), Map.class);
		boolean hibernateDialectFound = checkProperty(entity, "server.servlet.context-path", "/dailyjogger");
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
