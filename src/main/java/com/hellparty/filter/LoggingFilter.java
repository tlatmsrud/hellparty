package com.hellparty.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * title        : LoggingFilter
 * author       : sim
 * date         : 2023-08-06
 * description  : 요청, 응답에 대한 로깅 필터
 */


@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Value("${logging.exclude.uri}")
    private List<String> excludeUri;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
        printLogging(wrappingRequest, wrappingResponse);
        filterChain.doFilter(wrappingRequest, wrappingResponse);
        wrappingResponse.copyBodyToResponse();
    }

    private void printLogging(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(!isLoggingUri(request.getRequestURI())){
            return;
        }

        StringBuffer logMessage = new StringBuffer("\n");
        logMessage.append("┌──────────────────────────────────────────────────────────────\n");
        logMessage.append("│ Request URI : "+request.getRequestURI()+"\n");
        logMessage.append("│ Request Method : "+request.getMethod()+"\n");
        logMessage.append("│ Request Authorization Header : "+request.getHeader("Authorization") + "\n");

        if(request.getQueryString() != null) {
            logMessage.append("│ Request Body : "+ URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8)+"\n");
        }

        ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

        //Json 형식에 대한 Request 데이터 추출
        if (cachingRequest.getContentAsByteArray().length != 0){
            logMessage.append("│ Request Body : " + objectMapper.readTree(cachingRequest.getContentAsByteArray())+" \n");
        }

        //Json 형식에 대한 Response 데이터 추출
        if (cachingResponse.getContentType() != null && cachingResponse.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            if (cachingResponse.getContentAsByteArray().length != 0) {
                logMessage.append("│ Response Body : " + objectMapper.readTree(cachingResponse.getContentAsByteArray())+" \n");
            }
        }
        logMessage.append("└──────────────────────────────────────────────────────────────");

        //로그 출력
        logger.info(logMessage.toString());
    }

    private boolean isLoggingUri(String requestUri){
        return excludeUri.stream().anyMatch(requestUri::contains);
    }
}
