package project.semester.aspm.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Debug controller for troubleshooting validation issues.
 * This should be removed in production.
 */
@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);
    
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(".*[\\W_].*");

    @PostMapping("/validate-password")
    public ResponseEntity<Map<String, Object>> validatePassword(@RequestBody Map<String, String> request) {
        String password = request.get("password");
        logger.info("Debug validation for password: {}", password);
        
        boolean hasUppercase = UPPERCASE_PATTERN.matcher(password).matches();
        boolean hasSpecialChar = SPECIAL_CHAR_PATTERN.matcher(password).matches();
        boolean hasMinLength = password != null && password.length() >= 8;
        boolean isValid = hasUppercase && hasSpecialChar && hasMinLength;
        
        Map<String, Object> result = new HashMap<>();
        result.put("password", password);
        result.put("passwordLength", password != null ? password.length() : 0);
        result.put("hasMinLength", hasMinLength);
        result.put("hasUppercase", hasUppercase);
        result.put("hasSpecialChar", hasSpecialChar);
        result.put("isValid", isValid);
        
        return ResponseEntity.ok(result);
    }
} 