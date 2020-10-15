package com.ssafy.yogart.user.service;

import java.util.HashMap;

public interface NaverService {

	HashMap<String, String> getTokenInfo(String code, String state) throws Exception;
	HashMap<String, String> getUserProfile(String accessToken) throws Exception;
	
}
