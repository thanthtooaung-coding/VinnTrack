package com.java.ams.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java.ams.dto.DepartmentDto;
import com.java.ams.dto.UserDto;
import com.java.ams.model.Department;
import com.java.ams.service.DepartmentService;
import com.java.ams.service.NotificationService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
	private final NotificationService notificationService;
	private final DepartmentService departmentService;

	@GetMapping({ "", "/" })
	public ModelAndView addDepartmentPage(HttpSession session, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		if (currentUser != null) {
			modelAndView.addObject("currentUser", currentUser);
			session.setAttribute("currentUser", currentUser);
			int notificationCount = notificationService.getNotificationCount();
			modelAndView.addObject("notificationCount", notificationCount);
			modelAndView.addObject("departmentDto", new DepartmentDto());
			List<Department> departments = departmentService.getAllDepartments();
			modelAndView.addObject("departments", departments);
			modelAndView.setViewName("department/addDepartment");
		} else {
			modelAndView.setViewName("redirect:/login");
		}

		return modelAndView;
	}

	@PostMapping("/save")
	private String addDepartment(@ModelAttribute("departmentDto") DepartmentDto departmentDto, ModelMap model) {
		departmentService.save(departmentDto);
		return "redirect:/department";
	}

	@PostMapping("/edit")
	private String editDepartment(@ModelAttribute("departmentDto") DepartmentDto departmentDto, @RequestParam int id,
			ModelMap model) {
		departmentService.update(id, departmentDto);
		return "redirect:/department";
	}

	@PostMapping("/delete")
	public String deleteDepartment(@RequestParam int id) {
		try {
			departmentService.deleteDepartment(id);
		} catch (Exception ex) {
			System.out.println("Exception : " + ex.getMessage());

		}
		return "redirect:/department";
	}
}
