package com.dailyjoggers.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dailyjoggers.entity.DailyActivity;

public interface DailyActivityRepository extends JpaRepository<DailyActivity, Long> {
	Page<DailyActivity> findByUserId(Long userId, Pageable pageable);

	DailyActivity findByUserIdAndDate(Long userId, LocalDate date);

	@Query("SELECT da FROM DailyActivity da WHERE da.userId = :userId AND da.date = :date")
	DailyActivity getSummarizedDailyActivityForUserByDate(Long userId, LocalDate date);

	@Query(value = "SELECT DATE_FORMAT(da.date, '%Y-%m-%d') AS week_start, SUM(da.steps) AS total_steps, "
	        + "SUM(da.distance) AS total_distance, SUM(da.calories_burnt) AS total_calories_burnt "
	        + "FROM daily_activities da WHERE da.user_id = :userId GROUP BY DATE_FORMAT(da.date, '%Y-%m-%d') ORDER BY week_start", nativeQuery = true)
	List<Object[]> getSummarizedWeeklyActivityForUser(@Param("userId") Long userId);

	@Query(value = "SELECT DATE_FORMAT(da.date, '%Y-%m-01') AS month_start, SUM(da.steps) AS total_steps, "
	        + "SUM(da.distance) AS total_distance, SUM(da.calories_burnt) AS total_calories_burnt "
	        + "FROM daily_activities da WHERE da.user_id = :userId GROUP BY DATE_FORMAT(da.date, '%Y-%m-01') ORDER BY month_start", nativeQuery = true)
	List<Object[]> getSummarizedMonthlyActivityForUser(@Param("userId") Long userId);

}
