package com.kintai.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kintai.Dao.StampsDAO;
import com.kintai.Entity.StampsEntity;
import com.kintai.Service.AttendanceAgreeService;

@Controller
public class AttendanceAgreeController {
	//AttendanceAgreeのサービスオブジェクトを準備
	@Autowired
	private AttendanceAgreeService attendanceAgreeService;
	@Autowired
	private StampsDAO stampsDAO;

	//勤怠変更申請ページ遷移＆一覧
	@GetMapping("/attendanceAgree")
	public String attendanceAgree(Model model) {
		List<StampsEntity> changeRequests = attendanceAgreeService.getAttendanceAgreeList();
		model.addAttribute("changeRequests", changeRequests);
		return "attendanceAgree";
	}

	//打刻変更の承認ajax
	@PostMapping("/approve-request")
	@ResponseBody
	public String approveRequest(@RequestParam Long stampId) {

		stampsDAO.deleteAttendanceAgree(stampId);
		return "success";
	}

}
