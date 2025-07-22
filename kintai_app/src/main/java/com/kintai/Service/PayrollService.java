package com.kintai.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kintai.Dao.HourlyWagesDAO;
import com.kintai.Entity.HourlyWagesEntity;

@Service
public class PayrollService {

	
	//DB操作が必要なためHourlyWagesDAOを準備
	@Autowired
	private HourlyWagesDAO hourlyWagesDAO;
	
	
	//給与情報を取得
	public void payroll(Model model) {
		List<HourlyWagesEntity>resultDb2 = hourlyWagesDAO.readDb();
		model.addAttribute("payrollList", resultDb2);
		
	}
}
