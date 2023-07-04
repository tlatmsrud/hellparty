package com.hellparty.exception;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-05
 * description  :
 */
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }
}
