package com.kintai.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AttendancesEntity{
	private Long nameId;  //Primary Key
	private String name;
	private LocalTime checkinTime;
	private LocalTime checkoutTime;
	private LocalDate date;
}
