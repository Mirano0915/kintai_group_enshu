package com.kintai.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kintai.Service.AuthService;
import com.kintai.Service.PayrollService;

@Controller
public class PayrollController {

	//Payrollのサービスオブジェクトを準備
	@Autowired
	private PayrollService payrollService;
	@Autowired
	private AuthService authService;

	//給与計算＆給与計算画面遷移
	@GetMapping("/payroll")
	public String payroll(HttpSession session,
			@RequestParam(value = "filterMonth", required = false) String filterMonth,
			Model model) {
		//  管理者Session権限验证

		if (!authService.isManagerLoggedIn(session)) {
			return "redirect:/auth";
		}
		
		payrollService.payroll(model, filterMonth);
		return "payroll";
	}
}
