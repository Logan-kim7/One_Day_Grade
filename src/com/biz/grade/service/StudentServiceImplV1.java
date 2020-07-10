package com.biz.grade.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.IIOException;

import com.biz.grade.config.DBcontract;
import com.biz.grade.config.Lines;
import com.biz.grade.domain.ScoerVO;
import com.biz.grade.domain.StudentVO;

public class StudentServiceImplV1 implements StudentService {
	private List<StudentVO> studentList;
	private Scanner scan = new Scanner(System.in);
	private String fileName;

	@Override
	public List<StudentVO> getStudentList() {
		return studentList;
	}

	public StudentVO getStudentVO(String st_num) {
		// 1. studentVO를 null로 clear, null값을 studentVO에 할당

		StudentVO studentVO = null;
		// 2. studentList를 (순서대로) 뒤지면서
		for (StudentVO stVO : studentList) {
			// 3. 매개변수로 받은 st_num가 학생정보에서 나타나는지 검사
			// 4. 만약 있으면 해당하는 정보를 studentVO에 복사하고
			// 5. 반복문을 종료
			if (stVO.getNum().equals(st_num)) {
				studentVO = stVO;
				break;
			}
			// 6. 만약 sudentList에서 해당학번을 못찾으면
			// 반복문은 끝까지 진행할 것이다.
		}
		// 7. 만약 if, break를 만나고 for 문이 중단된 상태라면
		// studentVO에는 stVO가 담겨있을 것이고
		// 8. for 반복문이 끝까지 진행된 상태라면
		// studentVO에는 null 값이 담겨 있을 것이다.
		return studentVO;
	}

	public StudentServiceImplV1() {
		studentList = new ArrayList<StudentVO>();
		scan = new Scanner(System.in);
		fileName = "src/com/biz/grade/exec/data/student.txt";

	}

	@Override
	public void loadStudent() {
		FileReader fileReader = null;
		BufferedReader buffer = null;

		try {
			fileReader = new FileReader(this.fileName);
			buffer = new BufferedReader(fileReader);
			String reader = "";
			while (true) {
				reader = buffer.readLine();
				if (reader == null) {
					break;
				}
				String[] students = reader.split(":");
				StudentVO stVO = new StudentVO();
				stVO.setNum(students[DBcontract.STUDENT.ST_NUM]);
				stVO.setName(students[DBcontract.STUDENT.ST_NAME]);
				stVO.setDept(students[DBcontract.STUDENT.ST_DEPT]);

				String strGrades = students[DBcontract.STUDENT.ST_GRADE];
				int grades = Integer.valueOf(strGrades);
				stVO.setGrade(grades);

				stVO.setTel(students[DBcontract.STUDENT.ST_TEL]);
				studentList.add(stVO);

			}
			buffer.close();
			fileReader.close();

		} catch (FileNotFoundException e) {

			System.out.println("학생정보 파일 열기오류");

		} catch (IOException e) {
			System.out.println("학생정보 파일 읽기 오류");
		}
	}

	@Override
	public boolean inputStudent() {
		StudentVO stVO = new StudentVO();
		System.out.print("학번(END:종료)>> ");
		// 변수명 명명규칙
		// Camel case : 두단어 이상 사용할때 단어의 첫글자 대문자
		// snake case : 두단어 이상 사용할때 단어 사이를 _(under score) 사용
		String st_num = scan.nextLine();

		if (st_num.equalsIgnoreCase("END")) {
			return false;
		}
		int intNum = 0;
		try {
			intNum = Integer.valueOf(st_num);
			if (intNum < 0 || intNum > 99999) {
				System.out.println("학번은 1~ 99999까지만 가능");
				System.out.println("다시입력해주세요");
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println("학번은 숫자만 입력가능");
			System.out.println("입력한 문자열 :" + st_num);
		}
		// 00001 형식으로 만들기
		st_num = String.format("%05d", intNum);

		for (StudentVO sVO : studentList) {
			if (sVO.getNum().equals(st_num)) {
				System.out.println(st_num + "이미 등록되어 있는 학생입니다.");
				return true;
			}
		}
		stVO.setNum(st_num);

		System.out.print("이름(END:종료)>> ");
		String st_name = scan.nextLine();

		if (st_name.equalsIgnoreCase("END")) {
			return false;
		}
		stVO.setName(st_name);

		System.out.print("학과(END:종료)>> ");
		String st_dept = scan.nextLine();

		if (st_dept.equals("END")) {
			return false;
		}
		stVO.setDept(st_dept);

		System.out.println("학년(END:종료)>> ");
		String st_grade = scan.nextLine();

		if (st_grade.equalsIgnoreCase("END")) {
			return false;
		}
		int intgrade = 0;
		try {
			intgrade = Integer.valueOf(st_grade);
		} catch (Exception e) {
			System.out.println("학번은 숫자만 입력가능");
			System.out.println("입력한 문자열 :" + st_grade);
		}
		if (intgrade < 1 || intgrade > 4) {
			System.out.println("학년은 1~ 4까지만 가능");
			System.out.println("다시입력해주세요");
			return true;
		}

		stVO.setGrade(intgrade);

		System.out.print("전화번호>> ");
		String st_Tel = scan.nextLine();
		stVO.setTel(st_Tel);
		studentList.add(stVO);

		return true;

	}

	@Override
	public void savaStudent() {

		FileWriter fileWriter = null;
		PrintWriter pWriter = null;

		try {
			fileWriter = new FileWriter(this.fileName);
			pWriter = new PrintWriter(fileWriter);
			// 내부에 Writer buffer에 값을 기록
			for (StudentVO sVO : studentList) {
				pWriter.printf("%s:", sVO.getNum());
				pWriter.printf("%s:", sVO.getName());
				pWriter.printf("%s:", sVO.getDept());
				pWriter.printf("%d:", sVO.getGrade());
				pWriter.printf("%s\n", sVO.getTel());
			}
			// Writer buffer에 기록된 값을 파일에 저장하기
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@Override
	public void studentList() {

		System.out.println(Lines.dLine);
		System.out.println("학생 명부 리스트");
		System.out.println(Lines.dLine);
		System.out.print("학번\t|이름\t|학과\t|학년\t|전화번호\t|\n");
		for (StudentVO sVO : studentList) {
			System.out.printf("%s\t|", sVO.getNum());
			System.out.printf("%s\t|", sVO.getName());
			System.out.printf("%s\t|", sVO.getDept());
			System.out.printf("%s\t|", sVO.getGrade());
			System.out.printf("%s\t|\n", sVO.getTel());

		}
		System.out.println(Lines.dLine);

	}

}
