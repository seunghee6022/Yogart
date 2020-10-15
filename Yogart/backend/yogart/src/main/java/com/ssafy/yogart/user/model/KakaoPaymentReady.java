package com.ssafy.yogart.user.model;

import java.util.Date;

import lombok.Data;

@Data
public class KakaoPaymentReady {
	    
	private String tid;
	private String next_redirect_pc_url;
	private Date created_at;
}
