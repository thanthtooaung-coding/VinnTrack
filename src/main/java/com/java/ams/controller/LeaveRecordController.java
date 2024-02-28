package com.java.ams.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.ams.dto.LeaveRecordDto;
import com.java.ams.dto.UserDto;
import com.java.ams.mapper.LeaveRecordMapper;
import com.java.ams.mapper.UserMapper;
import com.java.ams.model.LeaveRecord;
import com.java.ams.model.LeaveType;
import com.java.ams.model.Notification;
import com.java.ams.model.User;
import com.java.ams.service.LeaveRecordService;
import com.java.ams.service.LeaveTypeService;
import com.java.ams.service.NotificationService;
import com.java.ams.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/leave")
public class LeaveRecordController {
	private final NotificationService notificationService;
	private final LeaveTypeService leaveTypeService;
	private final LeaveRecordService leaveRecordService;
	private final UserService userService;
	private final UserMapper userMapper;
	private final LeaveRecordMapper leaveRecordMapper;

	@GetMapping({ "", "/" })
	public ModelAndView attendance(HttpSession session, RedirectAttributes redirectAttributes) {
	    ModelAndView modelAndView = new ModelAndView();
	    UserDto currentUser = (UserDto) session.getAttribute("currentUser");
	    if (currentUser != null) {
	        modelAndView.addObject("currentUser", currentUser);
	        session.setAttribute("currentUser", currentUser);
	        int notificationCount = notificationService.getNotificationCount();
	        modelAndView.addObject("notificationCount", notificationCount);

	        List<Notification> notifications = notificationService.getFiveNotifications();
	        modelAndView.addObject("notifications", notifications);
	        
	        List<LeaveType> allLeaveTypes = leaveTypeService.getAllLeaveTypes();
			modelAndView.addObject("allLeaveTypes", allLeaveTypes);
			
			List<LeaveRecord> allLeaveRecords = leaveRecordService.getAllLeaveRecords();
			for(LeaveRecord leaveRecord : allLeaveRecords) {
				User leaveUser = userService.getUserById(leaveRecord.getUser().getId());
				leaveRecord.setUser(leaveUser);
			}
			modelAndView.addObject("allLeaveRecords", allLeaveRecords);						
			
			String success = (String) redirectAttributes.getFlashAttributes().get("success");
	        if (success != null) {
	            modelAndView.addObject("success", success);
	        }			
	        
	        modelAndView.addObject("leaveRecordDto", new LeaveRecordDto());
	        
	        modelAndView.setViewName("leave/leaveRecord");
	    } else {
	        modelAndView.setViewName("redirect:/login");
	    }	    

	    return modelAndView;
	}

	@PostMapping("/requestLeave")
	private String requestLeave(@ModelAttribute("leaveRecordDto") LeaveRecordDto leaveRecordDto, ModelMap model,
			HttpSession session, RedirectAttributes redirectAttributes) {
		System.out.println(leaveRecordDto.toString());
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		leaveRecordDto.setUser(userMapper.dtoToBean(currentUser));
		LeaveType findLeaveType = leaveTypeService.getLeaveTypeById(leaveRecordDto.getLeaveTypeId());
		leaveRecordDto.setLeaveType(findLeaveType);
		leaveRecordDto.setStatus("Pending");
		leaveRecordService.save(leaveRecordMapper.dtoToBean(leaveRecordDto));
		model.addAttribute("success", "true");
		redirectAttributes.addFlashAttribute("success", "true");
		return "redirect:/leave";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") int id) {
		leaveRecordService.deleteById(id);
		return "redirect:/leave";
    }
	
	@GetMapping("/accept/{id}")
	public String accept(@PathVariable(value = "id") int id) {
		leaveRecordService.acceptById(id);
		return "redirect:/leave";
    }
	
	@GetMapping("/reject/{id}")
	public String reject(@PathVariable(value = "id") int id) {
		leaveRecordService.rejectById(id);
		return "redirect:/leave";
    }
}
