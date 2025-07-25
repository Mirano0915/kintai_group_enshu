package com.kintai.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CompleteController {

    @GetMapping("/complete")
    public String showCompletePage(
            @RequestParam("type") String type,
            @RequestParam("employeeName") String name,
            @RequestParam("checkTime") String checkTime,
            Model model) {

        model.addAttribute("type", type);
        model.addAttribute("employeeName", name);
        model.addAttribute("checkTime", checkTime);
        return "complete";
    }
}