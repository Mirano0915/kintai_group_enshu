package com.kintai.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String showAttendanceChangeForm(@RequestParam Long attendanceId,
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
		
		AttendanceChangeForm attendanceChangeForm = new AttendanceChangeForm();
		attendanceChangeForm.setAttendanceId(attendanceId);
		attendanceChangeForm.setNameId(attendanceChangeService.getNameId(attendanceId));
		
		
		System.out.println(attendanceChangeForm.getAttendanceId());
		model.addAttribute("form",attendanceChangeForm);
		
	
		
		return "attendanceChange";
	}

	//送信処理
	@PostMapping("/completeChange")
	public String submitAttendanceForm(@ModelAttribute AttendanceChangeForm form, 
										String preCheckinTime, 
										String preCheckoutTime, 
										String comment,
										HttpSession session,
										Model model) {
		
		
		
		// 管理者かどうかチェック
		boolean isManager = authService.isManagerLoggedIn(session);

		// 管理者なら"manager"、そうでなければ"employee"
		if (isManager) {
			model.addAttribute("mode", "manager");
			attendanceChangeService.managerUpdateDB(form, preCheckinTime, preCheckoutTime, comment);
			
		} else {
			model.addAttribute("mode", "employee");
			attendanceChangeService.attendanceRegister(form, preCheckinTime, preCheckoutTime, comment);
		}
		
		
		return "completeChange";
	}
}
