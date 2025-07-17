package com.kintai.Form;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceChangeForm {
	
	private Long nameId; 
	
	private Time preCheckinTime;
	
	private Time preCheckoutTime;

	private String comment;
}
