package com.java.ams.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.ams.dto.AttendanceRecordDto;
import com.java.ams.dto.UserDto;
import com.java.ams.model.AttendanceRecord;
import com.java.ams.model.Notification;
import com.java.ams.service.AttendanceRecordService;
import com.java.ams.service.NotificationService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {
	private final NotificationService notificationService;
	private final AttendanceRecordService attendanceService;

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

	        modelAndView.addObject("attendanceDto", new AttendanceRecordDto());

	        List<AttendanceRecord> attendance = attendanceService.getRecordsNotForToday();
	        modelAndView.addObject("allAttendances", attendance);

	        String done = (String) redirectAttributes.getFlashAttributes().get("done");
	        if (done != null) {
	            modelAndView.addObject("done", done);
	        }	        

	        modelAndView.setViewName("attendance/attendanceRecord");
	    } else {
	        modelAndView.setViewName("redirect:/login");
	    }

	    ZonedDateTime currentTime5 = ZonedDateTime.now(ZoneId.systemDefault());
	    ZonedDateTime targetTime5 = currentTime5.withHour(17).withMinute(0).withSecond(0).withNano(0);

	    if (currentTime5.isAfter(targetTime5)) {
	    	attendanceService.setAbsentIfNotClicked();
	        List<AttendanceRecord> attendance = attendanceService.getRecordsNotPresentForToday();
	        modelAndView.addObject("allAttendances", attendance);
	    }
	    ZonedDateTime currentTime8 = ZonedDateTime.now(ZoneId.systemDefault());
	    ZonedDateTime targetTime8 = currentTime8.withHour(20).withMinute(0).withSecond(0).withNano(0);

	    if (currentTime8.isAfter(targetTime8)) {	    	
	        List<AttendanceRecord> attendance = attendanceService.getAllAttendanceRecord();
	        modelAndView.addObject("allAttendances", attendance);
	    }

	    return modelAndView;
	}

	@PostMapping("/takeAttendance")
	private String takeAttendance(@ModelAttribute("attendanceDto") AttendanceRecordDto attendanceDto, ModelMap model,
			HttpSession session, RedirectAttributes redirectAttributes) {
		System.out.println("hiiiiiiiiiiiiiii");
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		boolean isClicked = attendanceService.takeAttendance(attendanceDto, currentUser.getId());
		if (isClicked) {
			boolean isFirstClick = attendanceService.isFirstClickToday(currentUser.getId());
			if (isFirstClick) {
				attendanceService.storeFirstClick(currentUser.getId());
				model.addAttribute("done", "You have clicked one time at " + attendanceDto.getTimeInTest());
				redirectAttributes.addFlashAttribute("done",
						"You have clicked one time at " + attendanceDto.getTimeIn());
				System.out.println("done");
				return "redirect:/attendance";
			} else {
				return "redirect:/attendance";
			}
		} else {
			return "redirect:/attendance";
		}
	}
}
