package edu.wm.cs.gitprojectminer.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommitLenDistribution {

	public static void main(String argv[]){
		
	}
	
	
	private static void readFile1(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);
	 
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	 
		br.close();
	}

}
