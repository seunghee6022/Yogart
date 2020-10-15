package com.ssafy.yogart.qna.model;

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

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "qna_reply")
public class QnAReply {
	@Id
	@Column(name="qna_reply_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="댓글번호")
	private Integer qnaReplyId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false, cascade = CascadeType.ALL)
	@JoinColumn(name="qna_reply_qna_id", referencedColumnName="qna_id")
	@ApiModelProperty(value="아이디")
	private QnA qnaId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false, cascade = CascadeType.ALL)
	@JoinColumn(name="qna_reply_user_email", referencedColumnName="user_email")
    @ApiModelProperty(value="email")
    private User userEmail;
	
	@Column(name="qna_reply_content", columnDefinition = "longtext")
    @ApiModelProperty(value="답글내용")
	private String qnaReplyContent;
	
	@CreationTimestamp
	@Column(insertable = false, updatable = false)
    @ApiModelProperty(value="생성일")
	private LocalDateTime createDate;
}
