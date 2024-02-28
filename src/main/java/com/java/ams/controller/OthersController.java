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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.ams.dto.UserDto;
import com.java.ams.mapper.UserMapper;
import com.java.ams.model.Notification;
import com.java.ams.model.Role;
import com.java.ams.model.User;
import com.java.ams.service.AttendanceRecordService;
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
@RequestMapping("/")
public class OthersController {
	private final UserService userService;
	private final DepartmentService departmentService;
	private final PositionService positionService;
	private final NotificationService notificationService;
	private final AttendanceRecordService attendanceRecordService;
	private final EmailSenderService emailSender;
	private final UserMapper userMapper;

	@GetMapping({ "", "/", "/home" })
	public String home() {		
		return "home";
	}

	@GetMapping("/login")
	public ModelAndView login2() {
		return new ModelAndView("others/login", "login", new UserDto());
	}	

	@PostMapping("/authenticate")
	public String authenticate(@ModelAttribute("login") UserDto login, ModelMap model, HttpSession session) {
		User findUser = userService.findUser(login.getEmail());
		UserDto currentUser = new UserDto();

		if (findUser == null) {
			model.addAttribute("error", "Invalid Username or Password.");
			model.addAttribute("others/login", new UserDto());
			return "others/login";
		} else {
			if (findUser.getEmail().equals(login.getEmail())
					&& userService.checkPassword(login.getPassword(), findUser.getPassword()) == true) {
				if (findUser.getRole() != null) {
					String role = findUser.getRole();
					switch (role) {
					case "CEO":
						model.addAttribute("success", "CEO Login Successful!!!");
						break;
					case "ADMIN":
						model.addAttribute("success", "HR Admin Login Successful!!!");
						break;
					case "EMPLOYEE":
						model.addAttribute("success", "Employee Login Successful!!!");
						break;
					default:
						model.addAttribute("error", "Unknown Role");
						return "error";
					}
					
					currentUser.setId(findUser.getId());
					currentUser.setEmail(findUser.getEmail());
					currentUser.setName(findUser.getName());
					currentUser.setPhone(findUser.getPhone());
					currentUser.setRole(findUser.getRole());
					currentUser.setImageOutput(findUser.getImage());
					currentUser.setDepartmentId(findUser.getDepartment().getId());
					currentUser.setPosition(findUser.getPosition());
					currentUser.setDepartment(findUser.getDepartment());
					currentUser.setAddress(findUser.getAddress());
					session.setAttribute("currentUser", currentUser);
					return "redirect:/dashboard";
				}
			}
			model.addAttribute("error", "Invalid Username or Password.");
			return "others/login";
		}
	}

	@GetMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("signup", "register", new UserDto());
	}

	@PostMapping("/register")
	public String registration(@ModelAttribute("register") UserDto register, ModelMap model) {
		boolean validEmail = userService.duplicateUser(register.getEmail());
		if (validEmail) {
			model.addAttribute("error", "Duplicate Email!!!");
			return "signup";
		} else {
			register.setRole(Role.ADMIN.name());
			boolean save = userService.save(register);
			if (!save) {
				model.addAttribute("error", "User Create Failed!!!");
				return "signup";
			}
			model.addAttribute("success", "User Create Success!!!");
			return "redirect:/";
		}

	}

	@GetMapping("/forgotPassword")
	public ModelAndView forgotPassword(HttpSession session) {
		return new ModelAndView("others/forgotPassword", "userDto", new UserDto());
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@ModelAttribute("userDto") UserDto userDto, ModelMap model,
			RedirectAttributes redirectAttributes, HttpSession session) {
		User findUser = userService.findUser(userDto.getEmail());
		UserDto forgotUser = new UserDto();
		if (findUser != null) {
			int[] generatedOTP = PasswordGenerator.generateOTP(4);
			StringBuilder otpBuilder = new StringBuilder();
			for (int digit : generatedOTP) {
				otpBuilder.append(digit);
			}
			String otpString = otpBuilder.toString();
			forgotUser.setOtp(otpString);
			forgotUser.setEmail(findUser.getEmail());
			session.setAttribute("forgotUser", forgotUser);
			emailSender.sendMail(userDto.getEmail(), "VinnTrack OTP Verification", "Your OTP Number is " + otpString);
			return "redirect:/otpVerification";
		} else {
			redirectAttributes.addFlashAttribute("error", "Your email is invalid.");

			return "redirect:/forgotPassword";
		}
	}

	@GetMapping("/otpVerification")
	public ModelAndView otpVerification() {
		ModelAndView modelAndView = new ModelAndView("others/otpVerification");
		modelAndView.addObject("userDto", new UserDto());
		return modelAndView;
	}

	@PostMapping("/otpVerification")
	public String otpVerification(@ModelAttribute("userDto") UserDto userDto, ModelMap model,
			@RequestParam("otp") String[] otpArray, RedirectAttributes redirectAttributes, HttpSession session) {
		UserDto forgotUser = (UserDto) session.getAttribute("forgotUser");
		String enteredOTP = String.join("", otpArray);
		String generatedOTP = forgotUser.getOtp();
		if (generatedOTP.equals(enteredOTP)) {
			return "redirect:/createPassword";
		} else {
			model.addAttribute("error", "Invalid OTP. Please try again.");
			return "redirect:/otpVerification";
		}
	}

	@GetMapping("/createPassword")
	public ModelAndView createPassword() {
		ModelAndView modelAndView = new ModelAndView("others/createPassword");
		modelAndView.addObject("userDto", new UserDto());
		return modelAndView;
	}

	@PostMapping("/createPassword")
	public String createPassword(@ModelAttribute("userDto") UserDto userDto, ModelMap m, HttpSession session) {
		UserDto forgotUser = (UserDto) session.getAttribute("forgotUser");
		User changePasswordUser = userService.findUser(forgotUser.getEmail());
		forgotUser.setPassword(userDto.getPassword());
		if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
			userService.changePassword(changePasswordUser.getId(), forgotUser);
			return "redirect:/login";
		} else {
			m.addAttribute("error", "Please ensure that the new password matches the confirmed password.");			
			return "others/createPassword";
		}
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/login");
		session.invalidate();
		return modelAndView;
	} 

	@GetMapping("/dashboard")
	public ModelAndView dashboard(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		// Check if the user is logged in
		if (currentUser != null) {
			modelAndView.addObject("currentUser", currentUser);
			session.setAttribute("currentUser", currentUser);
			if (!currentUser.getRole().equals(Role.EMPLOYEE.name())) {
				int departmentCount = departmentService.getDepartmentCount();
				modelAndView.addObject("departmentCount", departmentCount);

				int positionCount = positionService.getPositionCount();
				modelAndView.addObject("positionCount", positionCount);

				int employeeCount = userService.getCountByRole(Role.EMPLOYEE.name());
				modelAndView.addObject("employeeCount", employeeCount);							

				int notificationCount = notificationService.getNotificationCount();
				modelAndView.addObject("notificationCount", notificationCount);
				
				int todayAttendanceCount = attendanceRecordService.getcountTodayAttendanceRecords();
				modelAndView.addObject("todayAttendanceCount", todayAttendanceCount);

				List<Notification> notifications = notificationService.getFiveNotifications();
				modelAndView.addObject("notifications", notifications);

				List<Notification> latestNotifications = notificationService.getLatestNotifications();

				modelAndView.addObject("latestNotifications", latestNotifications);
				modelAndView.setViewName("others/dashboard");
			} else {
				modelAndView.setViewName("redirect:/profile");
			}
		} else {
			modelAndView.setViewName("redirect:/login");
		}

		return modelAndView;
	}
	
	@GetMapping("/profile")
	public ModelAndView profile(HttpSession session, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		if (currentUser != null) {
			modelAndView.addObject("currentUser", currentUser);
			session.setAttribute("currentUser", currentUser);
			
			User profileUser = userService.getUserById(currentUser.getId());
			UserDto profileUserDto = userMapper.beanToDto(profileUser);
			profileUserDto.setImageOutput(profileUser.getImage());
			modelAndView.addObject("profileUser", profileUserDto);
			int notificationCount = notificationService.getNotificationCount();
			modelAndView.addObject("notificationCount", notificationCount);
			modelAndView.addObject("userDto", new UserDto());
			modelAndView.setViewName("others/profile");
		} else {
			modelAndView.setViewName("redirect:/login");
		}

		return modelAndView;
	}
	@PostMapping("/updateProfileImage")
	public String updateProfileImage(@ModelAttribute("userDto") UserDto userDto, ModelMap m, HttpSession session) {
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		User updatedUser = userService.updateProfileImage(userDto, currentUser.getId());
		currentUser.setImageOutput(updatedUser.getImage());
		return "redirect:/profile";
	}
	@PostMapping("/updateProfileName")
	public String updateProfileName(@ModelAttribute("userDto") UserDto userDto, ModelMap m, HttpSession session) {
		UserDto currentUser = (UserDto) session.getAttribute("currentUser");
		System.out.println(userDto.getName());
		User updatedUser = userService.updateProfileName(userDto, currentUser.getId());
		currentUser.setName(updatedUser.getName());
		return "redirect:/profile";
	}
}
