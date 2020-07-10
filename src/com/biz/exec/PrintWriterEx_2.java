package com.biz.exec;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.print.attribute.standard.PrinterState;

public class PrintWriterEx_2 {
	
	public static void main(String[] args) {
		
		String fileName = "src/com/biz/grade/exec/data/test.txt";
		PrintStream pStream = null;
		
		try {
			pStream = new PrintStream(fileName);
			pStream.println("대한민국 만세");
			pStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
