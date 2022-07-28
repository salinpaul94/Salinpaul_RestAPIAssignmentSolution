package com.greatlearning.employee.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.greatlearning.employee.dao.EmployeeRepository;
import com.greatlearning.employee.dao.UserRepository;
import com.greatlearning.employee.entity.Employee;
import com.greatlearning.employee.entity.User;
import com.greatlearning.employee.helper.Message;
import com.greatlearning.employee.service.EmployeeServiceInterface;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmployeeServiceInterface employeeService;
	
	@Autowired
	EmployeeRepository employeeRepository;

	//method for adding common data to response
	@ModelAttribute
	public void addCommonDate(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println(username);

		User user = userRepository.getUserByName(username);

		System.out.println("USER" + user);
		
		model.addAttribute("user", user);
	}

	// dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		return "normal/user_dashboard";
	}

	// open add form handler
	@GetMapping("/add-employee")
	public String openAddEmployeeForm(Model model)
	{
		model.addAttribute("title", "Add Employee");
		model.addAttribute("employee", new Employee());
		return "normal/add_employee_form";
	}
	
	//Processing add employee form
	@PostMapping("/porcess-employee")
	public String processEmployee(@ModelAttribute Employee employee, 
			@RequestParam("profileImage") MultipartFile file, HttpSession session) {
		
		try {
			System.out.println("Employee" + employee);
			
			//processing and uploading file...
			if(file.isEmpty())
			{
				//if the file is empty then try our message
				System.out.println("file is empty");
				employee.setImage("userdefault.png");
			} else {
				
				//upload the file to folder and update the name to contact
				employee.setImage(file.getOriginalFilename());
				
				File savefile = new ClassPathResource("static/image").getFile();
				
				Path path = Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("File saved");
			}
			
			/*
			 * if(3>2) { throw new Exception(); }
			 */
			employeeRepository.save(employee);
			
			System.out.println("Employee Added to database");
			
			// message success....
			
			session.setAttribute("message", new Message("Your contact is added !! Add more...", "success"));
			
		} catch (Exception e) {
			System.out.println("Error"+ e.getMessage());
			e.printStackTrace();
			//message error...
			session.setAttribute("message", new Message("something went wrond !!", "danger"));
		}
		
		return "normal/add_employee_form";
	}
	
	//show employee handler
	
	@GetMapping("/show-employees")
	public String showEmployee(Model model)
	{
		model.addAttribute("title", "Show User Contacts");
		
		List<Employee> employee = this.employeeRepository.findAll();
		
		model.addAttribute("employee", employee);
		return "normal/show-employees";
	}
	
	//Showing particular contact details.
	
	@RequestMapping("/{id}/employee")
	public String showContactDetail(@PathVariable("id") Integer id, Model model)
	{
		System.out.println("\n\n\n");
		System.out.println("Id" + id);
		System.out.println("\n\n\n");
		
		Optional<Employee> employeeOptional = this.employeeRepository.findById(id);
		Employee employee = employeeOptional.get();
		
		model.addAttribute("employee", employee);
		
		return "normal/employee_detail";
	}
	
	// profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model) 
	{
		model.addAttribute("title", "Profile Page");
		return "normal/profile";
	}
}
