package com.kintai.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kintai.Entity.AttendancesEntity;
import com.kintai.Service.AttendanceService;
import com.kintai.Service.AuthService;

@Controller
public class AttendanceController {

	
    private final AttendanceService attendanceService;
    private final AuthService authService;
    

    public AttendanceController(AttendanceService attendanceService, AuthService authService) {
        this.attendanceService = attendanceService;
        this.authService = authService;
    }

    // 勤怠一覧画面表示
    @GetMapping("/attendance")
    public String showAttendanceList(HttpSession session, Model model) {

        // 管理者かどうかチェック
        boolean isManager = authService.isManagerLoggedIn(session);

        // 管理者なら"manager"、そうでなければ"employee"
        if (isManager) {
            model.addAttribute("mode", "manager");
        } else {
            model.addAttribute("mode", "employee");
        }
  

        // データベースから従業員データを取得
        List<AttendancesEntity> employeeList = attendanceService.getAllAttendanceData();
        model.addAttribute("employeeList", employeeList);

        return "attendance";
    }


    // AJAX削除API（管理者のみ）
    @PostMapping("/api/delete-attendance")
    @ResponseBody
    public Map<String, Object> deleteAttendanceAjax(@RequestBody Map<String, Object> requestData,
                                                   HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        // 管理者かどうかチェック
        boolean isManager = authService.isManagerLoggedIn(session);

        // 管理者じゃない場合は拒否
        if (!isManager) {
            response.put("success", false);
            response.put("message", "管理者権限が必要です");
            return response;
        }

        try {
            Long attendanceId = Long.valueOf(requestData.get("attendanceId").toString());
            attendanceService.deleteAttendance(attendanceId);

            response.put("success", true);
            response.put("message", "削除が完了しました");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "削除に失敗しました: " + e.getMessage());
        }

        return response;
    }

    // メインページに戻る
    @GetMapping("/back-to-main")
    public String backToMain() {
        return "redirect:/";
    }
}