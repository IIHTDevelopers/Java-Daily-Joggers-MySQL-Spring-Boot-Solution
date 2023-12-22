package com.dailyjoggers.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dailyjoggers.dto.DailyActivityDTO;
import com.dailyjoggers.entity.DailyActivity;
import com.dailyjoggers.exception.ResourceNotFoundException;
import com.dailyjoggers.repo.DailyActivityRepository;
import com.dailyjoggers.service.DailyActivityService;

@Service
public class DailyActivityServiceImpl implements DailyActivityService {

	private final DailyActivityRepository dailyActivityRepository;

	@Autowired
	public DailyActivityServiceImpl(DailyActivityRepository dailyActivityRepository) {
		this.dailyActivityRepository = dailyActivityRepository;
	}

	@Override
	public DailyActivityDTO createDailyActivity(Long userId, DailyActivityDTO dailyActivityDTO) {
		DailyActivity dailyActivity = new DailyActivity();
		dailyActivity.setUserId(userId);
		dailyActivity.setDate(dailyActivityDTO.getDate());
		dailyActivity.setSteps(dailyActivityDTO.getSteps());
		dailyActivity.setDistance(dailyActivityDTO.getDistance());
		dailyActivity.setCaloriesBurnt(dailyActivityDTO.getCaloriesBurnt());
		DailyActivity savedActivity = dailyActivityRepository.save(dailyActivity);

		DailyActivityDTO savedActivityDTO = new DailyActivityDTO();

		savedActivityDTO.setId(savedActivity.getId());
		savedActivityDTO.setUserId(savedActivity.getUserId());
		savedActivityDTO.setDate(savedActivity.getDate());
		savedActivityDTO.setSteps(savedActivity.getSteps());
		savedActivityDTO.setDistance(savedActivity.getDistance());
		savedActivityDTO.setCaloriesBurnt(savedActivity.getCaloriesBurnt());
		return savedActivityDTO;
	}

	@Override
	public Page<DailyActivityDTO> getDailyActivityForUser(Long userId, Pageable pageable) {
		Page<DailyActivity> dailyActivityPage = dailyActivityRepository.findByUserId(userId, pageable);
		if (dailyActivityPage.isEmpty()) {
			throw new ResourceNotFoundException("No daily activity found.");
		}
		return dailyActivityPage.map(dailyActivity -> {
			DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
			dailyActivityDTO.setId(dailyActivity.getId());
			dailyActivityDTO.setUserId(dailyActivity.getUserId());
			dailyActivityDTO.setDate(dailyActivity.getDate());
			dailyActivityDTO.setSteps(dailyActivity.getSteps());
			dailyActivityDTO.setDistance(dailyActivity.getDistance());
			dailyActivityDTO.setCaloriesBurnt(dailyActivity.getCaloriesBurnt());
			return dailyActivityDTO;
		});
	}

	@Override
	public DailyActivityDTO updateDailyActivityForUser(Long userId, Long activityId,
			DailyActivityDTO dailyActivityDTO) {
		DailyActivity existingActivity = dailyActivityRepository.findById(activityId)
				.orElseThrow(() -> new IllegalArgumentException("Daily Activity not found"));

		if (!existingActivity.getUserId().equals(userId)) {
			throw new IllegalArgumentException("Daily Activity does not belong to this user");
		}

		existingActivity.setDate(dailyActivityDTO.getDate());
		existingActivity.setSteps(dailyActivityDTO.getSteps());
		existingActivity.setDistance(dailyActivityDTO.getDistance());
		existingActivity.setCaloriesBurnt(dailyActivityDTO.getCaloriesBurnt());
		// Update other daily activity attributes if present

		DailyActivity updatedActivity = dailyActivityRepository.save(existingActivity);

		DailyActivityDTO updatedActivityDTO = new DailyActivityDTO();
		updatedActivityDTO.setId(updatedActivity.getId());
		updatedActivityDTO.setUserId(updatedActivity.getUserId());
		updatedActivityDTO.setDate(updatedActivity.getDate());
		updatedActivityDTO.setSteps(updatedActivity.getSteps());
		updatedActivityDTO.setDistance(updatedActivity.getDistance());
		updatedActivityDTO.setCaloriesBurnt(updatedActivity.getCaloriesBurnt());
		// Set other fields if present

		return updatedActivityDTO;
	}

	@Override
	public void deleteDailyActivityForUser(Long userId, Long activityId) {
		DailyActivity existingActivity = dailyActivityRepository.findById(activityId)
				.orElseThrow(() -> new IllegalArgumentException("Daily Activity not found"));

		if (!existingActivity.getUserId().equals(userId)) {
			throw new IllegalArgumentException("Daily Activity does not belong to this user");
		}

		dailyActivityRepository.delete(existingActivity);
	}

	@Override
	public DailyActivityDTO getSummarizedDailyActivityForUserByDate(Long userId, LocalDate date) {
		DailyActivity summarizedDailyData = dailyActivityRepository.getSummarizedDailyActivityForUserByDate(userId,
				date);
		if (summarizedDailyData == null) {
			throw new ResourceNotFoundException("No daily activity found.");
		}
		DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
		dailyActivityDTO.setId(summarizedDailyData.getId());
		dailyActivityDTO.setUserId(summarizedDailyData.getUserId());
		dailyActivityDTO.setDate(summarizedDailyData.getDate());
		dailyActivityDTO.setSteps(summarizedDailyData.getSteps());
		dailyActivityDTO.setDistance(summarizedDailyData.getDistance());
		dailyActivityDTO.setCaloriesBurnt(summarizedDailyData.getCaloriesBurnt());
		return dailyActivityDTO;
	}

	@Override
	public List<DailyActivityDTO> getSummarizedWeeklyActivityForUser(Long userId) {
	    List<Object[]> summarizedWeeklyData = dailyActivityRepository.getSummarizedWeeklyActivityForUser(userId);
	    List<DailyActivityDTO> weeklyActivityDTOList = new ArrayList<>();
	    if (summarizedWeeklyData.isEmpty()) {
	        throw new ResourceNotFoundException("No weekly activities found.");
	    }
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    for (Object[] objArray : summarizedWeeklyData) {
	        DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
	        LocalDate weekStart = LocalDate.parse((String) objArray[0], formatter);
	        dailyActivityDTO.setUserId(userId);
	        dailyActivityDTO.setDate(weekStart);
	        dailyActivityDTO.setSteps(((BigDecimal) objArray[1]).intValue());
	        dailyActivityDTO.setDistance(((Double) objArray[2]).doubleValue());
	        dailyActivityDTO.setCaloriesBurnt(((BigDecimal) objArray[3]).intValue());
	        weeklyActivityDTOList.add(dailyActivityDTO);
	    }
	    return weeklyActivityDTOList;
	}

	@Override
	public List<DailyActivityDTO> getSummarizedMonthlyActivityForUser(Long userId) {
		List<Object[]> summarizedMonthlyData = dailyActivityRepository.getSummarizedMonthlyActivityForUser(userId);
		List<DailyActivityDTO> monthlyActivityDTOList = new ArrayList<>();
		if (summarizedMonthlyData.isEmpty()) {
			throw new ResourceNotFoundException("No monthly activities found.");
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (Object[] objArray : summarizedMonthlyData) {
			DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
			LocalDate monthStart = LocalDate.parse((String) objArray[0], formatter);
			dailyActivityDTO.setUserId(userId);
			dailyActivityDTO.setDate(monthStart);
			dailyActivityDTO.setSteps(((BigDecimal) objArray[1]).intValue());
			dailyActivityDTO.setDistance(((Double) objArray[2]).doubleValue());
			dailyActivityDTO.setCaloriesBurnt(((BigDecimal) objArray[3]).intValue());
			monthlyActivityDTOList.add(dailyActivityDTO);
		}
		return monthlyActivityDTOList;
	}

	public static DailyActivityDTO[] mapObjectArrayToDTOArray(List<Object[]> objectArray) {
		List<DailyActivityDTO> dtoList = new ArrayList<>();

		for (Object[] obj : objectArray) {
			DailyActivityDTO dailyActivityDTO = new DailyActivityDTO();
			// Assuming the order of elements in obj[] is: [date, steps, distance,
			// caloriesBurnt]
			dailyActivityDTO.setDate((LocalDate) obj[0]);
			dailyActivityDTO.setSteps((int) obj[1]);
			dailyActivityDTO.setDistance((double) obj[2]);
			dailyActivityDTO.setCaloriesBurnt((int) obj[3]);
			// Map other fields if present in the array

			dtoList.add(dailyActivityDTO);
		}

		return dtoList.toArray(new DailyActivityDTO[0]);
	}
}
