package com.kintai.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kintai.Dao.AttendancesDAO;
import com.kintai.Dao.HourlyWagesDAO;
import com.kintai.Dao.StampsDAO;
import com.kintai.Entity.AttendancesEntity;
import com.kintai.Form.AttendanceChangeForm;

@Service
public class AttendanceService {

	// AttendancesDAOの用意
	private final AttendancesDAO attendancesDAO;

	// StampsDAOの用意
	private final StampsDAO stampsDAO;

	private final HourlyWagesDAO hourlyWagesDAO;

	public AttendanceService(AttendancesDAO attendancesDAO, StampsDAO stampsDAO, HourlyWagesDAO hourlyWagesDAO) {
		this.attendancesDAO = attendancesDAO;
		this.stampsDAO = stampsDAO;
		this.hourlyWagesDAO = hourlyWagesDAO;

	}

	 // 勤怠データ一覧を取得 - 名前と日付の両方でフィルタリング
	 public List<AttendancesEntity> getAllAttendanceDataBothFilters(String name, LocalDate date) {
	     return attendancesDAO.readAllAttendanceDb(name, date);
	 }

	 // 勤怠データ一覧を取得 - 従業員名でフィルタリング
	 public List<AttendancesEntity> getAttendanceByName(String name) {
	     return attendancesDAO.readAllAttendanceDb(name, null);
	 }

	 // 勤怠データ一覧を取得 - 日付でフィルタリング
	 public List<AttendancesEntity> getAttendanceByDate(LocalDate date) {
	     return attendancesDAO.readAllAttendanceDb(null, date);
	 }
	 
	 
	//勤怠データ一覧を取得 - フィルターなし（全件表示）
	 public List<AttendancesEntity> getAllAttendanceData() {
	     return attendancesDAO.readAllAttendanceDb(null, null);
	 }

	 // フィルター用のドロップダウンメニューで使用
	 public List<String> getAllEmployeeNames() {
	     return attendancesDAO.getAllEmployeeNames();
	 }

	// 出勤処理
	public void checkin(Long nameId) {
		attendancesDAO.checkin(nameId);
	}

	// 退勤処理
	public void checkout(Long nameId) {
		attendancesDAO.checkout(nameId);
	}

	// 変更申請を送信
	public void submitChangeRequest(AttendanceChangeForm changeForm) {
		stampsDAO.insertAttendanceTime(changeForm);
	}

	// 出勤状況を判定（出勤済み、退勤済み、未出勤）
	public String getAttendanceStatus(AttendancesEntity attendance) {

		if (hourlyWagesDAO.isRetired(attendance.getNameId())) {
            return "退職";
        }

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
	
	
//	退勤登録
	public void deleteAttendance(Long attendanceId) {
	    AttendancesEntity entity = attendancesDAO.findById(attendanceId);
	    if (entity != null) {
	        Long nameId = entity.getNameId();

	        // 退職フラグによる論理削除
	        hourlyWagesDAO.retireByNameId(nameId);
	    }
	}
	
}
