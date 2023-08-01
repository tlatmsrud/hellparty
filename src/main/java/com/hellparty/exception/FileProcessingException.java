package com.hellparty.exception;

/**
 * title        : FileProcessingException
 * author       : sim
 * date         : 2023-08-01
 * description  : 파일 처리 도중 발생한 예외에 대한 RuntimeException
 */
public class FileProcessingException extends RuntimeException{

    public FileProcessingException(String message){
        super(message);
    }
}
