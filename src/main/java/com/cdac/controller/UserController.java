package com.cdac.controller;

import com.cdac.dto.UserRequestDto;
import com.cdac.dto.UserResponseDto;
import com.cdac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
//@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
    private UserService userService;

//    @PostMapping
//    public UserResponseDto createUser(@RequestBody UserRequestDto userDto) {
//        return userService.createUser(userDto);
//    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto userDto) {
        return userService.updateUser(id, userDto);
    }

//    @GetMapping("/{id}")
//    public UserResponseDto getUser(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
    
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id, Authentication authentication) {
//        String requesterEmail = (authentication != null) ? authentication.getName() : null;
//        UserResponseDto dto = userService.getUserById(id, requesterEmail);
//        return ResponseEntity.ok(dto);
//    }
  
   
    
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}