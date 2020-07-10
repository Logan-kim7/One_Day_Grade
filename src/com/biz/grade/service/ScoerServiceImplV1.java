package com.biz.grade.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.biz.grade.config.DBcontract;
import com.biz.grade.config.Lines;
import com.biz.grade.domain.ScoerVO;
import com.biz.grade.domain.StudentVO;

public class ScoerServiceImplV1 implements ScoerService {

	private List<ScoerVO> scoreList;
	private Scanner scan = new Scanner(System.in);
	private String fileName;

	// 학생정보에서 학번이 등록되어 있는지 확인
	// 과목명 문자열 배열로 선언하고.
	// 과목명 문자열 배열 개수만큼 점수를 담을 intScoers배열을 선언
	private String[] strSubJects;
	private Integer[] intScores;
	private int[] totalSum;
	private int[] totalAvg;

	StudentService stService;

	public ScoerServiceImplV1() {
		scoreList = new ArrayList<ScoerVO>();
		scan = new Scanner(System.in);
		fileName = "src/com/biz/grade/exec/data/Score.txt";

		strSubJects = new String[] { "국어", "영어", "수학", "음악" };
		intScores = new Integer[(strSubJects.length)];
		totalSum = new int[strSubJects.length];
		totalAvg = new int[strSubJects.length];
		stService = new StudentServiceImplV1();
		stService.loadStudent();

	}

	@Override
	public void loadScore() {
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
				String[] scoers = reader.split(":");
				ScoerVO scVO = new ScoerVO();
				scVO.setNum(scoers[DBcontract.STUDENT.ST_NUM]);
				scVO.setNum(scoers[Integer.valueOf(DBcontract.STUDENT.ST_GRADE)]);
				scVO.setNum(scoers[Integer.valueOf(DBcontract.SCORE.SC_KOR)]);
				scVO.setNum(scoers[Integer.valueOf(DBcontract.SCORE.SC_ENG)]);
				scVO.setNum(scoers[Integer.valueOf(DBcontract.SCORE.SC_MATH)]);
				scVO.setNum(scoers[Integer.valueOf(DBcontract.SCORE.SC_MUSIC)]);
				scVO.setNum(scoers[Integer.valueOf(DBcontract.SCORE.SC_SUM)]);
				scVO.setNum(scoers[Integer.valueOf(DBcontract.SCORE.SC_AVG)]);
				scoreList.add(scVO);

			}
			buffer.close();
			fileReader.close();

		} catch (FileNotFoundException e) {

			System.out.println("학생정보 파일 열기오류");

		} catch (IOException e) {
			System.out.println("학생정보 파일 읽기 오류");
		}
	}
	// return type를 int(primitive)가 아닌
	// Integer (Wrapper Class)로 설정

	// sc_score(매개변수)로 전달받은 값을 검사하는 코드
	// END 문자열을 받았으면 -1을 return
	// 숫자로 바꿀수 없는 문자열, 점수범위를 벗어나는 값이면 null 을 return
	// 정상적이문 문자열을 정수로 바꾸어 return

	private Integer scoerCheck(String sc_score) {

		// 만약 END를 입엵했으면 -1을 return 해라
		if (sc_score.equals("END")) {
			return -1;
		}

		/*
		 * int intScoer = null : 오류가 발생하는 코드 primitive int 형 변수는 null 값으로 clear, 초기화를 할수
		 * 없다. Integer intScoer = null : 정상적인 코드 Wrapper class Integer 형변수는 null 값으로
		 * clear, 초기화 할수 있다.
		 */
		Integer intScore = null;
		try {
			intScore = Integer.valueOf(sc_score);
		} catch (Exception e) {
			System.out.println("학번은 숫자만 입력가능");
			System.out.println("입력한 문자열 :" + sc_score);
			return null;
		}
		if (intScore < 0 || intScore > 100) {
			System.out.println("학번은 1~ 100까지만 가능");
			System.out.println("다시입력해주세요");
			return null;
		}
		return intScore;
	}

	@Override
	public boolean inputScore() {

		ScoerVO scoreVO = new ScoerVO();
		System.out.println("학번(END:종료) >>");
		String st_num = scan.nextLine();

		if (st_num.equals("END")) {
			return false;
		}
		int intNum = 0;
		try {
			intNum = Integer.valueOf(st_num);
		} catch (Exception e) {
			System.out.println("학번은 숫자만 입력가능");
			System.out.println("입력한 문자열 :" + st_num);
		}
		if (intNum < 0 || intNum > 99999) {
			System.out.println("학번은 1~ 99999까지만 가능");
			System.out.println("다시입력해주세요");
			return true;
		}
		// 00001 형식으로 만들기
		st_num = String.format("%05d", intNum);

		for (ScoerVO sVO : scoreList) {
			if (sVO.getNum().equals(st_num)) {
				System.out.println("이미 등록되어 있는 학생입니다.");
				return true;
			}
		}
		StudentVO retVO = stService.getStudentVO(st_num);
		if (retVO == null) {
			System.out.println(st_num + "학생정보가 학적부에 없음");
			System.out.println("성적을 입력할 수 없음");
			return true;
		}

		for (int i = 0; i < strSubJects.length; i++) {

			System.out.printf("%s 점수(END:종료)", strSubJects[i]);
			String sc_score = scan.nextLine();

			// intScoer -1, null, 숫자 값이 담겨지게 된다.
			Integer intScore = this.scoerCheck(sc_score);
			if (intScore == null) { // 입력값 오류!!
				// 만약 입력한 점수가 오류(문자열, 범위)가 발생했다면
				// for() 반복문의 i값을 -1감소시키고
				// 다시 for()을 시작하도록 한다.
				// 국어점수에서 이러한 일이 발생한다면
				// 계속해서 국어줌수를 입력ㅂㄱ다는 화면이 반복해서 나타날것이다.
				i--;
				continue;
			} else if (intScore < 0) {
				return false;
			}
			// 모든것이 정상이면 점수배열에 값을 저장하자
			intScores[i] = intScore;

		}
		scoreVO.setNum(st_num);
		scoreVO.setKor(intScores[0]);
		scoreVO.setEng(intScores[1]);
		scoreVO.setMath(intScores[2]);
		scoreVO.setMusic(intScores[3]);
		scoreList.add(scoreVO);
		this.saveScoerVO(scoreVO); // 1명의 데이터를 추가 저장하기
		return true;
	}

	@Override
	public void saveScore() {

	}

	@Override
	public void scoerList() {
		Arrays.fill(totalSum, 0);
		Arrays.fill(totalAvg, 0);

		System.out.println(Lines.dLine);
		System.out.println("학생 명부 리스트");
		System.out.println(Lines.dLine);
		System.out.println("학번\t|이름\t|국어\t|영어\t|수학\t|음악\t|총점\t|평균\t|");
		for (ScoerVO sVO : scoreList) {
			System.out.printf("%s\t|", sVO.getNum());

			StudentVO retVO = stService.getStudentVO(sVO.getNum());
			String st_name = "[없음]";
			if (retVO != null) {
				st_name = retVO.getName();
			}

			System.out.printf("%s\t|", "이름");
			System.out.printf("%s\t|", sVO.getKor());
			System.out.printf("%s\t|", sVO.getEng());
			System.out.printf("%s\t|", sVO.getMath());
			System.out.printf("%s\t|", sVO.getMusic());
			System.out.printf("%s\t|", sVO.getSum());
			System.out.printf("%s\t|\n", sVO.getAvg());

			totalSum[0] += sVO.getKor();
			totalSum[1] += sVO.getEng();
			totalSum[2] += sVO.getMath();
			totalSum[3] += sVO.getMusic();
		}
		System.out.println(Lines.sLine);
		System.out.print("과목총점:\t|");
		int sumAndSum = 0;

		for (int sum : totalSum) {
			System.out.printf("%s\t|", sum);
			sumAndSum += sum;
		}
		System.out.printf("%s\t|", sumAndSum);

		System.out.print(Lines.sLine);
		System.out.print("과목평균:\t|");
		float avgAndAvg = 0f;
		for (int sum : totalSum) {
			float avg = sum / (float) sum / scoreList.size();
			System.out.printf("%5.2f\t|", avg);
			avgAndAvg += avg;
		}
		System.out.printf("\t%5.2f\t|\n|", avgAndAvg / totalSum.length);
	}

	@Override
	public void saveScoerVO(ScoerVO scoerVO) {

		FileWriter fileWriter = null;
		PrintWriter pWriter = null;

		try {
			fileWriter = new FileWriter(this.fileName, true);
			pWriter = new PrintWriter(fileWriter);
			// 내부에 Writer buffer에 값을 기록
			pWriter.printf("%s:", scoerVO.getNum());
			pWriter.printf("%d:", scoerVO.getKor());
			pWriter.printf("%d:", scoerVO.getEng());
			pWriter.printf("%d:", scoerVO.getMath());
			pWriter.printf("%d\n", scoerVO.getMusic());
			// Writer buffer에 기록된 값을 파일에 저장하기
			pWriter.flush();
			pWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("성적 데이터를 파일에 저장하지 못했습니다.");
		}

	}

	@Override
	public void calcSum() {

		for (ScoerVO scoreVO : scoreList) {
			int sum = scoreVO.getKor();
			sum += scoreVO.getEng();
			sum += scoreVO.getMath();
			sum += scoreVO.getMusic();

		}

	}

	@Override
	public void calcAvg() {

		for (ScoerVO scoreVO : scoreList) {
			int sum = scoreVO.getSum();
			float avg = (float) sum / 4;

		}
	}

}
