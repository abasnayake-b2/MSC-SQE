package com.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rms.entity.User;
import com.rms.service.UserService;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserRestController {

	@Autowired
	private UserService userService;

//	//get All User
//	@GetMapping("/users")
//	public List<User> getAllUser(){
//		
//		return userService.findAll();
//	}
	
	
	@GetMapping("/home")
	public ModelAndView viewHomePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/home");
		return modelAndView;
	}
	
	// get User by Id
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {

		return userService.getUserByID(id);
	}

	//Create new User by Admin
	@GetMapping("/userCreate")
	public ModelAndView getUserCreationForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listRoles", userService.getRoles());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/usercreateform");
		return modelAndView;
	}
	
	//Save new User
	@PostMapping("/saveUser")
	public String saveUser(User user){
		 userService.save(user);
		 return "redirect:/user/list_users";
	}


	// edit user
	@GetMapping("/edituser/{id}")
	public ModelAndView editUser(@PathVariable("id") Long id, Model model) {
		User user = getUserById(id);

		model.addAttribute("user", user);
		model.addAttribute("listRoles", userService.getRoles());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/usereditform");
		return modelAndView;
	}

	// update user
	@PostMapping("/updateUser")
	public String updateUser(User user, Model model) {
		userService.updateUser(user);

		return "redirect:/user/list_users";
	}

	// get register user form
	@GetMapping("/userRegister")
	public ModelAndView getRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/registration");
		return modelAndView;
	}

	@PostMapping("/process_register")
	public ModelAndView processRegistration(User user) {

		userService.saveUserWithDefaultRole(user);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/register_success");
		return modelAndView;
	}

	@GetMapping("/list_users")
	public ModelAndView viewUserList(Model model) {

		List<User> listUsers = userService.findAll();
		model.addAttribute("listUsers", listUsers);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/users");
		return modelAndView;

	}

	
	//delete User by Id
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable Long id, Model model){
		userService.deleteUserByID(id);

		return "redirect:/user/list_users";
	}
	
	
//
//	// user login
//	
//	@GetMapping("/login")
//	public ModelAndView getLoginForm(Model model) {
//		model.addAttribute("user", new User());
//
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("pages/loginform");
//		return modelAndView;
//	}
//	
	//return error page
	@GetMapping("/403")
	public ModelAndView error403(Model model) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/403");
		return modelAndView;
	}
	
}
