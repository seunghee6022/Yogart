package com.ssafy.yogart.user.model;

import lombok.Data;

@Data	
public class KakaoLoginRequest {

	private String access_token;
    private String expires_in;
    private String refresh_token;
    private String refresh_token_expires_in;
//    private String scope;
    private String token_type;
    
    
}
