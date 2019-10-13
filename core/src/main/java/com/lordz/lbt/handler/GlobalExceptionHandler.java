package com.lordz.lbt.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lordz.lbt.exception.ForbiddenException;
import com.lordz.lbt.exception.LBTException;
import com.lordz.lbt.exception.ServiceException;
import com.lordz.lbt.model.support.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * @author ：zzz
 * @date ：Created in 7/19/19 4:49 PM
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public BaseResponse ServiceException(HttpServletResponse response, ServiceException e){
        response.setStatus(e.getStatus().value());
        return new BaseResponse(500,e.getMessage(),null);
    }

    @ExceptionHandler(ForbiddenException.class)
    public BaseResponse ForbiddenException(HttpServletResponse response,ForbiddenException e){
        response.setStatus(e.getStatus().value());
        return new BaseResponse(e.getStatus().value(),e.getMessage(),e.getErrorData());
    }

    @ExceptionHandler(LBTException.class)
    public BaseResponse LBTException(HttpServletResponse response,LBTException e){
        response.setStatus(e.getStatus().value());
        return new BaseResponse(e.getStatus().value(),e.getMessage(),e.getErrorData());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse ConstraintViolation(HttpServletResponse response,ConstraintViolationException e){
        //e.getConstraintViolations().forEach(c -> System.out.println(c.getMessage()));
        return new BaseResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
    }


/*    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleBindException1(HttpServletResponse response,MethodArgumentNotValidException e) throws Exception{
        e.getBindingResult().getAllErrors().forEach(System.out::println);
        //return new ResponseEntity<>("cuowu:" + MAPPER.writeValueAsString(e.getBindingResult().getAllErrors()), HttpStatus.BAD_REQUEST);
        return new BaseResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
    }*/
}
