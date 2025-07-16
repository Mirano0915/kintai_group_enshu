package com.kintai.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	//Viewのサービスオブジェクト準備
//	@Autowired
//	private IndexService indexService;
	
	
	//勤怠管理画面表示
	@RequestMapping("/")
	public String read() {
	return "index";
	}
}
