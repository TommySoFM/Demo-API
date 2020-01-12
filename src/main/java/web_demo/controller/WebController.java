package web_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

	@GetMapping("/")
	public String showHome() {
		return "home";
	}


	@GetMapping("/admin")
	public String showAdmin() {
		return "admin";
	}

//	@PostMapping("/testing")
//	@ResponseBody
//	public String test(@RequestBody String test) {
//		return test;
//	}
}










