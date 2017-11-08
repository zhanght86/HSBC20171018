package com.sinosoft.lis.taskservice.crontab;
import org.apache.log4j.Logger;

import java.util.Date;

public class DateModel {
private static Logger logger = Logger.getLogger(DateModel.class);
	int year;
	int month;
	int day;
	int date;
	int hour;
	int minute;
	
	public DateModel(){
	}
	
	public DateModel(Date date){
		this.year=date.getYear()+1900;
		this.month=date.getMonth()+1;
		this.date=date.getDate();
		this.day=date.getDay();
		this.hour=date.getHours();
		this.minute=date.getMinutes();
	}
	
	public DateModel(int year, int month, int date){
		this.year=year;
		this.month=month;
		this.date=date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}
}
