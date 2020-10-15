package com.ssafy.yogart.teachers.model;

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

import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "pt_clicked")
public class PtClicked implements Serializable {
	@Id
	@Column(name="pt_clicked_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="아이디")
    private Integer ptClickedId;
   
    @Column(name="pt_day")
    @ApiModelProperty(value="pt일")
    private Integer ptDay;
    
    @Column(name="pt_time")
    @ApiModelProperty(value="pt시")
    private Integer ptTime;
    
    @ManyToOne(fetch = FetchType.EAGER, optional=false, cascade = CascadeType.ALL)
	@JoinColumn(name="pt_student_id", referencedColumnName = "id")
    @ApiModelProperty(value="학생번호")
    private User ptStudentId;
    
    @Column(name="is_attend")
    @ApiModelProperty(value="참석여부")
    private Boolean isAttend;
    
    @Column(name="date_time")
    @ApiModelProperty(value="예약된날짜")
    private LocalDateTime dateTime;
    
    @ManyToOne(fetch = FetchType.EAGER, optional=false, cascade = CascadeType.DETACH)
	@JoinColumn(name="pt_clicked_name", referencedColumnName = "pt_name")
    @ApiModelProperty(value="피티이름")
    private PtInfo ptClickedName;

	public PtClicked(Integer ptDay, Integer ptTime, PtInfo ptClickedName) {
		super();
		this.ptDay = ptDay;
		this.ptTime = ptTime;
		this.ptClickedName = ptClickedName;
	}

	public PtClicked(Integer ptDay, Integer ptTime, User ptStudentId, Boolean isAttend, LocalDateTime dateTime,
			PtInfo ptClickedName) {
		super();
		this.ptDay = ptDay;
		this.ptTime = ptTime;
		this.ptStudentId = ptStudentId;
		this.isAttend = isAttend;
		this.dateTime = dateTime;
		this.ptClickedName = ptClickedName;
	}   
	
	
    
}
