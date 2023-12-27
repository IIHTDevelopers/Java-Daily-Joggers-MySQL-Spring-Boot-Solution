package com.dailyjoggers.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dailyjoggers.dto.DailyActivityDTO;
import com.dailyjoggers.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MasterData {

	public static UserDTO getUserDTO() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("john_doe");
		userDTO.setEmail("john@example.com");
		// Set other user properties as needed

		return userDTO;
	}

	public static List<UserDTO> getUserDTOList() {
		List<UserDTO> userDTOList = new ArrayList<>();

		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("john_doe");
		userDTO.setEmail("john@example.com");
		// Set other user properties as needed

		userDTOList.add(userDTO);

		return userDTOList;
	}

	public static DailyActivityDTO getDailyActivityDTO() {
		DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
		dailyActivityDTO.setId(1L);
		dailyActivityDTO.setUserId(1L);
		dailyActivityDTO.setDate(LocalDate.now());
		dailyActivityDTO.setSteps(5000);
		dailyActivityDTO.setDistance(3.2);
		dailyActivityDTO.setCaloriesBurnt(250);
		// Set other daily activity properties as needed

		return dailyActivityDTO;
	}

	public static List<DailyActivityDTO> getDailyActivityDTOList() {
		List<DailyActivityDTO> dailyActivityDTOList = new ArrayList<>();

		DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
		dailyActivityDTO.setId(1L);
		dailyActivityDTO.setUserId(1L);
		dailyActivityDTO.setDate(LocalDate.now());
		dailyActivityDTO.setSteps(5000);
		dailyActivityDTO.setDistance(3.2);
		dailyActivityDTO.setCaloriesBurnt(250);
		// Set other daily activity properties as needed

		dailyActivityDTOList.add(dailyActivityDTO);

		return dailyActivityDTOList;
	}

	public static String asJsonString(Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        mapper.registerModule(new JavaTimeModule()); // Register the module to handle Java 8 date/time types
	        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	        return mapper.writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	public static String userAsJsonString(UserDTO userDTO) {
		return asJsonString(userDTO);
	}

	public static String dailyActivityAsJsonString(DailyActivityDTO dailyActivityDTO) {
		return asJsonString(dailyActivityDTO);
	}

	public static Page<UserDTO> getUserDTOPage(int pageNo, int pageSize) {
		List<UserDTO> userDTOList = getUserDTOList(); // Replace with your method to get UserDTO list
		int start = (int) Math.min(pageNo * pageSize, userDTOList.size());
		int end = (int) Math.min((pageNo + 1) * pageSize, userDTOList.size());
		List<UserDTO> pageContent = userDTOList.subList(start, end);
		return new PageImpl<>(pageContent, PageRequest.of(pageNo, pageSize), userDTOList.size());
	}

	public static Page<DailyActivityDTO> getDailyActivityDTOPage(int pageNo, int pageSize) {
		List<DailyActivityDTO> dailyActivityDTOList = getMockDailyActivityDTOList(); // Replace this with your mock data
		int start = (int) Math.min(pageNo * pageSize, dailyActivityDTOList.size());
		int end = (int) Math.min((pageNo + 1) * pageSize, dailyActivityDTOList.size());
		List<DailyActivityDTO> pageContent = dailyActivityDTOList.subList(start, end);
		return new PageImpl<>(pageContent, Pageable.unpaged(), dailyActivityDTOList.size());
	}

	private static List<DailyActivityDTO> getMockDailyActivityDTOList() {
		List<DailyActivityDTO> mockDailyActivityDTOList = new ArrayList<>();
		return mockDailyActivityDTOList;
	}
}
