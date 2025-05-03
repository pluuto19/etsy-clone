package project.semester.aspm.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.semester.aspm.userservice.dto.AuthRequest;
import project.semester.aspm.userservice.dto.AuthResponse;
import project.semester.aspm.userservice.dto.RegistrationRequest;
import project.semester.aspm.userservice.dto.UserDto;
import project.semester.aspm.userservice.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        UserDto userDto = userService.registerUser(registrationRequest);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = userService.authenticateUser(authRequest);
        return ResponseEntity.ok(authResponse);
    }
} 