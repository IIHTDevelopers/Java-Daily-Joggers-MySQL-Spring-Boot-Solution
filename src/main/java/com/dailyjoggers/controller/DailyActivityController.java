package com.dailyjoggers.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailyjoggers.dto.DailyActivityDTO;
import com.dailyjoggers.service.DailyActivityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/daily-activities")
public class DailyActivityController {

	private final DailyActivityService dailyActivityService;

	@Autowired
	public DailyActivityController(DailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	@PostMapping("/{userId}")
	public ResponseEntity<DailyActivityDTO> createDailyActivity(@PathVariable Long userId,
			@RequestBody @Valid DailyActivityDTO dailyActivityDTO) {
		DailyActivityDTO createdActivity = dailyActivityService.createDailyActivity(userId, dailyActivityDTO);
		return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Page<DailyActivityDTO>> getDailyActivityForUser(@PathVariable Long userId,
			Pageable pageable) {
		Page<DailyActivityDTO> dailyActivityPage = dailyActivityService.getDailyActivityForUser(userId, pageable);
		return new ResponseEntity<>(dailyActivityPage, HttpStatus.OK);
	}

	@PutMapping("/{userId}/{activityId}")
	public ResponseEntity<DailyActivityDTO> updateDailyActivityForUser(@PathVariable Long userId,
			@PathVariable Long activityId, @RequestBody @Valid DailyActivityDTO dailyActivityDTO) {
		DailyActivityDTO updatedActivity = dailyActivityService.updateDailyActivityForUser(userId, activityId,
				dailyActivityDTO);
		return new ResponseEntity<>(updatedActivity, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}/{activityId}")
	public ResponseEntity<Void> deleteDailyActivityForUser(@PathVariable Long userId, @PathVariable Long activityId) {
		dailyActivityService.deleteDailyActivityForUser(userId, activityId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{userId}/summary/daily/{date}")
	public ResponseEntity<DailyActivityDTO> getSummarizedDailyActivityForUserByDate(@PathVariable Long userId,
			@PathVariable String date) {
		LocalDate parsedDate = LocalDate.parse(date);
		DailyActivityDTO summarizedDailyActivity = dailyActivityService.getSummarizedDailyActivityForUserByDate(userId,
				parsedDate);
		return new ResponseEntity<>(summarizedDailyActivity, HttpStatus.OK);
	}

	@GetMapping("/{userId}/summary/weekly")
	public ResponseEntity<List<DailyActivityDTO>> getSummarizedWeeklyActivityForUser(@PathVariable Long userId) {
		List<DailyActivityDTO> summarizedWeeklyActivity = dailyActivityService
				.getSummarizedWeeklyActivityForUser(userId);
		return new ResponseEntity<>(summarizedWeeklyActivity, HttpStatus.OK);
	}

	@GetMapping("/{userId}/summary/monthly")
	public ResponseEntity<List<DailyActivityDTO>> getSummarizedMonthlyActivityForUser(@PathVariable Long userId) {
		List<DailyActivityDTO> summarizedMonthlyActivity = dailyActivityService
				.getSummarizedMonthlyActivityForUser(userId);
		return new ResponseEntity<>(summarizedMonthlyActivity, HttpStatus.OK);
	}
}
