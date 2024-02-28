package com.java.ams.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.ams.dto.PositionDto;
import com.java.ams.dto.UserDto;
import com.java.ams.model.Position;
import com.java.ams.service.NotificationService;
import com.java.ams.service.PositionService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/position")
public class PositionController {
	private final NotificationService notificationService;
	private final PositionService positionService;
	@GetMapping({ "", "/" })
	public ModelAndView addPositionPage(HttpSession session, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		if (currentUser != null) {
			modelAndView.addObject("currentUser", currentUser);
			session.setAttribute("currentUser", currentUser);
			int notificationCount = notificationService.getNotificationCount();
			modelAndView.addObject("notificationCount", notificationCount);
			modelAndView.addObject("positionDto", new PositionDto());
			List<Position> positions = positionService.getAllPositions();
			modelAndView.addObject("positions", positions);
			modelAndView.setViewName("position/addPosition");
		} else {
			modelAndView.setViewName("redirect:/login");
		}

		return modelAndView;
	}
	@PostMapping("/save")
	private String addPosition(@ModelAttribute("positionDto") PositionDto positionDto, ModelMap model) {
		System.out.println(positionDto.toString());
		positionService.save(positionDto);
		return "redirect:/position";
	}
}
