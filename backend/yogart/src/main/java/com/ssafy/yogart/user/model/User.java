package com.ssafy.yogart.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements Serializable {
   @Id
   @Column(name="id")
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   @ApiModelProperty(value="아이디")
    private Integer id;
   
    @Column(name="user_email", unique = true)
    @ApiModelProperty(value="email")
    private String userEmail;
    
    @Column(name="user_name", columnDefinition = "char")
    @ApiModelProperty(value="이름")
    private String userName;
   
    @Column(name="user_nickname", unique = true, nullable = false)
    @ApiModelProperty(value="닉네임")
    private String userNickname;
    
    @Column(name="user_password")
    @ApiModelProperty(value="비밀번호")
    private String userPassword;
    
    @Column(name="login_method")
    @ApiModelProperty(value="접속방식")
    private String loginMethod;
    
    @Column(name="user_authority")
    @ApiModelProperty(value="권한")
    private String userAuthority;
        
    @CreationTimestamp
    @Column(insertable = false, updatable = false)
    @ApiModelProperty(value="생성일")
    private LocalDateTime createDate = null;
    
    @Column(name="user_profile", columnDefinition="text")
    @ApiModelProperty(value="프로필사진")
    private String userProfile = "default.png";
    
    @Column(name="user_intro", columnDefinition="text")
    @ApiModelProperty(value="자기소개")
    private String userIntro = "자기소개를 해주세요.";
    
    @Column(name="user_spoon")
    @ApiModelProperty(value="스푼 갯수")
    private Integer userSpoon = 0;
    
    @Column(name="teacher_code")
    @ApiModelProperty(value="강사코드")
    private String teacherCode;

    public User(String userEmail, String userName, String userNickname, String userPassword) {
       this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
    }
    
    public User(String userEmail, String userName, String userNickname, String userPassword, String loginMethod) {
       this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.loginMethod = loginMethod;
    }
    
    public User(String userEmail, String userNickname, String loginMethod) {
       this.userEmail = userEmail;
       this.userName = userNickname;
        this.userNickname = userNickname;
        this.loginMethod = loginMethod;
    }  
}