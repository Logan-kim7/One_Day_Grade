package com.biz.grade.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.grade.vo.ScoreVO;
import com.biz.grade.vo.StudentVO;
import com.biz.stsc.config.Lines;

public class StudentServiceImplV1 implements StService {
	Scanner scn;
	List<StudentVO> stList;
	String stFileName;

	public StudentServiceImplV1() {
		scn = new Scanner(System.in);
		stList = new ArrayList<StudentVO>();

	}

	@Override
	public boolean stInput() {
		// 학번 이름 학과 학년 전화번호
		StudentVO stVO = new StudentVO();

		System.out.println("학번을 입력해주세요>>[END]입력시 종료");
		String strStNum = scn.nextLine();

		if (strStNum.equalsIgnoreCase("END")) {
			return true;
		}

		try {
			int intStNum = (Integer.valueOf(strStNum));
			if (intStNum >= 99999) {
				System.out.println("입력할수 있는 학번의 개수를 초과했습니다.");

				return true;
			}
			strStNum = String.format("%05d", intStNum);
		} catch (NumberFormatException e) {
			System.out.println("형식에 맞지않는 입력입니다 다시입력하세요");

		}

		stVO.setStrStNum(strStNum);

		System.out.println("이름을 입력해주세요");
		String strStName = scn.nextLine();

		if (strStName.equals("")) {
			return true;
		}

		stVO.setStrStName(strStName);

		System.out.println("학과를 입력해주세요");
		String strDeep = scn.nextLine();
		if (strDeep.equals("")) {
			return true;
		}
		stVO.setStrDeep(strDeep);

		System.out.println("학년을 입력해주세요");
		String strGrade = scn.nextLine();

		int intGrade = 0;
		try {
			intGrade = Integer.valueOf(strGrade);
			if(intGrade >= 7) {
				System.out.println("대학교는 오래다닐수없어요 취업하세요 ; ) ");
				System.out.println("대학원생 화석이시라면 학부시스템을 이용하세요");
				return true;
			}
		} catch (NullPointerException e) {
			System.out.println("학년 입력은 숫자만가능합니다.");
			return true;
		}
		stVO.setIntGrade(intGrade);

		System.out.println("핸드폰 번호를 입력하세요");
		String strPhNum = scn.nextLine();

		if (strPhNum.equals(null)) {
			System.out.println("핸드폰번호를 다시입력하세요");
			return true;
		}

		stVO.setStrPhNum(strPhNum);

		stList.add(stVO);

		return true;
	}

	@Override
	public void stList() {

		String[] listTitle = { "학번", "이름", "학과", "학년", "전화번호" };
		System.out.println(Lines.dLine);
		System.out.println("학생정보 입력 결과");
		System.out.println(Lines.dLine);
		System.out.printf("%-20s\t|%17s\t|%15s\t|%12s\t%9s\t\n|", listTitle[0], listTitle[1], listTitle[2],
				listTitle[3], listTitle[4]);
		System.out.println(Lines.sLine);

		for (StudentVO sVO : stList) {
			System.out.printf("\t%s\t|", sVO.getStrStNum() + "\t");
			System.out.printf("\t%s\t|", sVO.getStrStName() + "\t");
			System.out.printf("\t%s\t|", sVO.getStrDeep() + "\t");
			System.out.printf("\t%s\t|", sVO.getIntGrade() + "\t");
			System.out.printf("\t%s\t|", sVO.getStrPhNum() + "\t");
		}
		System.out.println();
		System.out.println(Lines.dLine);
		System.out.println("출력완료!! 아무키나 누르세요");
		scn.nextLine();

	}

	@Override
	public void stSave() {
		PrintStream outPut = null;
		stFileName = "src/com/biz/exec/score.txt";

		try {
			outPut = new PrintStream(stFileName);
		} catch (FileNotFoundException e) {
			System.out.println("스코어 파일을 만들수 없습니다.");
		}

		for (StudentVO sVO : stList) {
			outPut.println(String.format(("%s:%s:%s:%s:%s:%s:%s"), sVO.getStrStNum(), sVO.getStrStName(), sVO.getStrDeep(),
					sVO.getIntGrade(), sVO.getStrPhNum()));

		}
		outPut.close();

	}

	@Override
	public void stLoad() {

		stList.clear();

		FileReader fileReader = null;
		BufferedReader buffer = null;

		stFileName = "src/com/biz/exec/student.txt";

		try {
			fileReader = new FileReader(stFileName);
			buffer = new BufferedReader(fileReader);

			String reader = "";
			while (true) {
				reader = buffer.readLine();
				if (reader == null) {
					break;
				}
				String[] balances = reader.split(":");

				ScoreVO scVO = new ScoreVO();
			}
			buffer.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾을수 없습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("파일을 읽어올수 없습니다.");
		}

	}
}
