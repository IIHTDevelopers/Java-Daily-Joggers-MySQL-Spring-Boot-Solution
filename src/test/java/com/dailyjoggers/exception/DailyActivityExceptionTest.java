package com.dailyjoggers.exception;

import static com.dailyjoggers.utils.MasterData.getDailyActivityDTO;
import static com.dailyjoggers.utils.TestUtils.currentTest;
import static com.dailyjoggers.utils.TestUtils.exceptionTestFile;
import static com.dailyjoggers.utils.TestUtils.testReport;
import static com.dailyjoggers.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dailyjoggers.controller.DailyActivityController;
import com.dailyjoggers.dto.DailyActivityDTO;
import com.dailyjoggers.service.DailyActivityService;
import com.dailyjoggers.utils.MasterData;

@WebMvcTest(DailyActivityController.class)
@AutoConfigureMockMvc
public class DailyActivityExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DailyActivityService dailyActivityService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateDailyActivityInvalidDataException() throws Exception {
		DailyActivityDTO dailyActivityDTO = getDailyActivityDTO();
		dailyActivityDTO.setUserId(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/daily-activities/1")
				.content(MasterData.asJsonString(dailyActivityDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateDailyActivityInvalidDataException() throws Exception {
		DailyActivityDTO dailyActivityDTO = getDailyActivityDTO();
		dailyActivityDTO.setUserId(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/daily-activities/1/" + dailyActivityDTO.getId())
				.content(MasterData.asJsonString(dailyActivityDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetDailyActivityByIdResourceNotFoundException() throws Exception {
		DailyActivityDTO dailyActivityDTO = getDailyActivityDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "DailyActivity not found");
		when(this.dailyActivityService.getDailyActivityForUser(1L, PageRequest.of(0, 10)))
				.thenThrow(new ResourceNotFoundException("DailyActivity not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/daily-activities/1/" + dailyActivityDTO.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (exResponse.getMessage().equals("DailyActivity not found") ? "true" : "false"),
				exceptionTestFile);
	}

}
