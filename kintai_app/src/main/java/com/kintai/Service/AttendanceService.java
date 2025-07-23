package com.kintai.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kintai.Dao.AttendancesDAO;
import com.kintai.Dao.StampsDAO;
import com.kintai.Entity.AttendancesEntity;
import com.kintai.Form.AttendanceChangeForm;

@Service
public class AttendanceService {

    // AttendancesDAOの用意
    private final AttendancesDAO attendancesDAO;
    
    // StampsDAOの用意
    private final StampsDAO stampsDAO;
    
 

    public AttendanceService(AttendancesDAO attendancesDAO, StampsDAO stampsDAO) {
        this.attendancesDAO = attendancesDAO;
        this.stampsDAO = stampsDAO;
      
    }

    // 勤怠データ一覧を取得 (attendance_idで管理)
    public List<AttendancesEntity> getAllAttendanceData() {
        return attendancesDAO.readAllAttendanceDb();
    }

    // 出勤処理
    public void checkin(Long nameId) {
        attendancesDAO.checkin(nameId);
    }

    // 退勤処理
    public void checkout(Long nameId) {
        attendancesDAO.checkout(nameId);
    }

    // 勤怠データを削除（管理者のみ）- attendance_idを使用
    public void deleteAttendance(Long attendanceId) {
        attendancesDAO.deleteDB(attendanceId);
    }

    // 変更申請を送信
    public void submitChangeRequest(AttendanceChangeForm changeForm) {
        stampsDAO.insertAttendanceTime(changeForm);
    }

    // 出勤状況を判定（出勤済み、退勤済み、未出勤）
    public String getAttendanceStatus(AttendancesEntity attendance) {
    	
    	
  	if (attendance.getDate() == null) {
    	    return "未入力"; // 念のため日付未登録をカバー
    	}
  	Date today = Date.valueOf(LocalDate.now());

    	if (attendance.getDate().equals(today)) {
    	    if (attendance.getCheckinTime() == null && attendance.getCheckoutTime() == null) {
    	        return "未出勤";
    	    } else if (attendance.getCheckinTime() != null && attendance.getCheckoutTime() == null) {
    	        return "出勤中";
    	    } else if (attendance.getCheckinTime() == null && attendance.getCheckoutTime() != null) {
    	        return "未入力";
    	    } else {
    	        return "退勤済み";
    	    }
    	} else {
    	    // 今日以外の日付
    	    if (attendance.getCheckinTime() == null || attendance.getCheckoutTime() == null) {
    	        return "未入力";
    	    } else {
    	        return "退勤済み";
    	    }
    	}
    	
    	
    }
}


//今日
//出勤のみ→出勤中
//出勤・退勤→退勤済み
//退勤のみ→未入力

//
//昨日以前
//nullがある：未入力
//出勤・退勤→退勤済み
