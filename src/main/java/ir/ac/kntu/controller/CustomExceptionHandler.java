package ir.ac.kntu.controller;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.google.gson.Gson;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import ir.ac.kntu.exception.DuplicateUserException;
import ir.ac.kntu.exception.UserNotExistedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;

// @ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    static class ExceptionMessage {
        String message;
        HttpStatus status;
        int errorCode;
        List<String> details;

        public ExceptionMessage(String message, HttpStatus status, String... details) {
            this.message = message;
            this.status = status;
            this.errorCode = status.value();
            setDetails(details);
        }

        private void setDetails(String[] details) {
            this.details = details == null || details.length == 0 ? null :
                    Arrays.asList(details);
        }
    }

    private Gson gson;

    public CustomExceptionHandler() {
        gson = new Gson();
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Object> handleAllExceptions(Throwable throwable,
                                                      WebRequest request) {
        throwable.printStackTrace();
        log(throwable);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                throwable.getMessage(), HttpStatus.SERVICE_UNAVAILABLE));
        return handleExceptionInternal(new Exception(throwable), bodyOfResponse, new HttpHeaders(),
                HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAllExceptions
            (Exception ex, WebRequest request) {
        log(ex);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeExceptions(RuntimeException ex,
                                                          WebRequest request) {
        System.out.println(ex);
        log(ex);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = io.jsonwebtoken.SignatureException.class)
    public ResponseEntity<Object> handleJwtSignitureNotMatched(
            SignatureException ex, WebRequest request) {
        log(ex);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                ex.getMessage(), HttpStatus.FORBIDDEN));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
                HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = com.fasterxml.jackson.databind.exc.InvalidDefinitionException.class)
    public ResponseEntity<Object> handleInstantiationFault(
            InvalidDefinitionException ex, WebRequest request) {
        log(ex);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = io.jsonwebtoken.MalformedJwtException.class)
    public ResponseEntity<Object> handleUnableToReadJson(
            MalformedJwtException ex, WebRequest request) {
        log(ex);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = UserNotExistedException.class)
    public ResponseEntity<Object> handleUserNotExistException(
            UserNotExistedException ex, WebRequest request) {
        log(ex);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                ex.getMessage(), HttpStatus.NOT_FOUND));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = DuplicateUserException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            DuplicateUserException ex, WebRequest request) {
        log(ex);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private void log(Throwable throwable) {
        logger.warn("exception handled:" + throwable.getClass().getSimpleName()
                + "\t" + throwable.getMessage());
    }
}
