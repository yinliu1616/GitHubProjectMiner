package edu.wm.cs.gitprojectminer.codefeatures;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SeperateLicenseFileExtractor {

	private static String ninka="./ninka/ninkaLICENSE.pl";
	private static File dir = new File(".");
	
	public static String LicenseExtractor(String file) {
		String licenseRes="";
		try{
			
			Runtime shell = Runtime.getRuntime();
			Process proc1 = shell.exec("cp "+file+" "+file+".comments",null,dir);
			try {
				proc1.waitFor();
			} catch (InterruptedException e) {
				System.out.println("Failed to copy LICENSE file "+file);
				e.printStackTrace();
			}
			Process proc2 = shell.exec(ninka + " -d "+file,null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc2.getInputStream()));
			licenseRes = stdIn.readLine();
			
			
			
		}
		catch (IOException e) {
			System.out.println("Failed Running ninka to get licence Results for file:"+"file"+"\n");
			e.printStackTrace();
		}
		String delims = ";";
		String[] tokens = licenseRes.split(delims);
		
		return tokens[1];
		// TODO Auto-generated constructor stub
	}

}
