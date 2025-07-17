package com.kintai.Controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
 
//     勤怠一覧画面表示
//      設計書の「勤怠一覧.html」に対応
     
    @GetMapping("/attendance")
    public String showAttendanceList(@RequestParam(value = "mode", defaultValue = "employee") String mode,
                                   HttpSession session,
                                   Model model) {
        
        // 管理者モードの場合はログインチェック
        if ("manager".equals(mode)) {
            if (!authService.isManagerLoggedIn(session)) {
                return "redirect:/auth";
            }
        }
        
        model.addAttribute("attendanceService", attendanceService);
        model.addAttribute("mode", mode);
        
        return "attendance";
    }

    
//      AJAX用勤怠更新API（管理者のみ）
     
    @PostMapping("/api/update-attendance")
    @ResponseBody
    public Map<String, Object> updateAttendanceAjax(@RequestBody Map<String, Object> requestData,
                                                   HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 管理者権限チェック
            if (!authService.isManagerLoggedIn(session)) {
                response.put("success", false);
                response.put("message", "権限がありません");
                return response;
            }
            
            // リクエストデータを取得
            Long nameId = Long.valueOf(requestData.get("nameId").toString());
            String checkinTime = (String) requestData.get("checkinTime");
            String checkoutTime = (String) requestData.get("checkoutTime");
            
            // AttendancesEntityを作成（実際のDAOが完成するまでは仮実装）
            AttendancesEntity attendance = new AttendancesEntity();
            attendance.setNameId(nameId);

            
            
            // サービス層で更新処理
            attendanceService.updateAttendance(attendance);
            
            response.put("success", true);
            response.put("message", "更新が完了しました");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新に失敗しました: " + e.getMessage());
        }
        
        return response;
    }

    
//     AJAX用勤怠削除API（管理者のみ）
     
    @PostMapping("/api/delete-attendance")
    @ResponseBody
    public Map<String, Object> deleteAttendanceAjax(@RequestBody Map<String, Object> requestData,
                                                   HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 管理者権限チェック
            if (!authService.isManagerLoggedIn(session)) {
                response.put("success", false);
                response.put("message", "権限がありません");
                return response;
            }
            
            // リクエストデータを取得
            Long nameId = Long.valueOf(requestData.get("nameId").toString());
            
            // サービス層で削除処理
            attendanceService.deleteAttendance(nameId);
            
            response.put("success", true);
            response.put("message", "削除が完了しました");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "削除に失敗しました: " + e.getMessage());
        }
        
        return response;
    }


//     インデックスに戻る
    
    @GetMapping("/back-to-index")
    public String backToMain() {
        return "redirect:/";
    }
}