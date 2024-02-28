package com.java.ams.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
public class CustomErrorController implements ErrorController {

	@GetMapping("/error")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handleError(NoHandlerFoundException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/errorPage");
		return modelAndView;
	}
}
