package com.java.ams.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="leave_record")
@Getter
@Setter
@RequiredArgsConstructor
public class LeaveRecord implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String status;
    private String reason;
    
    private Date startDate;
    private Date endDate;
    private boolean isAccepted;
    
    @ManyToOne
    @JoinColumn(name = "leave_type_id", referencedColumnName = "id")
    private LeaveType leaveType;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
