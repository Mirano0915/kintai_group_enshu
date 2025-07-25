package com.kintai.Service;

import org.springframework.stereotype.Service;

import com.kintai.Dao.HourlyWagesDAO;
import com.kintai.Form.PayrollForm;

@Service
public class AddEmployeeService {

    private final HourlyWagesDAO hourlyWagesDAO;

    public AddEmployeeService(HourlyWagesDAO hourlyWagesDAO) {
        this.hourlyWagesDAO = hourlyWagesDAO;
    }

 // 従業員を追加（同姓同名チェック付き）
    public boolean addEmployee(PayrollForm payrollForm) {
        // 同姓同名チェック
        if (hourlyWagesDAO.existsByName(payrollForm.getName())) {
            return false; // 同姓同名が存在する場合はfalseを返す
        }
        
        // 同姓同名がない場合は追加処理
        hourlyWagesDAO.addEmployee(payrollForm.getName(), payrollForm.getHourlyWage());
        return true; // 追加成功
    }
}