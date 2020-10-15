package com.ssafy.yogart.aicoach.model;

import java.io.Serializable;

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
@Table(name = "ai_course")
public class AicoachCourse implements Serializable {
	@Id
	@Column(name="ai_course_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="질문아이디")
	private Integer aiCourseId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false, cascade = CascadeType.ALL)
	@JoinColumn(name="ai_course_user_nickname", referencedColumnName="user_nickname")
    @ApiModelProperty(value="닉네임")
    private User aiCourseUserNickname;
	
	@Column(name="ai_course_name")
	@ApiModelProperty(value="코스이름")
	private String aiCourseName;
	
	@Column(name="ai_course_order")
	@ApiModelProperty(value="코스순서")
	private String aiCourseOrder;
}
