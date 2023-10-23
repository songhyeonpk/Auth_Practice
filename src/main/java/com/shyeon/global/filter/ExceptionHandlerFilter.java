package com.shyeon.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyeon.global.exception.ErrorResponse;
import com.shyeon.global.exception.customexception.TokenCustomException;
import com.shyeon.global.exception.errorcode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        logger.info("ExceptionFilter Run.");

        try {
            filterChain.doFilter(request, response);
        } catch (TokenCustomException e) {
            setErrorResponse(response, e.getErrorCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode)throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.from(errorCode)));
    }
}
