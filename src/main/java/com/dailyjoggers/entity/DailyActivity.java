package com.dailyjoggers.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "daily_activities")
public class DailyActivity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "date", columnDefinition = "DATE")
	private LocalDate date;

	@Column(name = "steps")
	private int steps;

	@Column(name = "distance")
	private double distance;

	@Column(name = "calories_burnt")
	private int caloriesBurnt;

	public DailyActivity() {
		super();
	}

	public DailyActivity(Long id, Long userId, LocalDate date, int steps, double distance, int caloriesBurnt) {
		super();
		this.id = id;
		this.userId = userId;
		this.date = date;
		this.steps = steps;
		this.distance = distance;
		this.caloriesBurnt = caloriesBurnt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getCaloriesBurnt() {
		return caloriesBurnt;
	}

	public void setCaloriesBurnt(int caloriesBurnt) {
		this.caloriesBurnt = caloriesBurnt;
	}

	@Override
	public String toString() {
		return "DailyActivity [id=" + id + ", userId=" + userId + ", date=" + date + ", steps=" + steps + ", distance="
				+ distance + ", caloriesBurnt=" + caloriesBurnt + "]";
	}
}
