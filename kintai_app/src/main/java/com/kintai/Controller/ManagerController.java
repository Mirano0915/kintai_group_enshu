package com.kintai.Controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    
//  管理者画面表示
  @GetMapping("/manager")
  public String showManagerPage(HttpSession session) {
      
      // 管理者ログインチェック
      if (!authService.isManagerLoggedIn(session)) {
          return "redirect:/auth";
      }
      
      return "manager";
  }
    
  
//    AJAX用 - 承認待ち件数取得API
    @GetMapping("/api/pending-count")
    @ResponseBody
    public Map<String, Integer> getPendingCount(HttpSession session) {
        Map<String, Integer> response = new HashMap<>();
        
        try {
            // 管理者権限チェック
            if (!authService.isManagerLoggedIn(session)) {
                throw new SecurityException("管理者権限がありません");
            }
            
            // サービスから承認待ち件数を取得
            int pendingCount = managerService.getPendingRequestCount();
            response.put("count", pendingCount);
        } catch (Exception e) {
            // すべてのエラー（権限なし・その他）で0を返す
            response.put("count", 0);
        }
        
        return response;
    }
        
}
