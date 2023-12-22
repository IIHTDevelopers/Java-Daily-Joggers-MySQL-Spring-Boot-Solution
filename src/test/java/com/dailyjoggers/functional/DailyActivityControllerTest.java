package com.dailyjoggers.functional;

import static com.dailyjoggers.utils.MasterData.asJsonString;
import static com.dailyjoggers.utils.MasterData.getDailyActivityDTO;
import static com.dailyjoggers.utils.MasterData.getDailyActivityDTOList;
import static com.dailyjoggers.utils.MasterData.getDailyActivityDTOPage;
import static com.dailyjoggers.utils.TestUtils.businessTestFile;
import static com.dailyjoggers.utils.TestUtils.currentTest;
import static com.dailyjoggers.utils.TestUtils.testReport;
import static com.dailyjoggers.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dailyjoggers.controller.DailyActivityController;
import com.dailyjoggers.dto.DailyActivityDTO;
import com.dailyjoggers.service.DailyActivityService;

@WebMvcTest(DailyActivityController.class)
@AutoConfigureMockMvc
public class DailyActivityControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DailyActivityService dailyActivityService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateDailyActivity() throws Exception {
		DailyActivityDTO dailyActivityDTO = getDailyActivityDTO();

		when(this.dailyActivityService.createDailyActivity(any(), any())).thenReturn(dailyActivityDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/daily-activities/1")
				.content(asJsonString(dailyActivityDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(dailyActivityDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetDailyActivityForUser() throws Exception {
		Page<DailyActivityDTO> dailyActivityPage = getDailyActivityDTOPage(0, 1); // Replace with appropriate
																					// DailyActivityDTO data

		when(this.dailyActivityService.getDailyActivityForUser(any(), any())).thenReturn(dailyActivityPage);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/daily-activities/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(dailyActivityPage)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testUpdateDailyActivityForUser() throws Exception {
		DailyActivityDTO dailyActivityDTO = getDailyActivityDTO();

		when(this.dailyActivityService.updateDailyActivityForUser(any(), any(), any())).thenReturn(dailyActivityDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/daily-activities/1/1")
				.content(asJsonString(dailyActivityDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(dailyActivityDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testDeleteDailyActivityForUser() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/daily-activities/1/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetSummarizedDailyActivityForUserByDate() throws Exception {
		DailyActivityDTO summarizedDailyActivity = getDailyActivityDTO(); // Replace with appropriate DailyActivityDTO
																			// data

		when(this.dailyActivityService.getSummarizedDailyActivityForUserByDate(any(), any()))
				.thenReturn(summarizedDailyActivity);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/daily-activities/1/summary/daily/2023-12-20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(summarizedDailyActivity)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetSummarizedWeeklyActivityForUser() throws Exception {
		List<DailyActivityDTO> summarizedWeeklyActivity = getDailyActivityDTOList(); // Replace with appropriate
																						// List<DailyActivityDTO> data

		when(this.dailyActivityService.getSummarizedWeeklyActivityForUser(any())).thenReturn(summarizedWeeklyActivity);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/daily-activities/1/summary/weekly")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(summarizedWeeklyActivity))
						? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetSummarizedMonthlyActivityForUser() throws Exception {
		List<DailyActivityDTO> summarizedMonthlyActivity = getDailyActivityDTOList(); // Replace with appropriate
																						// List<DailyActivityDTO> data

		when(this.dailyActivityService.getSummarizedMonthlyActivityForUser(any()))
				.thenReturn(summarizedMonthlyActivity);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/daily-activities/1/summary/monthly")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(summarizedMonthlyActivity))
						? "true"
						: "false"),
				businessTestFile);
	}
}
