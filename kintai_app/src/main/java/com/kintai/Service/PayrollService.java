package com.kintai.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.kintai.Dao.HourlyWagesDAO;
import com.kintai.Entity.HourlyWagesEntity;

public class PayrollService {

	
	//DB操作が必要なためHourlyWagesDAOを準備
	@Autowired
	private HourlyWagesDAO hourlyWagesDAO;
	
	
	//勤怠テーブルと時給テーブルを呼びだし＆給与計算
	public void payroll(Model model) {
		List<HourlyWagesEntity>resultDb2 = hourlyWagesDAO.readDb();
		System.out.println(resultDb2.get(0).getNameId());
		
	}
}
