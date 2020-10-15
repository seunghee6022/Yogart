package com.ssafy.yogart.mypage.model;

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

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "graph_time")
public class GraphTime implements Serializable {
	@Id
	@Column(name="graph_time_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="아이디")
	private Integer graphTimeId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false, cascade = CascadeType.ALL)
	@JoinColumn(name="graph_user_nickname", referencedColumnName="user_nickname")
    @ApiModelProperty(value="닉네임")
    private User graphUserNickname;
	
	@CreationTimestamp
    @Column(insertable = false, updatable = false)
	@ApiModelProperty(value="운동날짜")
	private LocalDateTime graphDateTime;
	
	@Column(name="graph_running_time")
	@ApiModelProperty(value="운동시간")
	private Integer graphRunningTime;
}
