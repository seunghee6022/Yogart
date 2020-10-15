package com.ssafy.yogart.user.model;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Result {
    public static final String SUCCESS_MESSAGE = "성공";
    public static final String SERVER_ERROR_MESSAGE = "실패";

    private HttpStatus statusCode;
    private String message;
    private Object token;
    private User user;
    private int totalCount;

    public Result(){}

    public static Result successInstance(){
        return new Result().success();
    }

    public Result success(){
        statusCode = HttpStatus.OK;
        message = SUCCESS_MESSAGE;
        return this;
    }

    public Result fail(){
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        message = SERVER_ERROR_MESSAGE;
        return this;
    }

    public Result setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public Result setUser(User user) {
    	this.user = user;
    	return this;
    }
    
    public Result setToken(Object token) {
    	this.token = token;
    	return this;
    }
    
    public int getStatusCode() {
        return statusCode.value();
    }
    
}
