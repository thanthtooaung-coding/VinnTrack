package com.java.ams.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java.ams.dto.UserDto;
import com.java.ams.model.Department;
import com.java.ams.model.Position;
import com.java.ams.model.Role;
import com.java.ams.model.User;
import com.java.ams.service.DepartmentService;
import com.java.ams.service.EmailSenderService;
import com.java.ams.service.NotificationService;
import com.java.ams.service.PasswordGenerator;
import com.java.ams.service.PositionService;
import com.java.ams.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final NotificationService notificationService;
	private final UserService userService;
	private final DepartmentService departmentService;
	private final PositionService positionService;
	private final EmailSenderService emailService;

	@GetMapping("/add")
	public ModelAndView addUserPage(HttpSession session, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		// Check if the user is logged in
		if (currentUser != null) {
			modelAndView.addObject("currentUser", currentUser);
			session.setAttribute("currentUser", currentUser);
			int notificationCount = notificationService.getNotificationCount();
			modelAndView.addObject("notificationCount", notificationCount);
			model.addAttribute("departmentId", "");
			model.addAttribute("positionId", "");
			modelAndView.addObject("userDto", new UserDto());
			List<Department> departments = departmentService.getAllDepartments();
			modelAndView.addObject("departments", departments);
			List<Position> positions = positionService.getAllPositions();
			modelAndView.addObject("positions", positions);			
			modelAndView.setViewName("employee/addEmployee");
		} else {
			modelAndView.setViewName("redirect:/login");
		}

		return modelAndView;
	}

	@PostMapping("/checkEmail")
	private ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
		boolean exists = userService.duplicateUser(email);
		if (exists) {
			return ResponseEntity.badRequest().body("Email already exists.");
		} else {
			return ResponseEntity.ok().build();
		}
	}

	@PostMapping("/save")
	private String addUser(@ModelAttribute("userDto") UserDto userDto, ModelMap model) {
		userDto.setRole(Role.EMPLOYEE.name());
		userDto.setPassword(PasswordGenerator.generatePassword(8));
		Department department = new Department();
		department.setId(userDto.getDepartmentId());
		userDto.setDepartment(department);
		Position position = new Position();
		position.setId(userDto.getPositionId());
		userDto.setPosition(position);
		// Check if the email already exists
		boolean exists = userService.duplicateUser(userDto.getEmail());
		if (exists) {
			model.addAttribute("error", "Email already exists.");
			model.addAttribute("userDto", userDto);
			return "redirect:/user/add";
		}

		boolean save = userService.save(userDto);
		if (!save) {
			model.addAttribute("error", "Employee Create Failed!!!");
			model.addAttribute("userDto", userDto);
			return "redirect:/user/add";
		}
		emailService.sendEmail(userDto.getEmail(),
				"Password For VinnTrack : Employee Attendance And Payroll Management System",
				"Email - " + userDto.getEmail() + "\n" + "Password - " + userDto.getPassword());
		return "redirect:/user/add";
	}
	@GetMapping("/view")
	public ModelAndView viewUserPage(HttpSession session, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		// Check if the user is logged in
		if (currentUser != null) {
			modelAndView.addObject("currentUser", currentUser);
			session.setAttribute("currentUser", currentUser);
			int notificationCount = notificationService.getNotificationCount();
			modelAndView.addObject("notificationCount", notificationCount);
			List<User> users = userService.getAllByRole(Role.EMPLOYEE.name());			
			modelAndView.addObject("users", users);
			List<Department> departments = departmentService.getAllDepartments();
			modelAndView.addObject("departments", departments);
			List<Position> positions = positionService.getAllPositions();
			modelAndView.addObject("positions", positions);
			int deptId = currentUser.getDepartmentId();
			model.addAttribute("departmentId", deptId);
			model.addAttribute("positionId", "");
			modelAndView.addObject("userDto", new UserDto());
			modelAndView.setViewName("employee/viewEmployee");
		} else {
			modelAndView.setViewName("redirect:/login");
		}
		
		return modelAndView;
	}
	@PostMapping("/edit")
	private String editUser(@ModelAttribute("userDto") UserDto userDto, @RequestParam int id,
			ModelMap model) {		
		Department department = new Department();
		department.setId(userDto.getDepartmentId());
		userDto.setDepartment(department);
		Position position = new Position();
		position.setId(userDto.getPositionId());
		userDto.setPosition(position);
		System.out.println(userDto.toString());
		userService.update(id, userDto);
		return "redirect:/user/view";
	}
	@PostMapping("/delete")
	public String deleteUser(@RequestParam("id") int id) {
		userService.delete(id);
		return "redirect:/user/view";
	}

}
