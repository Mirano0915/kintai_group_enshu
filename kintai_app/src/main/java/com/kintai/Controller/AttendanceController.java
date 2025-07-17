package com.kintai.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kintai.Entity.AttendancesEntity;
import com.kintai.Entity.StampsEntity;
import com.kintai.Form.AttendanceChangeForm;
import com.kintai.Service.AttendanceService;
import com.kintai.Service.AuthService;

@Controller
public class AttendanceController {

    // AttendanceServiceの用意
    private final AttendanceService attendanceService;
    
    // AuthServiceの用意
    private final AuthService authService;
    
    public AttendanceController(AttendanceService attendanceService, AuthService authService) {
        this.attendanceService = attendanceService;
        this.authService = authService;
    }

    /**
     * 勤怠一覧画面表示
     */
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

    /**
     * 勤怠削除の処理（管理者モードのみ）
     */
    @PostMapping("/delete-attendance")
    public String deleteAttendance(@RequestParam("nameId") Long nameId,
                                 @RequestParam(value = "mode", defaultValue = "manager") String mode,
                                 HttpSession session) {
        
        // 管理者権限チェック
        if (authService.isManagerLoggedIn(session)) {
            attendanceService.deleteAttendance(nameId);
        }
        
        return "redirect:/attendance?mode=" + mode;
    }

    /**
     * 勤怠更新の処理（管理者モードのみ）
     */
    @PostMapping("/update-attendance")
    public String updateAttendance(@ModelAttribute AttendancesEntity attendance,
                                 @RequestParam(value = "mode", defaultValue = "manager") String mode,
                                 HttpSession session) {
        
        // 管理者権限チェック
        if (authService.isManagerLoggedIn(session)) {
            attendanceService.updateAttendance(attendance);
        }
        
        return "redirect:/attendance?mode=" + mode;
    }

    /**
     * 勤怠作成の処理（管理者モードのみ）
     */
    @PostMapping("/create-attendance")
    public String createAttendance(@ModelAttribute AttendancesEntity attendance,
                                 @RequestParam(value = "mode", defaultValue = "manager") String mode,
                                 HttpSession session) {
        
        // 管理者権限チェック
        if (authService.isManagerLoggedIn(session)) {
            AttendancesEntity newAttendance = new AttendancesEntity();
            newAttendance.setName(attendance.getName());
            newAttendance.setCheckinTime(attendance.getCheckinTime());
            newAttendance.setCheckoutTime(attendance.getCheckoutTime());
            newAttendance.setDate(attendance.getDate());
            
            attendanceService.createAttendance(newAttendance);
        }
        
        return "redirect:/attendance?mode=" + mode;
    }

    /**
     * 変更申請の処理（従業員モード）
     */
    @PostMapping("/change-request")
    public String submitChangeRequest(@ModelAttribute AttendanceChangeForm changeForm,
                                    @RequestParam("nameId") Long nameId,
                                    @RequestParam("name") String name) {
        
        // AttendanceChangeForm から StampsEntity に変換
        StampsEntity stampsEntity = new StampsEntity();
        stampsEntity.setNameId(nameId);
        stampsEntity.setName(name);
        stampsEntity.setPreCheckinTime(changeForm.getPreCheckinTime());
        stampsEntity.setPreCheckOutTime(changeForm.getPreCheckOutTime());
        stampsEntity.setComment(changeForm.getComment());
        
        attendanceService.submitChangeRequest(stampsEntity);
        
        return "redirect:/change-complete";
    }

    /**
     * メインページに戻る
     */
    @GetMapping("/back-to-main")
    public String backToMain() {
        return "redirect:/";
    }
}