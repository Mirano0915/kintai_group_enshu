package com.kintai.Service;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private static final String ADMIN_PASSWORD = "team3";
    
    
//     管理者認証
    public boolean authenticateAdmin(String password) {
        return ADMIN_PASSWORD.equals(password);
    }
    
    
//    管理者セッション設置
    public void setManagerSession(HttpSession session) {
        session.setAttribute("isManagerLoggedIn", true);
        
    }
    
    
//     管理者ログイン状態の確認（ログインしてるかどうか）
    public boolean isManagerLoggedIn(HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isManagerLoggedIn");
        return isLoggedIn != null && isLoggedIn;
    }
    
    
//     ログアウト処理 
    public void logout(HttpSession session) {
        session.invalidate();
    }
}