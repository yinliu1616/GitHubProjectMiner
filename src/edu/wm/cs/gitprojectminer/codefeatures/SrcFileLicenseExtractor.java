package edu.wm.cs.gitprojectminer.codefeatures;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SrcFileLicenseExtractor {
	
	private static String ninka="./ninka/ninka.pl";
	private static File dir = new File(".");
	
	public static String LicenseExtractor(String file) {
		String licenseRes="";
		try{
			
			Runtime shell = Runtime.getRuntime();
			Process proc = shell.exec(ninka + " -d "+file,null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			licenseRes = stdIn.readLine();
			
			
			
		}
		catch (IOException e) {
			System.out.println("Failed Running ninka to get licence Results for file:"+"file"+"\n");
			e.printStackTrace();
		}
		
		
		return licenseRes;
		// TODO Auto-generated constructor stub
	}

}
