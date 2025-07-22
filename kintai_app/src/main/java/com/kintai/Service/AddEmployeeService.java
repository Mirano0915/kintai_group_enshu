package com.kintai.Service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.kintai.Form.PayrollForm;

@Service
public class AddEmployeeService {

    private final JdbcTemplate db;

    public AddEmployeeService(JdbcTemplate db) {
        this.db = db;
    }

    // 従業員を追加
    public void addEmployee(PayrollForm payrollForm) {
        // 名前の空チェック
        if (payrollForm.getName() == null || payrollForm.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("名前を入力してください");
        }
        
        // 時給の範囲チェック
        if (payrollForm.getHourlyWage() == null) {
            throw new IllegalArgumentException("時給を入力してください");
        }
        
        if (payrollForm.getHourlyWage() < 900) {
            throw new IllegalArgumentException("時給は900円以上で入力してください");
        }
        
        if (payrollForm.getHourlyWage() > 2000) {
            throw new IllegalArgumentException("時給は2000円以下で入力してください");
        }
        
        String sql = "INSERT INTO hourly_wages (name, hourly_wage) VALUES (?, ?)";
        
        db.update(sql, 
                 payrollForm.getName(), 
                 payrollForm.getHourlyWage());
        
        System.out.println("新しい従業員を追加しました: " + payrollForm.getName() + 
                          " (時給: " + payrollForm.getHourlyWage() + "円)");
    }
}