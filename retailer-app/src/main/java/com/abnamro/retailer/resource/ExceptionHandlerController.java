package com.abnamro.retailer.resource;

import com.abnamro.retailer.exception.InvalidInputException;
import com.abnamro.retailer.exception.NotFoundException;
import static com.abnamro.retailer.util.ErrorConstants.GENERAL_ERROR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Exception handler controller.
 */
@ControllerAdvice()
public class ExceptionHandlerController {

    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    /**
     * Handle invalid input exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handle not found exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    /**
     * Handle method argument not valid exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst().orElse(GENERAL_ERROR);
        return ResponseEntity.badRequest().body(message);
    }

    /**
     * Handle access denied exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    /**
     * Handle exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(500).body(GENERAL_ERROR);
    }

}