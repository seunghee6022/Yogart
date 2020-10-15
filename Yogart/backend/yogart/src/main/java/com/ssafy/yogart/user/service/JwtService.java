package com.ssafy.yogart.user.service;

import com.ssafy.yogart.user.model.User;

public interface JwtService {
	<T> String create(String key, T data, String subject);
	User get(String key);
//	int getUserId();
	boolean isUsable(String jwt);
}
