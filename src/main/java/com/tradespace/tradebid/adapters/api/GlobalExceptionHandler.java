package com.tradespace.tradebid.adapters.api;

import com.tradespace.tradebid.adapters.api.dto.ErrorDTO;
import com.tradespace.tradebid.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.List.of;
import static java.util.stream.Collectors.toList;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> format("'%s' has an invalid value '%s': %s", fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()))
                .collect(toList());

        return new ErrorDTO("BeanValidationException", valueOf(HttpStatus.BAD_REQUEST.value()), errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorDTO(e.getClass().getSimpleName(), valueOf(HttpStatus.NOT_FOUND.value()), of(e.getMessage()));
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleProjectNotFoundException(ProjectNotFoundException e) {
        return new ErrorDTO(e.getClass().getSimpleName(), valueOf(HttpStatus.NOT_FOUND.value()), of(e.getMessage()));
    }

    @ExceptionHandler(SelfBidException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO handleSelfBidException(SelfBidException e) {
        return new ErrorDTO(e.getClass().getSimpleName(), valueOf(HttpStatus.FORBIDDEN.value()), of(e.getMessage()));
    }

    @ExceptionHandler(ProjectExpiredException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO handleProjectExpiredException(ProjectExpiredException e) {
        return new ErrorDTO(e.getClass().getSimpleName(), valueOf(HttpStatus.FORBIDDEN.value()), of(e.getMessage()));
    }

    @ExceptionHandler(DuplicateBidException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO handleDuplicateBidException(DuplicateBidException e) {
        return new ErrorDTO(e.getClass().getSimpleName(), valueOf(HttpStatus.FORBIDDEN.value()), of(e.getMessage()));
    }

    @ExceptionHandler(JobNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleJobNotFoundException(JobNotFoundException e) {
        return new ErrorDTO(e.getClass().getSimpleName(), valueOf(HttpStatus.NOT_FOUND.value()), of(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleException(Exception e) {
        return new ErrorDTO(e.getClass().getSimpleName(), valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), of(e.getMessage()));
    }
}