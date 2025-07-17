package com.kintai.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintai.Service.PayrollService;

@Controller
public class PayrollController {

	//Payrollのサービスオブジェクトを準備
	@Autowired
	private PayrollService payrollService;
	
	
	//給与計算＆給与計算画面遷移
	@RequestMapping("/payroll")
	public String payroll(Model model) {
		payrollService.payroll(model);
		return "payroll";
	}
}
