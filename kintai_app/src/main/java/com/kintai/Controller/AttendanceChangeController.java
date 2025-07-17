package com.kintai.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintai.Form.AttendanceChangeForm;
import com.kintai.Service.AttendanceChangeService;

@Controller
public class AttendanceChangeController {

	@Autowired
	private AttendanceChangeService attendanceChangeService;

	//変更申請画面表示

//	@RequestMapping("/attendanceChange")
	@GetMapping("/attendanceChange")
	public String view() {
		return "attendanceChange"; // ← templates/attendanceChange.html を返す
	}

	//送信処理
	@PostMapping("/completeChange")
	public String submitAttendanceForm(@ModelAttribute AttendanceChangeForm form) {
		attendanceChangeService.attendanceRegister(form);
		return "completeChange";
	}
}
