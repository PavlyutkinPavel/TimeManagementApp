package com.timemanagement.timemanagementrest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionResolver extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    ResponseEntity<HttpStatus> userNotFoundException(){
        log.info("UserNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PostNotFoundException.class)
    ResponseEntity<HttpStatus> postNotFoundException(){
        log.info("PostNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CommentNotFoundException.class)
    ResponseEntity<HttpStatus> commentNotFoundException(){
        log.info("CommentNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    ResponseEntity<HttpStatus> taskNotFoundException(){
        log.info("TaskNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ReviewNotFoundException.class)
    ResponseEntity<HttpStatus> reviewNotFoundException(){
        log.info("ReviewNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PurchaseHistoryNotFoundException.class)
    ResponseEntity<HttpStatus> purchaseHistoryNotFoundException(){
        log.info("PurchaseHistoryNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    ResponseEntity<HttpStatus> productNotFoundException(){
        log.info("ProductNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = NotificationNotFoundException.class)
    ResponseEntity<HttpStatus> notificationNotFoundException(){
        log.info("NotificationNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = LabelNotFoundException.class)
    ResponseEntity<HttpStatus> labelNotFoundException(){
        log.info("LabelNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = AchievementNotFoundException.class)
    ResponseEntity<HttpStatus> achievementNotFoundException(){
        log.info("AchievementNotFound exception!!!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<HttpStatus> illegalArgumentException(){
        log.info("IllegalArgument exception!!!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OptimisticLockingFailureException.class)
    ResponseEntity<HttpStatus> optimisticLockingFailureException(){
        log.info("OptimisticLockingFailure exception!!!");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = IOException.class)
    ResponseEntity<HttpStatus> IOException(){
        log.info("IO exception!!!, failed to update profile image");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RequestBodyNotFullException.class)
    ResponseEntity<HttpStatus> requestBodyException(){
        log.info("Request body provided through method of controller is not full!!!, you have to provide all columns of the entity");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

/*    @ExceptionHandler
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorMessage errorMessage = new ErrorMessage("Wrong request!!!");

        return ResponseEntity.status(status).body(errorMessage);
    }*/
}
