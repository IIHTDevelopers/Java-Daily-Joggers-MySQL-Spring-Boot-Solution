package com.dailyjoggers.service;

import java.time.LocalDate;
import java.util.List;

import com.dailyjoggers.dto.DailyActivityDTO;

public interface DailyActivityService {
	DailyActivityDTO createDailyActivity(Long userId, DailyActivityDTO dailyActivityDTO);

	List<DailyActivityDTO> getDailyActivityForUser(Long userId);

	DailyActivityDTO updateDailyActivityForUser(Long userId, Long activityId, DailyActivityDTO dailyActivityDTO);

	void deleteDailyActivityForUser(Long userId, Long activityId);

	DailyActivityDTO getSummarizedDailyActivityForUserByDate(Long userId, LocalDate date);

	List<DailyActivityDTO> getSummarizedWeeklyActivityForUser(Long userId);
}
