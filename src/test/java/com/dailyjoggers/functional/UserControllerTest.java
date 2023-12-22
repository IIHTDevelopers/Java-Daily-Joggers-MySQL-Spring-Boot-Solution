package com.dailyjoggers.functional;

import static com.dailyjoggers.utils.MasterData.asJsonString;
import static com.dailyjoggers.utils.MasterData.getUserDTO;
import static com.dailyjoggers.utils.MasterData.getUserDTOPage;
import static com.dailyjoggers.utils.TestUtils.businessTestFile;
import static com.dailyjoggers.utils.TestUtils.currentTest;
import static com.dailyjoggers.utils.TestUtils.testReport;
import static com.dailyjoggers.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import com.dailyjoggers.controller.UserController;
import com.dailyjoggers.dto.UserDTO;
import com.dailyjoggers.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateUser() throws Exception {
		UserDTO userDTO = getUserDTO();

		when(this.userService.createUser(any())).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users").content(asJsonString(userDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(userDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetUserById() throws Exception {
		UserDTO userDTO = getUserDTO();

		when(this.userService.getUserById(any())).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(userDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testUpdateUserById() throws Exception {
		UserDTO userDTO = getUserDTO();

		when(this.userService.updateUserById(any(), any())).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/users/1").content(asJsonString(userDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(userDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testDeleteUserById() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/users/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetAllUsers() throws Exception {
		Page<UserDTO> usersPage = getUserDTOPage(0, 1); // Replace with appropriate User type data

		when(this.userService.getAllUsers(any())).thenReturn(usersPage);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(usersPage)) ? "true" : "false"),
				businessTestFile);
	}
}
