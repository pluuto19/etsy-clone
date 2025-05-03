package project.semester.aspm.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.semester.aspm.userservice.dto.UserDto;
import project.semester.aspm.userservice.service.UserService;

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

    @GetMapping("/verify/{token}")
    public ResponseEntity<UserDto> verifyEmail(@PathVariable String token) {
        UserDto userDto = userService.verifyEmail(token);
        return ResponseEntity.ok(userDto);
    }
} 