package com.zws.show.controller;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private int status;
    private String message;
    private T data;
    private Timestamp timestamp;

    public Result() {
    }

    public Result(int status, String message) {
        this(status, message, null);
    }

    public Result(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

//    public Result(Errors errors) {
//        StringBuilder message = new StringBuilder();
//        errors.getFieldErrors().forEach((ObjectError error) -> {
//            message.append(error.getDefaultMessage() + "\n");
//        });
//
//        this.status = 1;
//        this.message = message.toString();
//        this.timesTamp = new Timestamp(System.currentTimeMillis());
//    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }

}