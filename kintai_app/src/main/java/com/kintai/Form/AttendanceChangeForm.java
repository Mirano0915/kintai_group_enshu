package com.kintai.Form;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceChangeForm {
	
	
	private LocalTime preCheckinTime;
	
	private LocalTime preCheckOutTime;

	private String comment;
}
