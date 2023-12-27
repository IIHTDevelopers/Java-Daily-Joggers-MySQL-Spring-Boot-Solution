package com.dailyjoggers.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailyjoggers.dto.DailyActivityDTO;

public interface DailyActivityService {
	DailyActivityDTO createDailyActivity(Long userId, DailyActivityDTO dailyActivityDTO);

	Page<DailyActivityDTO> getDailyActivityForUser(Long userId, Pageable pageable);

	DailyActivityDTO updateDailyActivityForUser(Long userId, Long activityId, DailyActivityDTO dailyActivityDTO);

	void deleteDailyActivityForUser(Long userId, Long activityId);

	DailyActivityDTO getSummarizedDailyActivityForUserByDate(Long userId, LocalDate date);

	List<DailyActivityDTO> getSummarizedWeeklyActivityForUser(Long userId);

	List<DailyActivityDTO> getSummarizedMonthlyActivityForUser(Long userId);
}
