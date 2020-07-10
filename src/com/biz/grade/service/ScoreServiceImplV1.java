package com.biz.grade.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.print.attribute.standard.PrinterState;

import com.biz.grade.vo.ScoreVO;
import com.biz.grade.vo.StudentVO;
import com.biz.stsc.config.Lines;

public class ScoreServiceImplV1 implements ScService {
	Scanner scn;
	List<ScoreVO> scList;
	String scFileName;

	public ScoreServiceImplV1() {
		scn = new Scanner(System.in);
		scList = new ArrayList<ScoreVO>();
	}

	@Override
	public boolean scInput() {
		ScoreVO scVO = new ScoreVO();

		System.out.println("국어성적 입력>>[END]입력시 종료 :");
		String strKor = scn.nextLine();

		if (strKor.equalsIgnoreCase("END")) {
			return true;
		}

		int intKor = 0;
		try {
			intKor = Integer.valueOf(strKor);
			if (intKor > 100 || intKor < 0) {
				System.out.println("점수는 100점까지만입력가능!!!");
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println("성적입력은 숫자만가능합니다.");

			return true;
		}
		scVO.setIntKor(intKor);

		System.out.println("영어성적 입력>> :");
		String strEng = scn.nextLine();

		int intEng = 0;

		try {
			intEng = Integer.valueOf(strEng);
			if (intEng > 100 || intEng < 0) {
				System.out.println("점수는 100점까지만입력가능!!!");
				return true;

			}

		} catch (NumberFormatException e) {
			System.out.println("성적입력은 숫자만가능합니다.");

			return true;
		}
		scVO.setIntEng(intEng);

		System.out.println("수학성적 입력>> :");
		String strMath = scn.nextLine();

		int intMath = 0;

		try {
			intMath = Integer.valueOf(strMath);
			if (intMath > 100 || intMath < 0) {
				System.out.println("점수는 100점까지만입력가능!!!");
				return true;
			}

		} catch (NumberFormatException e) {
			System.out.println("성적입력은 숫자만가능합니다.");

			return true;
		}
		scVO.setIntMath(intMath);

		System.out.println("음악성적 입력>> :");
		String strMusic = scn.nextLine();

		int intMusic = 0;
		if (intMusic > 100 || intMusic < 0) {
			System.out.println("점수는 100점까지만입력가능!!!");
			return true;
		}

		try {
			intMusic = Integer.valueOf(strMusic);

		} catch (NumberFormatException e) {
			System.out.println("성적입력은 숫자만가능합니다.");

			return true;
		}
		scVO.setIntMusic(intMusic);
		scList.add(scVO);
		return true;

	}

	


	@Override
	public void scSave() {
		PrintStream outPut = null;
		scFileName = "src/com/biz/exec/score.txt";
		
		try {
			outPut = new PrintStream(scFileName);
		}catch(FileNotFoundException e){
			System.out.println("스코어 파일을 만들수 없습니다.");
		}
		
		for(ScoreVO sVO : scList) {
			outPut.println(String.format("%s:%d:%d:%d:%d:%d:%d", sVO.getStrNum(),
					sVO.getIntKor(), sVO.getIntEng(),sVO.getIntMath(),sVO.getIntMusic(),
					sVO.getIntSum(),sVO.getIntAvg()));
			
			
		}
		outPut.close();

	}
	
	@Override
	public void scLoad() {
		
		
		scList.clear();
		
		FileReader fileReader = null;
		BufferedReader buffer = null;
		
		scFileName = "src/com/biz/exec/score.txt";
		
		try {
			fileReader = new FileReader(scFileName);
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
		}catch(FileNotFoundException e) {
			System.out.println("파일을 찾을수 없습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("파일을 읽어올수 없습니다.");
		}
		
	}

	@Override
	public void calcSum() {
		
		int sum = 0;
		for(ScoreVO sVO : scList) {
			
			sum = (sVO.getIntKor() + sVO.getIntEng() +sVO.getIntMath() +sVO.getIntMusic());
		sVO.setIntSum(sum);
		}
		
	}

	@Override
	public void calcAvg() {
		int avg = 0;
		for(ScoreVO sVO : scList) {
			avg = sVO.getIntSum() /4;
			sVO.setIntAvg(avg);
		}
		
	}
	@Override
	public void scList() {
		String[] listTitle = { "국어", "영어", "수학", "음악" };
		System.out.println(Lines.dLine);
		System.out.println("성적 입력 결과");
		System.out.println(Lines.dLine);
		System.out.printf("%-20s\t|%17s\t|%15s\t|%12s\t\t|\n", listTitle[0], listTitle[1], listTitle[2], listTitle[3]);
		System.out.println(Lines.sLine);

		int intKorSum = 0;
		int intEngSum = 0;
		int intMathSum = 0;
		int intMusicSum = 0;
		int intAvg = 0;
		
		for (ScoreVO sVO : scList) {
			System.out.printf("\t%s\t|", sVO.getIntKor() + "\t");
			System.out.printf("\t%s\t|", sVO.getIntEng() + "\t");
			System.out.printf("\t%s\t|", sVO.getIntMath() + "\t");
			System.out.printf("\t%s\t|", sVO.getIntMusic() + "\t");
			System.out.println();
			
			intKorSum +=  sVO.getIntKor();
			intEngSum +=  sVO.getIntEng();
			intMathSum += sVO.getIntMath();
			intMusicSum += sVO.getIntMusic();
			intAvg += sVO.getIntSum() /4;
		}
		System.out.println(Lines.sLine);
		// 각 과목별 총점과 평균 표시
		// 먼저 각 과목별 총점과 평균 계산
		System.out.print("합계 : ");
		System.out.printf("%-15d\t|%17d\t|%15d\t|%12d\t\t|\n", intKorSum, intEngSum, intMathSum,intMusicSum );
		System.out.print("평균 : ");
		System.out.printf("%-15d\t|%17d\t|%15d\t|%12d\t\t|\n", intKorSum/4, intEngSum/4, intMathSum/4,intMusicSum/4 );
		System.out.println(Lines.dLine);
		System.out.println("출력완료!! 아무키나 누르세요");
		scn.nextLine();
		
	}
}
