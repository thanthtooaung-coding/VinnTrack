package com.java.ams.dto;

import java.util.List;

import com.java.ams.model.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PositionDto {
	private int id;
	
	@NotEmpty(message = "Position name is required")
    @Size(min = 3, max = 50, message = "Position name must be between 3 and 50 characters")
    private String name;
	private double salary;
	
	private List<User> users;

	@Override
	public String toString() {
		return "PositionDto [id=" + id + ", name=" + name + ", salary=" + salary + ", users=" + users + "]";
	}
	
}
