package com.ssafy.yogart.user.serviceimpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

import com.ssafy.yogart.user.service.NaverService;

@Service
public class NaverServiceImpl implements NaverService {

	private static final String NAVER_CLIENT_ID = "gdHF0XDc7dZhU9WNOgHe";
	private static final String NAVER_CLIENT_SECRET = "ps4FnHv9js";
	
	@Override
	public HashMap<String, String> getTokenInfo(String code, String state) throws Exception {
		String redirectURI = URLEncoder.encode("http://i3d202.p.ssafy.io/login", "UTF-8");
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + NAVER_CLIENT_ID;
        apiURL += "&client_secret=" + NAVER_CLIENT_SECRET;
        apiURL += "&redirect_uri=" + redirectURI;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;
        System.out.println("apiURL : " + apiURL);
        
        URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);
        
        BufferedReader br;
        if(responseCode == 200) { // 정상 호출
          br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  // 에러 발생
          br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
          response.append(inputLine);
        }
        br.close();
//        System.out.println(response.toString());
        
        HashMap<String, String> tokenInfo = new HashMap<>();
        StringTokenizer responseTokenizer = new StringTokenizer(response.toString().replaceAll("[{}\"]", ""), ",");
        while(responseTokenizer.hasMoreTokens()) {
        	StringTokenizer entityTokenizer = new StringTokenizer(responseTokenizer.nextToken(), ":");
        	String key = entityTokenizer.nextToken();
        	String value = entityTokenizer.nextToken();
        	tokenInfo.put(key, value);
        }
        
        for(HashMap.Entry<String, String> ent : tokenInfo.entrySet()) {
        	System.out.println(ent.getKey() + " / " + ent.getValue());
        }
        
		return tokenInfo;
	}

	@Override
	public HashMap<String, String> getUserProfile(String accessToken) throws Exception {
		String apiURL = "https://openapi.naver.com/v1/nid/me";
	    String headerString = "Bearer " + accessToken; // Bearer 다음에 공백 추가
	    
	    URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", headerString);
        
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);
        
        BufferedReader br;
        if(responseCode == 200) { // 정상 호출
          br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  // 에러 발생
          br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
          response.append(inputLine);
        }
        br.close();
//        System.out.println(response.toString());
        
        HashMap<String, String> userProfile = new HashMap<>();
        StringTokenizer responseTokenizer = new StringTokenizer(response.toString().replaceAll("[{}\"]", ""), ",");
        while(responseTokenizer.hasMoreTokens()) {
        	StringTokenizer entityTokenizer = new StringTokenizer(responseTokenizer.nextToken(), ":");
        	userProfile.put(entityTokenizer.nextToken(), entityTokenizer.nextToken()); 	
        }
        
//      유니코드 -> UTF-8로 변환해서 한글 닉네임도 받을 수 있게 설정
        String unicodeString = userProfile.get("nickname");
        StringBuffer convertBuffer = new StringBuffer();
        for (int i = 0; i < unicodeString.length(); i++) {
        	if ('\\' == unicodeString.charAt(i) && 'u' == unicodeString.charAt(i + 1)) {
        		Character r = (char) Integer.parseInt(unicodeString.substring(i + 2, i + 6), 16);
        		convertBuffer.append(r);
        		i += 5;
        	} else {
        		convertBuffer.append(unicodeString.charAt(i));
        	}
        }
        userProfile.replace("nickname", convertBuffer.toString());

//        for(HashMap.Entry<String, String> ent : userProfile.entrySet()) {
//        	System.out.println(ent.getKey() + " / " + ent.getValue());
//        }
        
        return userProfile;
	}

}
