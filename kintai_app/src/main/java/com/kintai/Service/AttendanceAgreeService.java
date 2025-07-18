package com.kintai.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.kintai.Dao.StampsDAO;
import com.kintai.Entity.StampsEntity;

public class AttendanceAgreeService {
	//DB操作が必要なためStampsDAOを準備
		@Autowired
		private StampsDAO stampsDAO;
		
		
		//勤怠テーブルと時給テーブルを呼びだし＆給与計算
		public void payroll(Model model) {
			List<StampsEntity>resultDb2 = stampsDAO.showAttendanceAgreeTable();
			System.out.println(resultDb2.get(0).getNameId());
		
}
}
