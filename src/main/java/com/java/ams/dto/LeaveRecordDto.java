package com.java.ams.dto;

import java.sql.Date;

import com.java.ams.model.LeaveType;
import com.java.ams.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LeaveRecordDto {
	private int id;
	    
	private String status;
	private String reason;
	
	private Date startDate;
	private Date endDate;
	private boolean isAccepted;
	    
	private LeaveType leaveType;
	private Integer leaveTypeId;
	
	private User user;
	private String leaveUserName;
	private String leaveUserEmail;
	private String leaveUserPhoneNumber;
	
	@Override
	public String toString() {
		return "LeaveRecordDto [id=" + id + ", status=" + status + ", reason=" + reason + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", isAccepted=" + isAccepted + ", leaveType=" + leaveType + ", leaveTypeId="
				+ leaveTypeId + ", user=" + user + ", leaveUserName=" + leaveUserName + ", leaveUserEmail="
				+ leaveUserEmail + ", leaveUserPhoneNumber=" + leaveUserPhoneNumber + "]";
	}	  
}
