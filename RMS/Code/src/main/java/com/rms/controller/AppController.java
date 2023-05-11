package com.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.rms.entity.User;
import com.rms.service.UserService;

@Controller
public class AppController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String viewHomePage() {
		return "index";
	}

//	// get register user form
//	@GetMapping("/userRegister")
//	public String getRegistrationForm(Model model) {
//		model.addAttribute("user", new User());
//		return "registration";
//	}
//
//	@PostMapping("/process_register")
//	public String processRegistration(User user) {
//
//		userService.saveUserWithDefaultRole(user);
//		return "register_success";
//	}
//
//	@GetMapping("/list_users")
//	public String viewUserList(Model model) {
//
//		List<User> listUsers = userService.findAll();
//		model.addAttribute("listUsers", listUsers);
//
//		return "users";
//	}

}
