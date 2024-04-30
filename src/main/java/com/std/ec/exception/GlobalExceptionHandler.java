package com.std.ec.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.std.ec.model.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
            WebRequest webRequest) {
                Map <String,String> mapErrors = new HashMap<>();
                exception.getBindingResult().getAllErrors().forEach((error)->{
                    String clave= ((FieldError)error).getField();
                    String valor = error.getDefaultMessage();
                    mapErrors.put(clave, valor);

                });

        ApiResponse apiResponse = new ApiResponse(
                mapErrors.toString(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    };

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundExceptio(ResourceNotFoundException exception,
            WebRequest webRequest) {

        ApiResponse apiResponse = new ApiResponse(
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    };

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception exception,
            WebRequest webRequest) {

        ApiResponse apiResponse = new ApiResponse(
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    };

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handlerBadRequestException(BadRequestException exception,
            WebRequest webRequest) {

        ApiResponse apiResponse = new ApiResponse(
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    };

}
