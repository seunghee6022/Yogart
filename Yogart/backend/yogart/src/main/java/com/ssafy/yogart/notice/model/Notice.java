package com.ssafy.yogart.notice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice")
public class Notice {
	@Id
	@Column(name="notice_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="아이디")
	private Integer noticeId;
	
	@Column(name="notice_title")
    @ApiModelProperty(value="이름")
	private String noticeTitle;
	
	@Column(name="notice_content", columnDefinition = "longtext")
    @ApiModelProperty(value="이름")
	private String noticeContent;
	
	@Column(insertable = false, updatable = false)
    @ApiModelProperty(value="생성일")
	private LocalDateTime createDate;
}
