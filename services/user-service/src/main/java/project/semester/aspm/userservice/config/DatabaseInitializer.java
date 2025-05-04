package project.semester.aspm.userservice.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * This method will execute when the application has finished starting up.
     * It will check if the username column exists and make it nullable
     * to avoid the "Field 'username' doesn't have a default value" error.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        logger.info("Checking database schema and fixing username column if needed");
        
        try {
            // First check if the users table exists
            jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'users'", 
                Integer.class
            );
            
            // Check if username column exists
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.columns " +
                "WHERE table_name = 'users' AND column_name = 'username'", 
                Integer.class
            );
            
            if (count != null && count > 0) {
                logger.info("Username column exists, modifying to make it nullable");
                jdbcTemplate.execute("ALTER TABLE users MODIFY username VARCHAR(255) NULL");
                logger.info("Username column successfully modified");
            } else {
                logger.info("Username column does not exist, no modifications needed");
            }
        } catch (Exception e) {
            logger.error("Error checking or modifying database schema", e);
        }
    }
} 