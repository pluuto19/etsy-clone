package project.semester.aspm.userservice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.semester.aspm.userservice.dto.AuthRequest;
import project.semester.aspm.userservice.dto.AuthResponse;
import project.semester.aspm.userservice.dto.RegistrationRequest;
import project.semester.aspm.userservice.dto.UserDto;
import project.semester.aspm.userservice.model.User;
import project.semester.aspm.userservice.repository.UserRepository;
import project.semester.aspm.userservice.security.JwtUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public UserDto registerUser(RegistrationRequest request) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        // Create a new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Set roles based on request
        Set<String> roles = new HashSet<>();
        roles.add("CUSTOMER"); // Every user is a customer
        
        if (request.isSeller()) {
            roles.add("SELLER");
        }
        
        user.setRoles(roles);
        
        // Save the user
        User savedUser = userRepository.save(user);
        
        // Return user DTO (excluding password)
        return convertToDto(savedUser);
    }
    
    public AuthResponse authenticateUser(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = jwtUtil.generateToken(userDetails);
        
        return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getRoles()
        );
    }
    
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return convertToDto(user);
    }
    
    public UserDto verifyEmail(String verificationToken) {
        // In a real application, you would look up the verification token
        // For simplicity, we'll just update the user's verified status
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setEmailVerified(true);
        User savedUser = userRepository.save(user);
        
        return convertToDto(savedUser);
    }
    
    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.isEmailVerified()
        );
    }
} 