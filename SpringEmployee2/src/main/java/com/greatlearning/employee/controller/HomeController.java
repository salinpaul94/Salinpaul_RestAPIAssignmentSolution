package com.greatlearning.employee.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatlearning.employee.dao.UserRepository;
import com.greatlearning.employee.entity.Employee;
import com.greatlearning.employee.entity.User;
import com.greatlearning.employee.helper.Message;
import com.greatlearning.employee.service.EmployeeServiceInterface;

@Controller
public class HomeController {
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping("/inde")
	public String dashboard(Model model, Principal principal)
	{
		String username = principal.getName();
		System.out.println(username);
		
		User user = userRepository.getUserByName(username);
		
		model.addAttribute("user", user);
		System.out.println();
		System.out.println();
		System.out.println("USER" + user);
		String role = user.getRole();
		System.out.println("USER TYPE:"+role);
		System.out.println(role);
		
		if(role.equals("ROLE_ADMIN"))
		{
			return "redirect:/admin/index";
		}
			return "redirect:/user/index";
		
	}

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	// handler for registering user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			@RequestParam(value = "userrole", defaultValue = "false") boolean userrole, Model model,
			 HttpSession session) {

		try {
			if (!agreement) {
				System.out.println("You have not agreed the terms and conditions");
				throw new Exception("You have not agreed the terms and conditions");
			}
			
			if(result1.hasErrors())
			{
				System.out.println("ERROR" + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			if (!userrole) {
				user.setRole("ROLE_USER");
			}
			else
			{
				user.setRole("ROLE_ADMIN");
			}
			
			user.setEnabled(true );
			user.setImageUrl("userdefault.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("Agreement" + agreement);
			System.out.println("user" + user);
			
			User result = this.userRepository.save(user);
			System.out.println(result);

			model.addAttribute("user", new User());
			
			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something Went wrong !!" + e.getMessage(),"alert-danger"));
			return "signup";
		}

		
	}

	//handler for customLogin
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		model.addAttribute("title", "Login Page");
		return "login";
	}
	
	
	
}
