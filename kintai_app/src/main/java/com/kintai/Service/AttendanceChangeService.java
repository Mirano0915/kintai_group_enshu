package com.kintai.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kintai.Dao.StampsDAO;
import com.kintai.Form.AttendanceChangeForm;

@Service
public class AttendanceChangeService {

	//DB操作が必要なためStampsDAOを準備
	@Autowired
	private StampsDAO stampsDAO;

	//変更後の出勤・退勤時間、コメントを入力

	public void attendanceRegister(AttendanceChangeForm form) {
		// まだ実装しないけど、動作確認のため
		System.out.println("出勤時間：" + form.getPreCheckinTime());
		System.out.println("退勤時間：" + form.getPreCheckoutTime());
		System.out.println("コメント：" + form.getComment());
	}

//	public void attendanceRegister(AttendanceChangeForm form) {
//		stampsDAO.updateAttendanceTime //名前はくの君にきく  
//		(
//				form.getPreCheckinTime(),
//				form.getPreCheckoutTime(),
//				form.getComment());
//
//	}

}
