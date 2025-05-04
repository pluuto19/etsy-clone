package project.semester.aspm.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.semester.aspm.userservice.dto.UserDto;
import project.semester.aspm.userservice.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/verify/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<UserDto> verifyEmail(@PathVariable Long id) {
        UserDto userDto = userService.verifyEmail(id);
        return ResponseEntity.ok(userDto);
    }
    
    @PostMapping("/{id}/resend-verification")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String, String>> resendVerificationToken(@PathVariable Long id) {
        String token = userService.createVerificationToken(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Verification token has been sent");
        // In a real application, we would not return the token directly
        // For debugging purposes only:
        response.put("token", token);
        
        return ResponseEntity.ok(response);
    }
} 