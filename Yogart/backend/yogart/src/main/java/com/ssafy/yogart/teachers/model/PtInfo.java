package com.ssafy.yogart.teachers.model;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pt_info")
public class PtInfo implements Serializable {
	@Id
	@Column(name="pt_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="아이디")
    private Integer ptId;
   
    @Column(name="pt_name", unique = true)
    @ApiModelProperty(value="피티이름")
    private String ptName;
    
    @Column(name="pt_price")
    @ApiModelProperty(value="피티가격")
    private Integer ptPrice;
    
    @Column(name="pt_intro", columnDefinition = "text")
    @ApiModelProperty(value="피티소개")
    private String ptIntro = "PT를 소개해주세요.";
   
    @ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="pt_teacher_id", referencedColumnName = "id")
    @ApiModelProperty(value="선생님아이디")
    private User ptTeacherId;

	public PtInfo(String ptName, Integer ptPrice, String ptIntro, User ptTeacherId) {
		super();
		this.ptName = ptName;
		this.ptPrice = ptPrice;
		this.ptIntro = ptIntro;
		this.ptTeacherId = ptTeacherId;
	}
}
