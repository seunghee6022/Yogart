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
@Table(name = "graph_body_part")
public class GraphBodyPart implements Serializable {
	@Id
	@Column(name="graph_body_part_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="아이디")
	private Integer graphBodyPartId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional=false, cascade = CascadeType.ALL)
	@JoinColumn(name="graph_body_part_user_nickname", referencedColumnName="user_nickname")
    @ApiModelProperty(value="닉네임")
    private User graphBodyPartUserNickname;
	
	@Column(name="whole_body")
	@ApiModelProperty(value="전신")
	private Integer wholeBody;
	
	@Column(name="spine")
	@ApiModelProperty(value="척추")
	private Integer spine;
	
	@Column(name="Abs")
	@ApiModelProperty(value="복근")
	private Integer Abs;
	
	@Column(name="arm")
	@ApiModelProperty(value="팔")
	private Integer arm;
	
	@Column(name="leg")
	@ApiModelProperty(value="다리")
	private Integer leg;
	
	@Column(name="relaxing")
	@ApiModelProperty(value="릴렉싱")
	private Integer relaxing;
	
	@Column(name="energy")
	@ApiModelProperty(value="에너지")
	private Integer energy;
	
    @Column(name="start_date_time", unique=true)
	@ApiModelProperty(value="시작시간")
	private LocalDateTime startDateTime;

	public GraphBodyPart(User graphBodyPartUserNickname, Integer wholeBody, Integer spine, Integer abs, Integer arm,
			Integer leg, Integer relaxing, Integer energy, LocalDateTime startDateTime) {
		super();
		this.graphBodyPartUserNickname = graphBodyPartUserNickname;
		this.wholeBody = wholeBody;
		this.spine = spine;
		Abs = abs;
		this.arm = arm;
		this.leg = leg;
		this.relaxing = relaxing;
		this.energy = energy;
		this.startDateTime = startDateTime;
	}

	public GraphBodyPart(User graphBodyPartUserNickname, Integer wholeBody, Integer spine, Integer abs, Integer arm,
			Integer leg, Integer relaxing, Integer energy) {
		super();
		this.graphBodyPartUserNickname = graphBodyPartUserNickname;
		this.wholeBody = wholeBody;
		this.spine = spine;
		Abs = abs;
		this.arm = arm;
		this.leg = leg;
		this.relaxing = relaxing;
		this.energy = energy;
	}

}
