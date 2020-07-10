package com.biz.exec;

import java.util.Scanner;

import com.biz.grade.service.ScService;
import com.biz.grade.service.ScoreServiceImplV1;
import com.biz.grade.service.StService;
import com.biz.grade.service.StudentServiceImplV1;
import com.biz.grade.vo.ScoreVO;

public class ComplitedSc_01 {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		ScService scservice = new ScoreServiceImplV1();
		StService stservice = new StudentServiceImplV1();
		

		while (true) {
			System.out.println("======================================");
			System.out.println("학생 성적처리 시스템 V3");
			System.out.println("======================================");
			System.out.println("1. 성적입력");
			System.out.println("2. 성적 정보 저장");
			System.out.println("3. 학생 정보 입력");
			System.out.println("4. 학생 자료 저장");
			System.out.println("5. 학생정보 & 성적자료 출력");
			System.out.println("6. 성적 자료 불러오기");
			System.out.println("7. 학생 자료 불러오기");
			System.out.println("-1. 업무종료");
			System.out.println("======================================");
			System.out.println("-업무선택 >> ");

			String strMenu = scn.nextLine();
			int intMenu = 0;
			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("메뉴선택은 숫자로만 하세요");
				continue;
			}
			if (intMenu == -1) {
				break;
			} else if (intMenu == 1) {
				scservice.scInput();

			} else if (intMenu == 2) {
				scservice.scSave();
				
			} else if (intMenu == 3) {
				stservice.stInput();
				
			} else if (intMenu == 4) {
				stservice.stSave();
			} else if (intMenu == 5) {
				scservice.scList();
			} else if (intMenu == 6) {
				scservice.scLoad();
			} else if (intMenu == 7) {
				stservice.stLoad();
			}else {

				System.out.println("선택된 업무가 없습니다.");
		}
   }
 }
	}


