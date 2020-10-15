package com.ssafy.yogart.qna.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnAResult {
	private List<QnAReply> list;
	private boolean isAdmin;
}
