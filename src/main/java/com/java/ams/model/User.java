package com.java.ams.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@RequiredArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "Username is required")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String name;

	private String username;

	@NotEmpty(message = "Email is required")
	@Email(message = "Please provide a valid email address")
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@NotEmpty(message = "Password is required")
	private String password;

	private String image;
	private Date dob;
	private String gender;
	private String address;

	@Pattern(regexp = "\\d{10,15}", message = "Phone number must be 13 digits")
	private String phone;
	private String role;

	private LocalDate joinDate;

	@ManyToOne
	@JoinColumn(name = "position_id", referencedColumnName = "id")
	private Position position;

	@ManyToOne
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Department department;
	
	@OneToMany(mappedBy = "user")
	private List<AttendanceRecord> attendanceRecords;
	
	@OneToMany(mappedBy = "user")
	private List<LeaveRecord> leaveRecords;

	@OneToMany(mappedBy = "user")
	private List<Notification> notifications;

	@PrePersist
	protected void setJoinDateToCurrentDate() {
		joinDate = LocalDate.now();
	}

	public int calculateAge() {
		if (dob == null) {
			throw new IllegalStateException("Date of birth is not set for this user");
		}
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(dob.toLocalDate(), currentDate);
		return period.getYears();
	}
}
