package com.ssafy.yogart.qna.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.ssafy.yogart.user.model.User;

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
@Table(name = "qna")
public class QnA implements Serializable {
	@Id
	@Column(name="qna_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="질문아이디")
	private Integer qnaId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false, cascade = CascadeType.DETACH)
	@JoinColumn(name="qna_user_email", referencedColumnName = "user_email")
    @ApiModelProperty(value="email")
    private User userEmail;
	
	@Column(name="qna_title")
    @ApiModelProperty(value="제목")
	private String qnaTitle;
	
	@Column(name="qna_content", columnDefinition = "longtext")
    @ApiModelProperty(value="내용")
	private String qnaContent;
	
	@CreationTimestamp
	@Column(insertable = false, updatable = false)
    @ApiModelProperty(value="생성일")
	private LocalDateTime createDate;
}
