package com.qa.servingapicontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@GetMapping("/rabo")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="all API tests were successful") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

}
