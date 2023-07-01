package com.hellparty.exception;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }
}
