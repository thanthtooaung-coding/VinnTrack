package com.java.ams.service.implement;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.ams.model.User;
import com.java.ams.dto.UserDto;
import com.java.ams.mapper.UserMapper;
import com.java.ams.repository.UserRepository;
import com.java.ams.service.EmailSenderService;
import com.java.ams.service.LeaveRecordService;
import com.java.ams.service.UserService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

	private final UserRepository repo;
	private final UserMapper userMapper;
	private final String uploadDir = "public/images/";
	private final String defaultImage = "default-image.png";
	private final EmailSenderService emailService;
	private final LeaveRecordService leaveRecordService;

	@Override
	public boolean save(UserDto userDto) {
		String storageFileName = null;
		MultipartFile image = userDto.getImage();
		if (image != null) {

			Date createdAt = new Date();
			storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

			try {
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try (InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				}
			} catch (Exception ex) {
				System.out.println("Exception : " + ex.getMessage());
			}
		} else {
			storageFileName = defaultImage;
		}
		String hashPassword = this.hashPassword(userDto.getPassword());
		User userBean = userMapper.dtoToBean(userDto);
		userBean.setPassword(hashPassword);
		userBean.setImage(storageFileName);
		repo.save(userBean);
		return true;
	}

	@Override
	public boolean update(int id, UserDto userDto) {
		User userBean = this.getUserById(id);
		if (userBean == null) {
			throw new IllegalArgumentException("User not found with id: " + id);
		}
		MultipartFile image = userDto.getImage();
		String storageFileName = null;
		if (image != null) {
			try {
				if (!userDto.getImage().isEmpty()) {
					if (userBean.getImage() != null) {
						Path oldImagePath = Paths.get(uploadDir + userBean.getImage());
						Files.delete(oldImagePath);
					}
					Date createdAt = new Date();
					storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
					try (InputStream inputStream = image.getInputStream()) {
						Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
								StandardCopyOption.REPLACE_EXISTING);
					}
					userDto.setImageOutput(storageFileName);
				}
			} catch (Exception ex) {
				System.out.println("IOException occurred: " + ex.getMessage());
				return false;
			}

		} else {
			userDto.setImageOutput(userBean.getImage());
		}
		userDto.setPassword(userBean.getPassword());
		userDto.setJoinDate(userBean.getJoinDate());
		userDto.setRole(userBean.getRole());
		User updatedUser = userMapper.dtoToBean(userDto);
		updatedUser.setImage(userDto.getImageOutput());
		repo.save(updatedUser);
		return true;
	}
	
	@Override
	public User updateProfileImage(UserDto userDto, int id) {
		User userBean = this.getUserById(id);
		if (userBean == null) {
			throw new IllegalArgumentException("User not found with id: " + id);
		}
		MultipartFile image = userDto.getImage();
		String storageFileName = null;
		if (image != null) {
			try {
				if (!userDto.getImage().isEmpty()) {
					if (userBean.getImage() != null) {
						Path oldImagePath = Paths.get(uploadDir + userBean.getImage());
						Files.delete(oldImagePath);
					}
					Date createdAt = new Date();
					storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
					try (InputStream inputStream = image.getInputStream()) {
						Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
								StandardCopyOption.REPLACE_EXISTING);
					}
					userBean.setImage(storageFileName);
				}
			} catch (Exception ex) {
				System.out.println("IOException occurred: " + ex.getMessage());
				return null;
			}

		}
		repo.save(userBean);
		return userBean;
	}
	
	@Override
	public User updateProfileName(UserDto userDto, int id) {
		User userBean = this.getUserById(id);
		if (userBean == null) {
			throw new IllegalArgumentException("User not found with id: " + id);
		}
		if (userDto.getName() != null) {
			userBean.setName(userDto.getName());
		}
		repo.save(userBean);
		return userBean;	
	}
	
	@Override
    public boolean changePassword(int id,UserDto userDto) {
        User userBean = this.getUserById(id);
        if (userBean == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        
        String hashPassword = this.hashPassword(userDto.getPassword());
        userBean.setPassword(hashPassword);
        repo.save(userBean);
        
        return true;
    }
	
	@Override
	public User getUserById(int id) {
		return repo.findById(id);
	}

	@Override
	public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
	
	@Override
	public User findUser(String email) {
		return repo.findUserByEmail(email);
	}

	@Override
	public boolean checkPassword(String inputPassword, String hashedPassword) {
		return BCrypt.checkpw(inputPassword, hashedPassword);
	}

	@Override
	public boolean duplicateUser(String email) {
		System.out.println("Duplicate : " + repo.existsByEmail(email));
		return repo.existsByEmail(email);
	}

	@Override
	public int getUserCount() {
		return (int) repo.count();
	}

	@Override
	public List<User> getAllUsers() {
		return repo.findAllWithPosition();
	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);
	}

	@Override
	public List<User> getAllByRole(String role) {
		return repo.findAllByRole(role);
	}

	@Override
	public int getCountByRole(String role) {
		// TODO Auto-generated method stub
		return repo.countByRole(role);
	}	
	
	@Scheduled(cron = "0 50 16 * * *")
    @Override
    public void sendEmailToAllUsers() {
		String subject = "Remind for Taking Attendance";
		String message = "Hi, it is 4:50 PM. Please open VinnTrack and click the 'Take Attendance' button.";
        List<User> users = getAllByRole("EMPLOYEE");
        for (User user : users) {
            emailService.sendEmail(user.getEmail(), subject, message);
        }
    }
	
	@Override
	public double calculateSalary(int id) {
		
		User findUser = getUserById(id);		
		
		LocalDate currentDate = LocalDate.now();
		
		long yearsOfService = ChronoUnit.YEARS.between(findUser.getJoinDate(), currentDate);

		double positionSalary = findUser.getPosition().getSalary();

		double additionalSalaryPerYear = 100000.0;

		double salary = positionSalary + (additionalSalaryPerYear * yearsOfService);

		double leaveDaySalaryReduction = positionSalary / 30.0;
		
		long totalLeaveDays = leaveRecordService.getLeaveDaysByUserId(id);
		
		salary -= totalLeaveDays * leaveDaySalaryReduction;

		return salary;
	}
}
