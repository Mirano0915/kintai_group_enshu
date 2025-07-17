package com.kintai.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintai.Service.IndexService;

@Controller
public class IndexController {

	//Viewのサービスオブジェクト準備
	@Autowired
	private IndexService indexService;
	
	
	//勤怠管理画面表示
	@RequestMapping("/")
	public String view(Model model) {
		indexService.readName(model);
		System.out.println("メソッド呼ばれた");
		return "index";
	}
	
	
	//出勤処理（出勤ボタン）
	@RequestMapping("/checkin")
	public String checkin() {
		indexService.checkin();
		return "complete";
	}
	
	
	//退勤処理(退勤ボタン)
	@RequestMapping("/checkout")
	public String checkout() {
		indexService.checkout();
		return "complete";
	}
	
}
