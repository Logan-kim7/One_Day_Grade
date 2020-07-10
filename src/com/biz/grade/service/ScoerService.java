package com.biz.grade.service;

import com.biz.grade.domain.ScoerVO;

public interface ScoerService {
	
	
	
	public void loadScore();
	public boolean inputScore();
	
	public void saveScoerVO(ScoerVO scoerVO);
	
	public void calcSum();
	public void calcAvg();
	
	public void saveScore();
	public void scoerList();
}
