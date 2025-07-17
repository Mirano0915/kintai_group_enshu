package com.kintai.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kintai.Service.AuthService;

@Controller
public class AuthController {

    // AuthServiceの用意
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    
//      ログイン画面表示
     
    @GetMapping("/auth")
    public String showAuth() {
        return "auth";
    }

    
//      ログイン処理
    
    @PostMapping("/auth")
    public String processAuth(@RequestParam("password") String password, 
                            HttpSession session, 
                            Model model) {
        
        if (authService.authenticateAdmin(password)) {
            // ログイン成功
            authService.setManagerSession(session);
            return "redirect:/manager";
        } else {
            // ログイン失敗
            model.addAttribute("error", "パスワードが間違っています");
            return "auth";
        }
    }

    
//      ログアウト処理
     
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "redirect:/";
    }

    
//     管理者画面表示
    
    @GetMapping("/manager")
    public String showManagerPage(HttpSession session) {
        
        // ログインチェック
        if (!authService.isManagerLoggedIn(session)) {
            return "redirect:/auth";
        }
        
        return "manager";
    }
    
}

