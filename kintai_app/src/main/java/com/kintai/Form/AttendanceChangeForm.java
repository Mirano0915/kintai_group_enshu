package com.kintai.Form;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceChangeForm {
	
	private Long nameId; 
	
	private LocalTime preCheckinTime;
	
	private LocalTime preCheckoutTime;

	private String comment;
}
