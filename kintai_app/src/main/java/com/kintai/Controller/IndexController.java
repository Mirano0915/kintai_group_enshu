package com.kintai.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintai.Dao.AttendancesDAO;
import com.kintai.Dao.HourlyWagesDAO;
import com.kintai.Service.IndexService;

@Controller
public class IndexController {

	//Viewのサービスオブジェクト準備
	@Autowired
	private IndexService indexService;

	@Autowired
	private HourlyWagesDAO hourlyWagesDAO;

	@Autowired
	private AttendancesDAO attendancesDAO;

	//勤怠管理画面表示
	@GetMapping("/")
	public String attendanceAgree(Model model) {

		indexService.readName(model);
		System.out.println("メソッド呼ばれた");
		return "index";
	}

	@RequestMapping("/checkin")
	public String checkin(
			@RequestParam("employeeName") String name,
			RedirectAttributes redirectAttributes,
			Model model) {

		Long nameId = hourlyWagesDAO.getNameIdByName(name);

		if (attendancesDAO.hasCheckedInToday(nameId)) {
			model.addAttribute("errorMessage", "今日はもう出勤済みです！");
			indexService.readName(model);
			return "index";

		} else if (attendancesDAO.forgotCheckedInToday(nameId)) {
			model.addAttribute("errorMessage", "不正な操作です　勤務時間の変更は申請を行って下さい");
			indexService.readName(model);
			return "index";
		}

		// 出勤処理
		String checkTime = indexService.checkin(nameId);

		// 完了画面に渡す情報を一時保存（リダイレクト用）
		redirectAttributes.addAttribute("type", "checkin");
		redirectAttributes.addAttribute("employeeName", name);
		redirectAttributes.addAttribute("checkTime", checkTime);

		return "redirect:/complete";
	}

	//退勤処理(退勤ボタン)
	@RequestMapping("/checkout")
	public String checkout(
			@RequestParam("employeeName") String name,
			RedirectAttributes redirectAttributes,
			Model model) {

		Long nameId = hourlyWagesDAO.getNameIdByName(name);

		if (attendancesDAO.hasCheckedoutToday(nameId)) {
			model.addAttribute("errorMessage", "今日はもう退勤済みです！");
			indexService.readName(model);
			return "index";

		}
		// 出勤処理
		String checkTime = indexService.checkout(nameId);

		// 完了画面に渡す情報を一時保存（リダイレクト用）
		redirectAttributes.addAttribute("type", "checkout");
		redirectAttributes.addAttribute("employeeName", name);
		redirectAttributes.addAttribute("checkTime", checkTime);

		return "redirect:/complete";
	}
	
	
	 @GetMapping("/complete")
	    public String showCompletePage(
	            @RequestParam("type") String type,
	            @RequestParam("employeeName") String name,
	            @RequestParam(value = "checkTime", required = false) String checkTime,
	            Model model) {

	        model.addAttribute("type", type);
	        model.addAttribute("employeeName", name);
	        model.addAttribute("checkTime", checkTime);
	        return "complete";
	    }

}
