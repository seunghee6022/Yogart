package com.ssafy.yogart.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;
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
@Table(name = "user_file")
public class UserFile implements Serializable {
	@Id
	@Column(name="user_file_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="아이디")
    private Integer userFileId;
   
	@ManyToOne(fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="user_file_email", referencedColumnName="user_email")
	@ApiModelProperty(value="email")
	private User userFileEmail;
    
	@Column(name="user_file", columnDefinition = "text")
    @ApiModelProperty(value="강사 인증사진")
    private String userFile;
        
    @CreationTimestamp
    @Column(insertable = false, updatable = false)
    @ApiModelProperty(value="생성일")
    private LocalDateTime fileCreateDate;

	public UserFile(User userFileEmail, String userFile) {
		this.userFileEmail = userFileEmail;
		this.userFile = userFile;
	}
}