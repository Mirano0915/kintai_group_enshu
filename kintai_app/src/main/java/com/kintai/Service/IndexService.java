package com.kintai.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kintai.Dao.AttendancesDAO;

@Service
public class IndexService {

	//DB操作が必要なためAttendancesDaoを準備
	@Autowired
	private AttendancesDAO attendancesDAO;
	
	
	//従業員の名前を所得
	public void readName(Model model) {
		
	}
	
	
	//出勤処理
	public void checkin() {
		
	}
	
	
	//退勤処理
	public void checkout() {
		
	}
}
