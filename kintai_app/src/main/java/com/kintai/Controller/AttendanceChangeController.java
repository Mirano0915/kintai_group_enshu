package com.kintai.Controller;

import java.util.Set;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintai.Dao.HourlyWagesDAO;
import com.kintai.Form.AttendanceChangeForm;
import com.kintai.Interface.EmployeeGroup;
import com.kintai.Service.AttendanceChangeService;
import com.kintai.Service.AuthService;

@Controller
public class AttendanceChangeController {

	@Autowired
	private AttendanceChangeService attendanceChangeService;
	@Autowired
	private HourlyWagesDAO hourlyWagesDAO;
	@Autowired
	private AuthService authService;

	//変更申請画面表示
	@GetMapping("/attendance-change-form")
	public String showAttendanceChangeForm(@RequestParam Long attendanceId,
			HttpSession session,
			Model model) {

		// 管理者かどうかチェック
		boolean isManager = authService.isManagerLoggedIn(session);

		// 管理者なら"manager"、そうでなければ"employee"
		if (isManager) {
			model.addAttribute("mode", "manager");
		} else {
			model.addAttribute("mode", "employee");
		}

		AttendanceChangeForm attendanceChangeForm = attendanceChangeService.setCheckTime(attendanceId);

		attendanceChangeForm.setAttendanceId(attendanceId);
		attendanceChangeForm.setNameId(attendanceChangeService.getNameId(attendanceId));

		System.out.println(attendanceChangeForm.getAttendanceId());
		model.addAttribute("form", attendanceChangeForm);

		return "attendanceChange";
	}

	//送信処理
	@PostMapping("/completeChange")
	public String submitAttendanceForm(
	    @ModelAttribute("form") AttendanceChangeForm form,
	    BindingResult bindingResult,
	    RedirectAttributes redirectAttributes,
	    HttpSession session,
	    Model model) {

		String name = form.getEmployeeName();
		
		// 管理者かどうかチェック
		boolean isManager = authService.isManagerLoggedIn(session);

		// 管理者なら"manager"、そうでなければ"employee"
		if (isManager) {

			model.addAttribute("mode", "manager");
			attendanceChangeService.managerUpdateDB(form);

			redirectAttributes.addAttribute("employeeName", name);
			redirectAttributes.addAttribute("type", "update");

			return "redirect:/complete";

		} else {
			model.addAttribute("mode", "employee");

			// 従業員用のバリデーションを手動で実行
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<AttendanceChangeForm>> violations = validator.validate(form, EmployeeGroup.class);

			if (!violations.isEmpty()) {
				for (ConstraintViolation<AttendanceChangeForm> violation : violations) {
					bindingResult.rejectValue("comment", "", violation.getMessage());
				}
				model.addAttribute("form", form);
				return "attendanceChange";
			}

			attendanceChangeService.attendanceRegister(form);
			return "completeChange";
		}
	}
}
