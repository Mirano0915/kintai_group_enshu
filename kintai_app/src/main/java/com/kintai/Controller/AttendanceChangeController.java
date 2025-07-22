package com.kintai.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintai.Form.AttendanceChangeForm;
import com.kintai.Service.AttendanceChangeService;
import com.kintai.Service.AttendanceService;
import com.kintai.Service.AuthService;


@Controller
public class AttendanceChangeController {

	@Autowired
	private AttendanceChangeService attendanceChangeService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AuthService authService;

	//変更申請画面表示
	@GetMapping("/attendance-change-form")
	public String showAttendanceChangeForm(//@RequestParam("nameId") Long nameId,
			HttpSession session,
			Model model) {

		
		
		// 管理者かどうかチェック
		boolean isManager = authService.isManagerLoggedIn(session);

		// 管理者なら"manager"、そうでなければ"employee"
		if (isManager) {
			model.addAttribute("mode", "manager");
		} else {
			model.addAttribute("mode", "employee");
		}

//		model.addAttribute("nameId", nameId);
//		model.addAttribute("name", name);

		return "attendanceChange";
	}

	//送信処理
	@PostMapping("/completeChange")
	public String submitAttendanceForm(@ModelAttribute AttendanceChangeForm form) {
		attendanceChangeService.attendanceRegister(form);
		return "completeChange";
	}
}
