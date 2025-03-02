package edu.icet.ecom.order.util.exception;

import edu.icet.ecom.order.dto.ErrorResponse;
import edu.icet.ecom.order.util.exception.custom.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class, EntityNotFoundException.class})
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(objectError -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String errorMessage = objectError.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
