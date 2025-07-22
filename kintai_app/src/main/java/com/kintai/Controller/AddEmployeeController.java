package com.kintai.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintai.Form.PayrollForm;
import com.kintai.Service.AddEmployeeService;

@Controller
public class AddEmployeeController {

    private final AddEmployeeService addEmployeeService;

    public AddEmployeeController(AddEmployeeService addEmployeeService) {
        this.addEmployeeService = addEmployeeService;
    }

    // 従業員追加画面表示
    @GetMapping("/add-employee")
    public String showAddEmployeePage(Model model) {
        model.addAttribute("payrollForm", new PayrollForm());
        return "addEmployee";
    }

    // 従業員追加処理
    @PostMapping("/add-employee")
    public String addEmployee(@ModelAttribute PayrollForm payrollForm, Model model) {

        try {
            // 従業員を追加 (Service内でバリデーション実行)
            addEmployeeService.addEmployee(payrollForm);
            model.addAttribute("successMessage", "従業員が正常に追加されました");
            model.addAttribute("payrollForm", new PayrollForm()); 
        } catch (IllegalArgumentException e) {
            // バリデーションエラー
            model.addAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            // その他のエラー
            model.addAttribute("errorMessage", "従業員の追加に失敗しました: " + e.getMessage());
        }

        return "addEmployee";
    }
}