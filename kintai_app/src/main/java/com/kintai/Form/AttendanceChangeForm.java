package com.kintai.Form;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceChangeForm {
	
	private Long attendanceId;
	private Long nameId; 
	private String preCheckinTime;
	private String preCheckoutTime;
	private String comment;

	
	//入力フォームから受け取る時間がString型なのでTime型に変換
	public Time getPreCheckinTimeAsSqlTime() {
        // 直接 Time.valueOf(String) を使う。例："15:30:00"
        return Time.valueOf(preCheckinTime + ":00");
    }

    public Time getPreCheckoutTimeAsSqlTime() {
        return Time.valueOf(preCheckoutTime + ":00");
    }
}
