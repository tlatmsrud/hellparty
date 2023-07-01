package com.hellparty.exception;

import com.hellparty.dto.ErrorResponseDTO;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final String UNKNOWN_ERROR_MESSAGE = "시스템에서 알 수 없는 에러가 발생하였습니다. 관리자에게 문의해주세요.";
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO NotFoundExceptionHandler(NotFoundException e){
        e.printStackTrace();
        return new ErrorResponseDTO(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO JwtExceptionHandler(JwtException e){
        e.printStackTrace();
        return new ErrorResponseDTO(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO NotFoundExceptionHandler(Exception e){
        e.printStackTrace();
        return new ErrorResponseDTO(UNKNOWN_ERROR_MESSAGE);
    }

}
