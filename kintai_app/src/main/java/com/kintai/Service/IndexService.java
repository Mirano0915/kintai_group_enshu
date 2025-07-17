package com.kintai.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kintai.Dao.AttendancesDAO;
import com.kintai.Entity.AttendancesEntity;

@Service
public class IndexService {

	//DB操作が必要なためAttendancesDaoを準備
	@Autowired
	private AttendancesDAO attendancesDAO;
	
	
	//従業員の名前取得
	public void readName(Model model) {
		List<AttendancesEntity>resultDb = attendancesDAO.readNameDb();
		model.addAttribute("nameLIst", resultDb);
	}
	
	
	//出勤処理
	public void checkin(String name) {
		attendancesDAO.checkin(name);
	}
	
	
	//退勤処理
	public void checkout(String name) {
		attendancesDAO.checkout(name);
	}
}
