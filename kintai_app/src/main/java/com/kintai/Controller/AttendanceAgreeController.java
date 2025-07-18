package com.kintai.Controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

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
import com.kintai.Service.AuthService;

@Controller
public class AttendanceAgreeController {
	//AttendanceAgreeのサービスオブジェクトを準備
	@Autowired
	private AttendanceAgreeService attendanceAgreeService;
	@Autowired
	private StampsDAO stampsDAO;
	@Autowired
    private AuthService authService;

	//勤怠変更申請ページ遷移＆一覧
	@GetMapping("/attendanceAgree")
	public String attendanceAgree(HttpSession session, Model model) {
        
        //  管理者Session権限验证
    	
		 if (!authService.isManagerLoggedIn(session)) {
	            return "redirect:/auth";
	        }
		List<StampsEntity> changeRequests = attendanceAgreeService.getAttendanceAgreeList();
		model.addAttribute("changeRequests", changeRequests);
		return "attendanceAgree";
	}

	//打刻変更の承認ajax
	@PostMapping("/approve-request")
	@ResponseBody
	public String approveRequest(@RequestParam Long stampId, HttpSession session) {
		
		 //  管理者Session権限验证
		if (!authService.isManagerLoggedIn(session)) {
            return "redirect:/auth";
        }
		stampsDAO.deleteAttendanceAgree(stampId);
		return "success";
	}
	
	//打刻変更の却下ajax
	@PostMapping("/deny-request")
	@ResponseBody
	public String denyRequest(@RequestParam Long stampID, HttpSession session) {
		
		 //  管理者Session権限验证
		if (!authService.isManagerLoggedIn(session)) {
            return "redirect:/auth";
        }
		stampsDAO.deleteAttendanceAgree(stampID);
		return "success";
	}

}
