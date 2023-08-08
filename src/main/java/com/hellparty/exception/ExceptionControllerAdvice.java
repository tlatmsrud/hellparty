package com.hellparty.exception;

import com.hellparty.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * title        : ControllerAdvice
 * author       : sim
 * date         : 2023-07-01
 * description  : Controller에서 발생한 예외 핸들러
 */

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    private static final String UNKNOWN_ERROR_MESSAGE = "시스템에서 알 수 없는 에러가 발생하였습니다. 관리자에게 문의해주세요.";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO NotFoundExceptionHandler(NotFoundException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseDTO(e.getMessage());
    }

    @ExceptionHandler(JwtTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO JwtTokenExceptionHandler(JwtTokenException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseDTO(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO BadRequestExceptionHandler(BadRequestException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseDTO(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseDTO(e.getMessage());
    }

    @ExceptionHandler(FileProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO FileProcessingExceptionHandler(FileProcessingException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseDTO(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO IOExceptionHandler(IOException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseDTO(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO ExceptionHandler(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ErrorResponseDTO(UNKNOWN_ERROR_MESSAGE);
    }

}
