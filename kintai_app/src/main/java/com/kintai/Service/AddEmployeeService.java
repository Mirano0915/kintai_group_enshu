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
        String sql = "INSERT INTO hourly_wages (name, hourly_wage) VALUES (?, ?)";
        
        db.update(sql, 
                 payrollForm.getName(), 
                 payrollForm.getHourlyWage());
        
        System.out.println("新しい従業員を追加しました: " + payrollForm.getName() + 
                          " (時給: " + payrollForm.getHourlyWage() + "円)");
    }
}