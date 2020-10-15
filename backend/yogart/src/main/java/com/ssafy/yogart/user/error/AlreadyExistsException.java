package com.ssafy.yogart.user.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already exists")
public class AlreadyExistsException extends RuntimeException {
	public AlreadyExistsException(String message) {
		   super(message);
		  }
}
