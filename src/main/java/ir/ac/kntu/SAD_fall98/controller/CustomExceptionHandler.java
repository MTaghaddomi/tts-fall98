package ir.ac.kntu.SAD_fall98.controller;

import com.google.gson.Gson;
import ir.ac.kntu.SAD_fall98.exception.DuplicateUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
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

        private void setDetails(String[] details){
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
        log(ex);
        String bodyOfResponse = gson.toJson(new ExceptionMessage(
                ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
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

    private void log(Throwable throwable){
        logger.warn("exception handled:" + throwable.getClass().getSimpleName());
    }
}
