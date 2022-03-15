package io.yoshizaki2104.xcutpomverup.cli.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReadUtils {

	public static String readFrom(String path) throws FileNotFoundException, IOException {
		
		StringBuilder sb = new StringBuilder();
		try(FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr);){
			char[] charArrayData = new char[1024];
			int readCharNum = br.read(charArrayData);
			while(readCharNum!=-1) {
				sb.append(charArrayData, 0, readCharNum);
				readCharNum = br.read(charArrayData);
			}
		} finally {
			
		}
		return sb.toString();
	}
	
	public static String determineLineBreak(String path) throws FileNotFoundException, IOException {
		
		String fileContent = readFrom(path);
		int crSize = count(fileContent,"\r\n");
		return crSize==0 ? "\n" : "\r\n";
	}
	
	public static int count(String text, String target) {
		int c = 0;
		int i = 0;
		int len = text.length();
		
		while((i=text.indexOf(target, i))!=-1) {
			c++;
			i += len;
		}
		
		return c;
	}
}
