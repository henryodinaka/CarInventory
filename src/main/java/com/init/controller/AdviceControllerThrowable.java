package com.init.controller;

import com.init.dto.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.PersistenceException;
import java.net.SocketTimeoutException;

@ControllerAdvice(annotations = RestController.class, basePackages = "ng.union.bank.controller")
@ResponseBody
@Slf4j
public class AdviceControllerThrowable {
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleLockedException(HttpClientErrorException e) {
        log.error("Error", e);
        return new Response(String.valueOf(e.getRawStatusCode()), e.getStatusText() + " " + e.getResponseBodyAsString(), null);
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleLoginException(PersistenceException e) {
        log.error("PersistenceException ", e);
        return new Response("400", "Data error", null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleLoginException(IllegalArgumentException e) {
        log.error("IllegalArgumentException ", e);
        return new Response("400", e.getLocalizedMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response noAccessException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException ", e);
        return new Response("400", "Wrong message was sent or some required fields were not provided", null);
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public Response timeOutException(MethodArgumentNotValidException e) {
        log.error("SocketTimeoutException ", e);
        return new Response("408", "Sorry...request is taking longer than expected.. please trying again later", null);
    }

    @ExceptionHandler(NullPointerException.class)
    public Response noAccessException(NullPointerException e) {
        log.error("Null Pointer exception", e);
        return new Response(503, "Sorry we can't process your request at this moment, please try again", null);
    }
}
