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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.greatlearning.employee.dao.EmployeeRepository;
import com.greatlearning.employee.dao.UserRepository;
import com.greatlearning.employee.entity.Employee;
import com.greatlearning.employee.entity.User;
import com.greatlearning.employee.helper.Message;
import com.greatlearning.employee.service.EmployeeServiceInterface;


@Controller
@RequestMapping("/admin")
public class AdminController {

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
		return "admin/user_dashboard";
	}

	// open add form handler
	@GetMapping("/add-employee")
	public String openAddEmployeeForm(Model model)
	{
		model.addAttribute("title", "Add Employee");
		model.addAttribute("employee", new Employee());
		return "admin/add_employee_form";
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
		
		return "admin/add_employee_form";
	}
	
	//show employee handler
	
	@GetMapping("/show-employees")
	public String showEmployee(Model model)
	{
		model.addAttribute("title", "Show User Contacts");
		
		List<Employee> employee = this.employeeRepository.findAll();
		
		model.addAttribute("employee", employee);
		return "admin/show-employees";
	}
	
	//Showing particular employee details.
	
		@RequestMapping("/{id}/employee")
		public String showContactDetail(@PathVariable("id") Integer id, Model model)
		{
			System.out.println("\n\n\n");
			System.out.println("Id" + id);
			System.out.println("\n\n\n");
			
			Optional<Employee> employeeOptional = this.employeeRepository.findById(id);
			Employee employee = employeeOptional.get();
			
			model.addAttribute("employee", employee);
			
			return "admin/employee_detail";
		}
		
		// Delete employee handler
		@GetMapping("/delete/{id}")
		public String deleteContact(@PathVariable("id") Integer id, HttpSession session)
		{
			Optional<Employee> employeeid = this.employeeRepository.findById(id);
			Employee employee = employeeid.get();
			
			this.employeeRepository.delete(employee);
			session.setAttribute("message", new Message("Contact deleted Successfully", "success"));
			return "redirect:/admin/show-employees";
		}
		
		// Open update form handler
		@PostMapping("/update-employee/{id}")
		public String updateForm(@PathVariable("id") Integer id,Model model)
		{
			model.addAttribute("title", "Update Employee");
			
			Optional<Employee> employeeOptional = this.employeeRepository.findById(id);
			Employee employee = employeeOptional.get();
			
			model.addAttribute("employee", employee);
			return "/admin/update_form";
		}
		
		// Process employee update details
		@RequestMapping(value="/porcess-update-employee", method = RequestMethod.POST)
		public String updateHandler(@ModelAttribute Employee employee,
									@RequestParam("profileImage") MultipartFile file,
									HttpSession session)
		{
			try {
				//Old contact details
				Employee oldDetail = this.employeeRepository.findById(employee.getId()).get();
				System.out.println(oldDetail);
				
				//image
				if(!file.isEmpty())
				{
					// file work...
					// rewrite
					
					//delete old photo
					
					File deletefile = new ClassPathResource("static/image").getFile();
					File file1= new File(deletefile, oldDetail.getImage());
					file1.delete();
					
					//update new photo
					
					File savefile = new ClassPathResource("static/image").getFile();
					
					Path path = Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
					
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("File saved");
					
					employee.setImage(file.getOriginalFilename());
				} else
				{
					employee.setImage(oldDetail.getImage());
					System.out.println(employee.getImage());
				}
				this.employeeRepository.save(employee);
				
				//success message
				session.setAttribute("message", new Message("Your contact is updated...", "success"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			System.out.println("Employee Name:- " + employee.getFirstName());
			System.out.println("Employee Name:- " + employee.getId());
			
			return "redirect:/admin/"+employee.getId()+"/employee";
		}
		
		//profile handler
		@GetMapping("/profile")
		public String yourProfile(Model model)
		{
			model.addAttribute("title", "Profile Page");
			return "admin/profile";
		}
}