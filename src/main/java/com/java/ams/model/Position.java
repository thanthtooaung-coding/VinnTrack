package com.java.ams.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="position")
@Getter
@Setter
@RequiredArgsConstructor
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "Position name is required")
    @Size(min = 3, max = 50, message = "Position name must be between 3 and 50 characters")
    private String name;
	
	private double salary;
	
	@OneToMany(mappedBy = "position")
    private List<User> users;
}
