package com.kintai.Entity;


import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StampsEntity {
	private Long stampId; //Primary Key
	private Long nameId;  //Foreign Key
	private String name;
	private LocalTime checkinTime;
	private LocalTime checkoutTime;
	private LocalDate date;
	private LocalTime preCheckinTime;
	private LocalTime  preCheckOutTime;;
	private String comment;
}