package com.ssafy.yogart.user.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.yogart.user.model.KakaoLoginRequest;
import com.ssafy.yogart.user.model.KakaoPaymentApproval;
import com.ssafy.yogart.user.model.KakaoPaymentReady;
import com.ssafy.yogart.user.model.Result;
import com.ssafy.yogart.user.model.User;
import com.ssafy.yogart.user.model.UserFile;
import com.ssafy.yogart.user.repository.UserFileRepository;
import com.ssafy.yogart.user.repository.UserRepository;
import com.ssafy.yogart.user.service.JwtService;
import com.ssafy.yogart.user.service.KakaoService;
import com.ssafy.yogart.user.service.NaverService;
import com.ssafy.yogart.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private final UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private KakaoService kakaoService;

	@Autowired
	private NaverService naverService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserFileRepository userFileRepository;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	private static final String SUCCESS = "success";

	private static int RECENT_TOTAL_AMOUNT;
	private static String RECENT_TID;

	@ApiOperation(value = "관리자 여부 판단")
	@GetMapping(value = "/isAdmin")
	public ResponseEntity<Boolean> isAdmin(@RequestHeader Map<String,String> header) {
		String token = header.get("authorization");
		User user = userService.authentication(token);
		if("ADMIN".equals(user.getUserAuthority())) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} 
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}

	@ApiOperation(value = "강사 여부 판단")
	@GetMapping(value = "/isTeacher")
	public ResponseEntity<Boolean> isTeacher(@RequestHeader Map<String,String> header) {
		String token = header.get("authorization");
		User user = userService.authentication(token);
		if("TEACHER".equals(user.getUserAuthority())) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} 
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}

	//로그인
	@ApiOperation(value = "로그인")
	@PostMapping(value = "/login")
	public ResponseEntity<Result> login(@RequestBody User loginData) {
		String email = loginData.getUserEmail();
		String password = loginData.getUserPassword();
		// jwtServiceImpl -> create  메서드 이용해서 토큰 생성
		System.out.println(email + "  " + password);
		ResponseEntity<Result> response = null;
		Result result = Result.successInstance();
		User user = userService.login(email, password);
		if(user == null) {                                   
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			String token = jwtService.create("user", user, email);
			System.out.println(token);
			result.setToken(token);
			result.setUser(user);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}

		return response;
	}

	@ApiOperation(value="카카오로그인")
	@PostMapping(value="/kakaoLogin")
	public ResponseEntity<Result> kakaoLogin(@RequestBody KakaoLoginRequest req) {
		//    	System.out.println("KakaoLoginRequest : " + req);
		String accessToken = req.getAccess_token();
		HashMap<String, Object> userProfile = kakaoService.getUserProfile(accessToken);
		String nickname = (String)userProfile.get("nickname");
		String email = (String)userProfile.get("email");
		//    	System.out.println("nickname : " + nickname + " / email : " + email);
		// jwtServiceImpl -> create  메서드 이용해서 토큰 생성
		ResponseEntity<Result> response = null;
		Result result = Result.successInstance();
		User user = userService.loginSocial(email, "kakao");
		if(user == null) {                                   
			String randPass = generateRandomCode(8);
			user = new User(email, nickname, nickname, randPass, "kakao");
			userService.joinSocial(email, nickname, randPass, "kakao");
		} 
		String token = jwtService.create("user", user, email);
		System.out.println(token);
		result.setToken(token);
		result.setUser(user);

		response = new ResponseEntity<>(result, HttpStatus.OK);

		return response;
	}

	@ApiOperation(value="네이버로그인")
	@PostMapping(value="/naverLogin")
	public ResponseEntity<Result> naverLogin(@RequestBody Map<String, String> accessTokenInfo) {	
		//    	System.out.println("Code : " + code + " / State : " + state);
		ResponseEntity<Result> response = null;
		try {
			//    		String accessToken = naverService.getTokenInfo(code, state).get("access_token");
			System.out.println("accessToken : " + accessTokenInfo);
			HashMap<String, String> userProfile = naverService.getUserProfile(accessTokenInfo.get("accessToken"));
			String nickname = userProfile.get("nickname");
			String email = userProfile.get("email");
			//    		System.out.println("nickname : " + nickname + " / email : " + email);
			Result result = Result.successInstance();
			User user = userService.loginSocial(email, "naver");
			if(user == null) {
				String randPass = generateRandomCode(8);
				user = new User(email, nickname, nickname, randPass, "naver");
				userService.joinSocial(email, nickname, randPass, "naver");
			} 
			String token = jwtService.create("user", user, email);
			System.out.println(token);
			result.setToken(token);
			result.setUser(user);
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	// 사용자 username과 password을 입력받아 새로운 User를 생성하고 그 결과를 반환
	@ApiOperation(value="회원가입", response = Result.class)
	@PostMapping(value = "/signup")
	public ResponseEntity<Result> create(@RequestBody Map<String, String> signupData) {
		String email = signupData.get("userEmail");
		String username = signupData.get("userName");
		String nickname = signupData.get("userNickname");
		String password = signupData.get("userPassword");
		Result result = new Result();
	
		if(userService.emailChk(email) != null && userService.nicknameChk(nickname) != null) {
			result.setMessage("email/nickname");
			result.setStatusCode(HttpStatus.FORBIDDEN);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}else if(userService.nicknameChk(nickname) != null) {			
			result.setMessage("nickname");
			result.setStatusCode(HttpStatus.FORBIDDEN);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}else if(userService.emailChk(email) != null) {
			result.setMessage("email");
			result.setStatusCode(HttpStatus.FORBIDDEN);
			return new ResponseEntity<Result>(result, HttpStatus.OK);			
		}
		User user = new User(email, username, nickname, password);
		result.setUser(user);
		System.out.println(email + ", " 
				+ username + ", " 
				+ nickname + ", " 
				+ password);
		userService.join(email, username, nickname, password);
		String token = jwtService.create("user", user, email);
		System.out.println(token);
		result.setToken(token);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}

	// 자신의 정보를 반환
	@ApiOperation(value="자신의 정보를 반환")
	@GetMapping(value = "/myInfo/profile")
	public ResponseEntity<User> getMe(@RequestHeader(value="config") Map<String,String> data) {
		String token = (String)data.get("authorization");
		System.out.println("token:::" + token);
		User user = userService.authentication(token);
		Result result = Result.successInstance();
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	// 자신의 정보를 반환
		@ApiOperation(value="자신의 정보를 반환")
		@GetMapping(value = "/myInfo")
		public ResponseEntity<User> getMe2(@RequestHeader(value="config") Map<String,String> data) {
			String token = (String)data.get("authorization");
			System.out.println("token:::" + token);
			User user = userService.authentication(token);
			Result result = Result.successInstance();
			if(user == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

	// 닉네임, 프로필사진, 자기소개 정보를 수정
	@ApiOperation(value="유저 정보 업데이트", response = User.class)
	@PutMapping(value = "/myInfo/update")
	public User updateInfo(@RequestHeader(value="config") Map<String, Object> header, @ModelAttribute User content) {
		System.out.println(header);
		System.out.println(content.getUserIntro());
//		System.out.println(content.get("userEmail"));
		String token = (String)header.get("authorization");
		User user = userService.authentication(token);
		System.out.println("?" + user.getUserNickname());
		user.setUserNickname(content.getUserNickname());
		user.setUserIntro(content.getUserIntro());
		return userService.updateInfo(user);
	}

	// 탈퇴
	@ApiOperation(value="탈퇴", response = String.class)
	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> withdraw(@RequestHeader Map<String, String> header) {
		String token = header.get("authorization");
		User user = userService.authentication(token);
		userService.withdraw(user);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value="강사 인증 이미지 업로드")
	@PostMapping(value = "/imageUpload")
	public ResponseEntity<Result> imageUpload(@RequestHeader Map<String, String> header,
			@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {

		User currUser = userService.authentication(header.get("authorization"));
		ResponseEntity<Result> response;
		for(MultipartFile file : files)
		{
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			System.out.println(request.getServletContext());
//			File dest = new File(request.getServletContext().getRealPath("/") + fileName);
			System.out.println(request.getServletContext().getRealPath("/"));
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String uploadDate = simpleDateFormat.format(new Date());
				save(file, request.getServletContext().getRealPath("/"), uploadDate);
				UserFile userFile = new UserFile(currUser, uploadDate + fileName);
				userFileRepository.save(userFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		}
		Result result = Result.successInstance();
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	@ApiOperation(value="스푼 결제")
	@PostMapping(value = "/pay")
	public ResponseEntity<KakaoPaymentReady> chargeSpoon(@RequestHeader String authorization, 
			@RequestBody Map<String, Integer> purchaseData) {
		int quantity = purchaseData.get("quantity");
		int price = purchaseData.get("price");
		User currentUser = userService.authentication(authorization);
		String userNickname = currentUser.getUserNickname();
		KakaoPaymentReady paymentInfo = null;
		ResponseEntity<KakaoPaymentReady> response;

		try {
			paymentInfo = kakaoService.kakaoPayReady(userNickname, quantity, price);
			response = new ResponseEntity<>(paymentInfo, HttpStatus.OK);
			RECENT_TOTAL_AMOUNT = price;
			RECENT_TID = paymentInfo.getTid();
		} catch (Exception e) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

		return response;
	}

	@ApiOperation(value="스푼 결제 승인 정보")
	@PostMapping(value = "/paymentSuccess")
	public ResponseEntity<KakaoPaymentApproval> paymentSuccess(@RequestHeader String authorization, 
			@RequestBody Map<String, String> paymentData) {

		String pgToken = paymentData.get("pgToken");
		User currentUser = userService.authentication(authorization);
		String userNickname = currentUser.getUserNickname();
		KakaoPaymentApproval approvalInfo = null;
		ResponseEntity<KakaoPaymentApproval> response;

		try {
			approvalInfo = kakaoService.kakaoPaySuccess(userNickname, RECENT_TID, pgToken, RECENT_TOTAL_AMOUNT);
			response = new ResponseEntity<>(approvalInfo, HttpStatus.OK);
			currentUser.setUserSpoon(currentUser.getUserSpoon() + approvalInfo.getQuantity());
			userService.updateInfo(currentUser);
		} catch (Exception e) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

		return response;
	}

	@ApiOperation(value="프로필 사진 업로드")
	@PostMapping(value = "/profileUpload")
	public ResponseEntity<Result> profileImageUpload(@RequestHeader Map<String, String> authorization, @RequestParam("userImage") MultipartFile[] files,
			HttpServletRequest request) {

		ResponseEntity<Result> response;
		System.out.println(authorization);
		User currUser = userService.authentication(authorization.get("authorization"));
		for(MultipartFile file : files)
		{
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			System.out.println(request.getServletContext());
//			File dest = new File(request.getServletContext().getRealPath("/") + fileName);
			System.out.println(request.getServletContext().getRealPath("/"));
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String uploadDate = simpleDateFormat.format(new Date());
				save(file, request.getServletContext().getRealPath("/"), uploadDate);
				currUser.setUserProfile(uploadDate + fileName);
				userService.updateInfo(currUser);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		}
		Result result = Result.successInstance();
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}  

	@ApiOperation(value="프로필 사진 가져오기")
	@GetMapping(value="/profileImage")
	public ResponseEntity<byte[]> getProfileImage(@RequestParam String authToken,
			HttpServletRequest request) {
		System.out.println("11111111111111111111111111111111111111111");
		ResponseEntity<byte[]> response = null;
		HttpHeaders header = new HttpHeaders();
		System.out.println(authToken);

		try {
			InputStream input = null;
			User currentUser = userService.authentication(authToken);
			String filePath = request.getServletContext().getRealPath("/") + currentUser.getUserProfile();
			System.out.println(filePath);
			String mimeType = Files.probeContentType(Paths.get(filePath));
			System.out.println(mimeType);
			input = new FileInputStream(filePath);

			header.setContentType(MediaType.parseMediaType(mimeType));
			response = new ResponseEntity<byte[]>(IOUtils.toByteArray(input), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<byte[]>(null, header, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@ApiOperation(value="이메일로 프로필 사진 가져오기")
	@GetMapping(value="/profileImageByEmail")
	public ResponseEntity<byte[]> getProfileImageByEmail(@RequestParam String userEmail,
			HttpServletRequest request) {
		ResponseEntity<byte[]> response = null;
		HttpHeaders header = new HttpHeaders();

		try {
			InputStream input = null;
			User currentUser = userRepository.findByUserEmail(userEmail);
			String filePath = request.getServletContext().getRealPath("/") + currentUser.getUserProfile();
			System.out.println(filePath);
			String mimeType = Files.probeContentType(Paths.get(filePath));
			System.out.println(mimeType);
			input = new FileInputStream(filePath);

			header.setContentType(MediaType.parseMediaType(mimeType));
			response = new ResponseEntity<byte[]>(IOUtils.toByteArray(input), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<byte[]>(null, header, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@ApiOperation(value="강사 등록대기 리스트")
	@GetMapping(value="/registrationList")
	public ResponseEntity<Set<User>> viewRegistrationList() {

		ResponseEntity<Set<User>> response = null;

		List<UserFile> userFileList = userService.getRegistrationUsers();
		Set<User> userList = new LinkedHashSet<User>();

		for(UserFile userFile : userFileList) {
			userList.add(userFile.getUserFileEmail());
		}

		response = new ResponseEntity<Set<User>>(userList, HttpStatus.OK);
		return response;
	}

	@ApiOperation(value="강사 인증사진 가져오기")
	@GetMapping(value="/registrationImage")
	public ResponseEntity<byte[]> getRegistrationImage(@RequestParam String userEmail,
			HttpServletRequest request) {

		System.out.println(userEmail);
		ResponseEntity<byte[]> response = null;
		HttpHeaders header = new HttpHeaders();

		try {
			InputStream input = null;
			List<UserFile> userFile = userService.getRegistrationImage(userEmail);

			String filePath = request.getServletContext().getRealPath("/") + userFile.get(0).getUserFile();
			System.out.println(filePath);
			String mimeType = Files.probeContentType(Paths.get(filePath));
			System.out.println(mimeType);
			input = new FileInputStream(filePath);

			header.setContentType(MediaType.IMAGE_PNG);
			response = new ResponseEntity<byte[]>(IOUtils.toByteArray(input), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<byte[]>(null, header, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@ApiOperation(value="유저를 강사로 등록")
	@GetMapping(value="/registration")
	public ResponseEntity<Set<User>> registerTeacher(@RequestParam String userEmail) {

		ResponseEntity<Set<User>> response = null;

		User registrationUser = userRepository.findByUserEmail(userEmail);
		registrationUser.setUserAuthority("TEACHER");
		registrationUser.setTeacherCode(generateRandomCode(12));
		userService.updateInfo(registrationUser);
		userService.registerUserToTeacher(userEmail);

		List<UserFile> userFileList = userService.getRegistrationUsers();
		Set<User> userList = new LinkedHashSet<User>();

		for(UserFile userFile : userFileList) {
			userList.add(userFile.getUserFileEmail());
		}

		response = new ResponseEntity<Set<User>>(userList, HttpStatus.OK);
		return response;
	}

	private String save(MultipartFile file, String contextPath, String uploadDate) {
		try {
			String newFileName = uploadDate + file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			//윈도우에서는 폴더가 없으면 생성이안됨
			Path path = Paths.get(contextPath + newFileName);
			Files.write(path,bytes);
			return newFileName; 

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	private String generateRandomCode(int length) {
		int leftLimit = 48;
		int rightLimit = 122;
		int targetStringLength = length;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		return generatedString;
	}
}
