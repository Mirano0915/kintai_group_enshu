package com.kintai.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HourlyWagesEntity {
	private Long nameId; //Primary Key
	private String name;
	private int hourlyWage;
	private boolean isRetired;
	
	private String totalWorkingTime;
	private int nightWorkingTime;
	private int daysWorked;
	private int transportation;
	private int totalAmount;
}