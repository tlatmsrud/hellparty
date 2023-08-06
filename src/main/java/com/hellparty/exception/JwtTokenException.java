package com.hellparty.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * title        : JwtTokenException
 * author       : sim
 * date         : 2023-08-06
 * description  : JwtTokenException
 */
public class JwtTokenException extends AuthenticationException {
    public JwtTokenException(String message) {
        super(message);
    }
}
