package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.dao.BookingDao;
import com.cdac.dao.PackageDao;
import com.cdac.dao.UserDao;
import com.cdac.dao.VehicleDao;
import com.cdac.dao.WasherDao;
import com.cdac.dto.AuthResp;
import com.cdac.dto.SignInDto;
import com.cdac.dto.SignupReqDto;
import com.cdac.dto.UserRequestDto;
import com.cdac.dto.UserResponseDto;
import com.cdac.dto.VehicleDto;
import com.cdac.entities.Role;
import com.cdac.entities.UserEntity;
import com.cdac.security.JwtUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
    public UserResponseDto getUserByEmail(String email) {
        UserEntity user = userDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setId(user.getId());
        userResponse.setFullName(user.getFullName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setPhone(user.getPhone());
        userResponse.setIsActive(user.getIsActive());
        userResponse.setVehicles(user.getVehicles().stream()
                .map(vehicle -> {
                    VehicleDto vehicleDto = new VehicleDto();
                    vehicleDto.setId(vehicle.getId());
                    vehicleDto.setUserId(vehicle.getUser().getId());
                    vehicleDto.setModel(vehicle.getModel());
                    vehicleDto.setType(vehicle.getType());
                    vehicleDto.setRegNumber(vehicle.getRegNumber());
                    return vehicleDto;
                })
                .toList());

        return userResponse;
    }
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }

//	private UserEntity toEntity(UserRequestDto dto) {
//		UserEntity user = new UserEntity();
//		user.setFullName(dto.getFullName());
//		user.setEmail(dto.getEmail());
//		user.setPassword(dto.getPassword());
//		user.setPhone(dto.getPhone());
//		user.setIsActive(dto.getIsActive());
//		user.setRole(dto.getRole());
////	        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
//		return user;
//	}

	private UserResponseDto toResponseDto(UserEntity entity) {
		UserResponseDto dto = new UserResponseDto();
		dto.setId(entity.getId());
		dto.setFullName(entity.getFullName());
		dto.setEmail(entity.getEmail());
		dto.setPhone(entity.getPhone());
		dto.setIsActive(entity.getIsActive());
		dto.setRole(entity.getRole());
		
		// map vehicles (include id)
        List<VehicleDto> vehicles = entity.getVehicles().stream().map(vehicle -> {
            VehicleDto v = new VehicleDto();
            v.setId(vehicle.getId());
            v.setUserId(entity.getId());
            v.setModel(vehicle.getModel());
            v.setType(vehicle.getType());
            v.setRegNumber(vehicle.getRegNumber());
            return v;
        }).collect(Collectors.toList());

        dto.setVehicles(vehicles);
		
		
		return dto;
	}

//	@Override
//	public UserResponseDto createUser(UserRequestDto userDto) {
//		UserEntity user = toEntity(userDto);
//
//		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
//		user.setPassword(hashedPassword);
//
//		return toResponseDto(userDao.save(user));
//	}
	
	

	@Override
	public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
		UserEntity user = userDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

		user.setFullName(userDto.getFullName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setPhone(userDto.getPhone());
		user.setIsActive(userDto.getIsActive());
		user.setRole(userDto.getRole());
//	        user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));

		return toResponseDto(userDao.save(user));
	}

	
	
//	@Override
//    @Transactional
//    public UserResponseDto getUserById(Long id, String requesterEmail) {
//        // load the target user
//        UserEntity user = userDao.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
//
//        if (requesterEmail == null) {
//            throw new AccessDeniedException("Authentication required");
//        }
//
//        // if requester is same user -> allow
//        if (requesterEmail.equalsIgnoreCase(user.getEmail())) {
//            return toResponseDto(user);
//        }
//
//        // otherwise check requester role (must be ADMIN)
//        UserEntity requester = userDao.findByEmail(requesterEmail)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "email", requesterEmail));
//
//        if (requester.getRole() != Role.ADMIN) {
//            throw new AccessDeniedException("You are not authorized to access this resource");
//        }
//        
//     // requester is admin -> allowed
//        return toResponseDto(user);
//    }
//	
	
	

	@Override
	public List<UserResponseDto> getAllUsers() {
		return userDao.findAll().stream().map(this::toResponseDto).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Long id) {
		UserEntity user = userDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
		userDao.delete(user);
	}

	@Transactional
	@Override
	public UserResponseDto signUp(SignupReqDto dto) {
		// Check if user already exists (optional: add this if you want to prevent
		// duplicate emails)

		if (userDao.findByEmail(dto.getEmail()).isPresent()) {
			throw new IllegalArgumentException("User with this email already exists.");
		}

		UserEntity user = new UserEntity();
		user.setFullName(dto.getFullName());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔒 Hash the password
		user.setPhone(dto.getPhone());
		user.setIsActive(dto.getIsActive());
		user.setRole(dto.getRole());

		UserEntity savedUser = userDao.save(user);
		return toResponseDto(savedUser);
	}

	
	
	

}
