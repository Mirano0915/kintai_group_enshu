package com.kintai.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.kintai.Dao.HourlyWagesDAO;

public class PayrollService {

	
	//DB操作が必要なためHourlyWagesDAOを準備
	@Autowired
	private HourlyWagesDAO hourlywagesDAO;
	
	
	//勤怠テーブルと時給テーブルを呼びだし＆給与計算
	public void payroll(Model model) {
		
	}
}
