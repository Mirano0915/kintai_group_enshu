package com.kintai.Service;

import java.time.LocalDate;
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
	public void payroll(Model model, String filterMonth) {
		
		// 現在の月を取得（初めてのページ遷移の場合に初期値設定）
	    if (filterMonth == null || filterMonth.isEmpty()) {
	        filterMonth = String.valueOf(LocalDate.now().getMonthValue());  // 今月を初期値に設定
	    }
	    model.addAttribute("filterMonth", filterMonth);
		
		List<HourlyWagesEntity>resultDb2 = hourlyWagesDAO.readDb(filterMonth);
		model.addAttribute("payrollList", resultDb2);
		
	}
}
