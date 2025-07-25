package com.kintai.Entity;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AttendancesEntity{
	private Long attendanceId;//Primary Key
	private Long nameId;  
	private String name;
	private Time checkinTime;
	private Time checkoutTime;
	private Date date;
}
