package project.semester.aspm.userservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project.semester.aspm.userservice.dto.AuthRequest;
import project.semester.aspm.userservice.dto.AuthResponse;
import project.semester.aspm.userservice.dto.RegistrationRequest;
import project.semester.aspm.userservice.dto.UserDto;
import project.semester.aspm.userservice.model.User;
import project.semester.aspm.userservice.model.VerificationToken;
import project.semester.aspm.userservice.repository.UserRepository;
import project.semester.aspm.userservice.repository.VerificationTokenRepository;
import project.semester.aspm.userservice.security.JwtUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    
    // Simple password validation patterns
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(".*[\\W_].*");

    public UserService(UserRepository userRepository, 
                      VerificationTokenRepository tokenRepository,
                      PasswordEncoder passwordEncoder,
                      JwtUtil jwtUtil, 
                      AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UserDto registerUser(RegistrationRequest request) {
        // Validate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        
        // Validate password
        validatePassword(request.getPassword());
        
        // Create a new user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Set username based on email (temporary solution until database is fixed)
        user.setUsername(request.getEmail());
        
        // Set roles based on request
        Set<String> roles = new HashSet<>();
        roles.add("CUSTOMER"); // Every user is a customer
        
        if (request.isSeller()) {
            roles.add("SELLER");
        }
        
        user.setRoles(roles);
        
        // Save the user
        User savedUser = userRepository.save(user);
        
        // Create verification token
        VerificationToken verificationToken = new VerificationToken(savedUser);
        tokenRepository.save(verificationToken);
        
        // In a real application, we would send an email with the verification link here
        logger.info("Verification token created: {} for user: {}", verificationToken.getToken(), savedUser.getEmail());
        
        // Return user DTO (excluding password)
        return convertToDto(savedUser);
    }
    
    private void validatePassword(String password) {
        // Check length
        if (password.length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Password must be at least 8 characters");
        }
        
        // Check for uppercase letter
        if (!UPPERCASE_PATTERN.matcher(password).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Password must contain at least one uppercase letter");
        }
        
        // Check for special character
        if (!SPECIAL_CHAR_PATTERN.matcher(password).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Password must contain at least one special character");
        }
    }
    
    public AuthResponse authenticateUser(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            
            String token = jwtUtil.generateToken(userDetails);
            
            return new AuthResponse(
                    token,
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRoles()
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }
    
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        return convertToDto(user);
    }
    
    @Transactional
    public UserDto verifyEmail(Long userId) {
        // Find the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        // Update user's verified status
        user.setEmailVerified(true);
        User savedUser = userRepository.save(user);
        
        return convertToDto(savedUser);
    }
    
    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRoles(),
                user.isEmailVerified()
        );
    }
    
    public String createVerificationToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
                
        // Check if there's an existing token
        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);
        
        // Create new token
        VerificationToken token = new VerificationToken(user);
        tokenRepository.save(token);
        
        return token.getToken();
    }
} 