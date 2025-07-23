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
	public void attendanceRegister(AttendanceChangeForm f, String preCheckinTime, String preCheckoutTime, String comment) {
		f.setPreCheckinTime(preCheckinTime);
		f.setPreCheckoutTime(preCheckoutTime);
		f.setComment(comment);
		stampsDAO.insertAttendanceTime(f);

	}
	
	
	public void managerUpdateDB(AttendanceChangeForm f, String preCheckinTime, String preCheckoutTime, String comment) {
		f.setPreCheckinTime(preCheckinTime);
		f.setPreCheckoutTime(preCheckoutTime);
		attendancesDAO.managerUpdateDB(f);
	}

}
