package com.kintai.Entity;


import java.sql.Date;
import java.sql.Time;

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
	private Time checkinTime;
	private Time checkoutTime;
	private Date date;
	private Time preCheckinTime;
	private Time  preCheckOutTime;;
	private String comment;
}