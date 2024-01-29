package com.smart.data.msuser.interceptor;

import com.smart.data.msuser.entity.CustomError;
import com.smart.data.msuser.exception.NotUniqueEmailCustomException;
import com.smart.data.msuser.exception.NotUniqueUserSecurityCustomException;
import com.smart.data.msuser.exception.PasswordPatternCustomException;
import com.smart.data.msuser.exception.ResourceNotFoundCustomException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustonHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleFormatError(ConstraintViolationException ex) {
        CustomError error = CustomError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        log.info("Error message={} status={}", error.getMessage(), error.getStatus());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(NotUniqueEmailCustomException.class)
    public ResponseEntity<Object> handleNotUniqueEmailException() {
        CustomError error = CustomError.builder()
                .message("El correo ya fue registrado, intentar con uno nuevo")
                .status(HttpStatus.BAD_REQUEST)
                .build();
        log.info("Error message={} status={}", error.getMessage(), error.getStatus());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(NotUniqueUserSecurityCustomException.class)
    public ResponseEntity<Object> handleNotUserSecurityException() {
        CustomError error = CustomError.builder()
                .message("El nombre de usuario ya fue registrado")
                .status(HttpStatus.BAD_REQUEST)
                .build();
        log.info("Error message={} status={}", error.getMessage(), error.getStatus());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(JwtException ex) {
        CustomError error = CustomError.builder()
                .message("El token expiro, intente generar uno nuevo")
                .status(HttpStatus.FORBIDDEN)
                .build();
        log.info("Error message={} status={}", error.getMessage(), error.getStatus());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundCustomException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ResourceNotFoundCustomException ex) {
        CustomError error = CustomError.builder()
                .message("El usuario no fue encontrado")
                .status(HttpStatus.NO_CONTENT)
                .build();
        log.info("Error message={} status={}", error.getMessage(), error.getStatus());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(PasswordPatternCustomException.class)
    public ResponseEntity<Object> handleExpiredJwtException(PasswordPatternCustomException ex) {
        CustomError error = CustomError.builder()
                .message("La contraseña debe cumplir los patrones de seguridad")
                .status(HttpStatus.BAD_REQUEST)
                .build();
        log.info("Error message={} status={}", error.getMessage(), error.getStatus());
        return new ResponseEntity<>(error, error.getStatus());
    }
}
