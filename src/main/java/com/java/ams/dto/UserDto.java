package com.java.ams.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.java.ams.model.Department;
import com.java.ams.model.Notification;
import com.java.ams.model.Position;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {	
	private int id;
	
	@NotEmpty(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String name;		
	
	@NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @NotEmpty(message = "Password is required")
    private String password;
    
    //private String image;
    private LocalDate dob;
    private String gender;
    private String address;
    
    @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 13 digits")
    private String phone;
    private String role;
    
    private LocalDate joinDate;
    
    private Position position;
    
    private Department department;
    
    private List<Notification> notifications;
    
    private String username;
    private String otp;
    private String confirmPassword;
    private MultipartFile image;
    
    private String imageOutput;
    
    private Integer departmentId;
    private Integer positionId;
    
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", dob=" + dob
				+ ", gender=" + gender + ", address=" + address + ", phone=" + phone + ", role=" + role + ", joinDate="
				+ joinDate + ", position=" + position + ", department=" + department + ", notifications="
				+ notifications + ", username=" + username + ", otp=" + otp + ", confirmPassword=" + confirmPassword
				+ ", image=" + image + ", imageOutput=" + imageOutput + ", departmentId=" + departmentId
				+ ", positionId=" + positionId + "]";
	}   
}
