package com.switchkit.switchkit_test.exceptions;

import com.switchkit.switchkit_test.exceptions.ReportRestrictionsException;
import com.switchkit.switchkit_test.security.jwt.JwtAuthException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class UniControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            EntityNotFoundException.class,
            UsernameNotFoundException.class,
    })
    public ResponseEntity<Object> userNotFoundHandler(RuntimeException ex, WebRequest req) {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        log.error(ex.getLocalizedMessage());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), headers, HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler({
            JwtAuthException.class,
            ReportRestrictionsException.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity<Object> jwtAuthHandler(RuntimeException ex, WebRequest req) {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        log.error(ex.getLocalizedMessage());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), headers, HttpStatus.BAD_REQUEST, req);
    }
}
