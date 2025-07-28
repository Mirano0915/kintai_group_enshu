package com.kintai.Form;

import java.sql.Time;

import jakarta.validation.constraints.NotBlank;

import com.kintai.Interface.EmployeeGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceChangeForm {
	
	private Long attendanceId;
	private Long nameId; 
	
	@NotBlank(message = "必須入力")
	private String preCheckinTime;
	
	@NotBlank(message = "必須入力")
	private String preCheckoutTime;

	@NotBlank(message = "変更理由を入力してください！", groups = EmployeeGroup.class)
	private String comment;
	
	private String checkinTime;
	private String checkouttime;

	
	private String employeeName;

	
	//入力フォームから受け取る時間がString型なのでTime型に変換
	public Time getPreCheckinTimeAsSqlTime() {
        // 直接 Time.valueOf(String) を使う。例："15:30:00"
        return Time.valueOf(preCheckinTime + ":00");
    }

    public Time getPreCheckoutTimeAsSqlTime() {
        return Time.valueOf(preCheckoutTime + ":00");
    }
    
 
//    退勤時間は出勤時間後の入力を判定
    public boolean isValidTimeRange() {
        if (preCheckinTime == null || preCheckinTime.isEmpty() || 
            preCheckoutTime == null || preCheckoutTime.isEmpty()) {
            return true; 
        }
        
        try {
            Time checkinTime = getPreCheckinTimeAsSqlTime();
            Time checkoutTime = getPreCheckoutTimeAsSqlTime();
            
            // 時間比較（java.sql.Time implements Comparable）
            return checkoutTime.compareTo(checkinTime) >= 0;
        } catch (Exception e) {
            return false; // 時間が間違いました
        }
    }
  }

