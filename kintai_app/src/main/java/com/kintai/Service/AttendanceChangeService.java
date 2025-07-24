package com.kintai.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kintai.Dao.AttendancesDAO;
import com.kintai.Dao.StampsDAO;
import com.kintai.Form.AttendanceChangeForm;

@Service
public class AttendanceChangeService {

	//DB操作が必要なためStampsDAOを準備
	@Autowired
	private StampsDAO stampsDAO;
	
	@Autowired
	private AttendancesDAO attendancesDAO;


	//attendanceIdからnameIdを取得
	public Long getNameId(Long attendanceId) {
		return stampsDAO.readNameId(attendanceId);
	}

	
	//変更後の出勤・退勤時間、コメントを入力
	public void attendanceRegister(AttendanceChangeForm f) {
		stampsDAO.insertAttendanceTime(f);

	}
	
	//出勤退勤時間の変更（管理者のみ）
	public void managerUpdateDB(AttendanceChangeForm f) {
		attendancesDAO.managerUpdateDB(f);
	}
	
	

	//退勤Idから出勤時間と退勤時間を取得
	public AttendanceChangeForm setCheckTime(Long attendanceId) {
		return attendancesDAO.setCheckTime(attendanceId);
	}
}
