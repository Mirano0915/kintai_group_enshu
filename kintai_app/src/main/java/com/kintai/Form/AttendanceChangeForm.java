package com.kintai.Form;

import java.sql.Time;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceChangeForm {
	
	private Long nameId; 
	
	@NotBlank(message = "必須入力")
	private String preCheckinTime;
	
	@NotBlank(message = "必須入力")
	private String preCheckoutTime;

	@NotBlank(message = "変更理由を入力してください！")
	private String comment;

	
	//入力フォームから受け取る時間がString型なのでTime型に変換
	public Time getPreCheckinTimeAsSqlTime() {
        // 直接 Time.valueOf(String) を使う。例："15:30:00"
        return Time.valueOf(preCheckinTime + ":00");
    }

    public Time getPreCheckoutTimeAsSqlTime() {
        return Time.valueOf(preCheckoutTime + ":00");
    }
    
    @AssertTrue(message = "出勤時間は退勤時間より前である必要があります！")
    public boolean isCheckinBeforeCheckout() {
        try {
            return getPreCheckinTimeAsSqlTime().before(getPreCheckoutTimeAsSqlTime());
        } catch (Exception e) {
            return false; // 例外（nullや不正フォーマット）ならfalseで失敗にゃ
        }
    }
}
