package project.semester.aspm.storesevice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StoreServiceException extends RuntimeException {
    private final HttpStatus status;
    
    public StoreServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    
    public static StoreServiceException notFound(String message) {
        return new StoreServiceException(message, HttpStatus.NOT_FOUND);
    }
    
    public static StoreServiceException badRequest(String message) {
        return new StoreServiceException(message, HttpStatus.BAD_REQUEST);
    }
    
    public static StoreServiceException forbidden(String message) {
        return new StoreServiceException(message, HttpStatus.FORBIDDEN);
    }
    
    public static StoreServiceException conflict(String message) {
        return new StoreServiceException(message, HttpStatus.CONFLICT);
    }
} 