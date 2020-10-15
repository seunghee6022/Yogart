package com.ssafy.yogart.user.serviceimpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.yogart.user.model.KakaoPaymentApproval;
import com.ssafy.yogart.user.model.KakaoPaymentReady;
import com.ssafy.yogart.user.service.KakaoService;

@Service
public class KakaoServiceImpl implements KakaoService {

	private static final String KAKAO_PAY_HOST = "https://kapi.kakao.com";
	private static final String KAKAO_PAY_ADMIN_KEY = "c6b40447619f78404eb74f24d63da6a3";
	
	@Override
	public String getAccessToken(String authorizeCode) {
		
		String accessToken = "";
		String refreshToken = "";
		String requestURL = "https://kauth.kakao.com/oauth/token";
		
		try {
			
			URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
            sb.append("&client_id=b5f85af25d1bdf961d4f2016bafe3c6e");
            sb.append("&redirect_uri=http://i3d202.p.ssafy.io:8000/login");
            sb.append("&code=" + authorizeCode);
            bw.write(sb.toString());
            bw.flush();
            
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
            
            System.out.println("access_token : " + accessToken);
            System.out.println("refresh_token : " + refreshToken);
            
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return accessToken;
	}

	@Override
//	public HashMap<String, Object> getUserInfo(String access_Token) {
//		RestTemplate rt = new RestTemplate();
//		// HttpHeader 오브젝트 생성
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "Bearer " + access_Token);
//		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
//
//		// Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
//		ResponseEntity<String> KakaoResponse = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
//				kakaoProfileRequest, String.class);
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		KakaoUserProfile kakaoProfile = null;
//		try {
//			kakaoProfile = objectMapper.readValue(KakaoResponse.getBody(), KakaoUserProfile.class);
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
//		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
//
//		System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
//		System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
//
//		return null;
//	}
	
	public HashMap<String, Object> getUserProfile (String access_Token) {
	    System.out.println(access_Token);
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String reqURL = "https://kapi.kakao.com/v2/user/me";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	        conn.connect();
	        int responseCode = conn.getResponseCode();
	        System.out.println(conn.getResponseMessage());
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
//	        System.out.println("response body : " + result);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	        
	        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
	        userInfo.put("nickname", nickname);
	        userInfo.put("email", email);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    return userInfo;
	}

	@Override
	public KakaoPaymentReady kakaoPayReady(String userNickname, int quantity, int price) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		KakaoPaymentReady kakaoPaymentReady;
		
//		RequestHeader
		header.add("Authorization", "KakaoAK " + KAKAO_PAY_ADMIN_KEY);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		header.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		
//		RequestBody
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("cid", "TC0ONETIME");
        parameters.add("partner_order_id", "1001");
        parameters.add("partner_user_id", userNickname);
        parameters.add("item_name", "스푼");
        parameters.add("quantity", String.valueOf(quantity));
        parameters.add("total_amount", String.valueOf(price));
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://i3d202.p.ssafy.io/kakaoPay/Success");
        parameters.add("cancel_url", "http://i3d202.p.ssafy.io/kakaoPay/Cancel");
        parameters.add("fail_url", "http://i3d202.p.ssafy.io/kakaoPay/Fail");
		
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(parameters, header);
        
        kakaoPaymentReady = restTemplate.postForObject(new URI(KAKAO_PAY_HOST + "/v1/payment/ready"), body, KakaoPaymentReady.class);
            
        System.out.println(kakaoPaymentReady);
 
		return kakaoPaymentReady;
	}

	@Override
	public KakaoPaymentApproval kakaoPaySuccess(String userNickname, String tID, String pgToken, int totalAmount) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		KakaoPaymentApproval kakaoPaymentApproval;
		
        header.add("Authorization", "KakaoAK " + KAKAO_PAY_ADMIN_KEY);
        header.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        header.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", tID);
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", userNickname);
        params.add("pg_token", pgToken);
        params.add("total_amount", String.valueOf(totalAmount));
        
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, header);
        kakaoPaymentApproval = restTemplate.postForObject(new URI(KAKAO_PAY_HOST+ "/v1/payment/approve"), body, KakaoPaymentApproval.class);
        return kakaoPaymentApproval;        
	}
}
