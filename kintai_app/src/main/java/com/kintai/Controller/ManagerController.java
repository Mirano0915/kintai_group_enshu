package com.kintai.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kintai.Service.AuthService;
import com.kintai.Service.ManagerService;

@Controller
public class ManagerController {

    private final AuthService authService;
    private final ManagerService managerService;

    public ManagerController(AuthService authService, ManagerService managerService) {
        this.authService = authService;
        this.managerService = managerService;
    }

    /**
     * 管理者メイン画面表示（申請件数付き）
     * 申請件数表示 + 3つのナビゲーションボタン
     */
    @GetMapping("/manager-main")
    public String showManagerPage(HttpSession session, Model model) {
        
        // 🔒 管理者Session権限验证
        if (!authService.isManagerLoggedIn(session)) {
            return "redirect:/auth";
        }
        
        // 承認待ちの申請件数を取得してページに渡す
        int pendingRequestCount = managerService.getPendingRequestCount();
        model.addAttribute("pendingCount", pendingRequestCount);
        
        return "manager";
    }
}