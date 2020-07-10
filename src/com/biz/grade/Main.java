package com.biz.grade;

import com.biz.grade.service.ScService;
import com.biz.grade.service.ScoreServiceImplV1;
import com.biz.grade.vo.ScoreVO;

public class Main {

	public static void main(String[] args) {
		ScService scoreVO = new ScoreServiceImplV1();
		scoreVO.scInput();
		scoreVO.scList();
	}
}