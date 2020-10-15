package com.ssafy.yogart.user.service;

import java.util.HashMap;

import com.ssafy.yogart.user.model.KakaoPaymentApproval;
import com.ssafy.yogart.user.model.KakaoPaymentReady;

public interface KakaoService {

	String getAccessToken(String authorizeCode);
	HashMap<String, Object> getUserProfile (String access_Token);
	KakaoPaymentReady kakaoPayReady(String userNickname, int quantity, int price) throws Exception;
	KakaoPaymentApproval kakaoPaySuccess(String userNickname, String tID, String pgToken, int totalAmount) throws Exception;
}
