package com.biz.grade.service;
/*
 * 
 * 파일을 List에 담기
 * 학생정보를 입력받아 List에 담기
 * List에 담긴 학생정보를 파일에 저장
 * 
 * 
 */

import java.util.List;

import com.biz.grade.domain.StudentVO;

public interface StudentService {
	
	
	public void loadStudent();
	public boolean inputStudent();
	public void savaStudent();
	public void studentList();
	
	public List<StudentVO> getStudentList();

	public StudentVO getStudentVO(String st_num);
}
