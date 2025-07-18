package com.kintai.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kintai.Entity.StampsEntity;
import com.kintai.Service.AttendanceAgreeService;

@Controller
public class AttendanceAgreeController {
	//AttendanceAgreeのサービスオブジェクトを準備
	@Autowired
	private AttendanceAgreeService attendanceAgreeService;

	//勤怠変更申請ページ遷移＆一覧
	@GetMapping("/attendanceAgree")
	public String attendanceAgree(Model model) {
		List<StampsEntity> changeRequests = attendanceAgreeService.getAttendanceAgreeList();
        model.addAttribute("changeRequests", changeRequests);
		return "attendanceAgree";
	}
}
