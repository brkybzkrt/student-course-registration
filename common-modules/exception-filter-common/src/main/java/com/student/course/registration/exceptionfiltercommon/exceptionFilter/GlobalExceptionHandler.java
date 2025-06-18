package com.student.course.registration.exceptionfiltercommon.exceptionFilter;

import com.student.course.registration.base.interceptors.requestPath.RequestContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.student.course.registration.base.response.ErrorResponse;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice(basePackages = "com.student.course.registration")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e){
        Map<String , List<String>> errors= new HashMap<>();

        for(ObjectError error: e.getBindingResult().getAllErrors()){
            String fieldName = ((FieldError)error).getField();
            if(errors.containsKey(fieldName)){
                errors.put(fieldName,addMapValue(errors.get(fieldName),error.getDefaultMessage()));
            }else {
                errors.put(fieldName,addMapValue(new ArrayList<>(),error.getDefaultMessage()));

            }

        }

        return ResponseEntity.badRequest().body(createErrorResponse(errors,HttpStatus.BAD_REQUEST.value(),null));
    }


    @ExceptionHandler(value =ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {
        String errors = e.getMessage().split(":", 1)[0];

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(errors,HttpStatus.NOT_FOUND.value(), null));
    };



    private List<String> addMapValue(List<String> list,String newValue){
        list.add(newValue);
        return list;

    }

    private <T> ErrorResponse<T> createErrorResponse(T errors,Integer status,String message){
        ErrorResponse<T> response = new ErrorResponse<>();

        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setMessage(message);
        response.setPath(RequestContextHolder.getPath());
        response.setError(errors);

        return response;
    }
}
