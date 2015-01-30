package edu.wm.cs.gitprojectminer.codefeatures;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.wm.cs.gitprojectminer.config.ConfigString;

public class SrcFileLicenseExtractor {
	
	//private static String ninka="./ninka/ninka.pl";
	private static File dir = new File(".");
	
	public static String LicenseExtractor(String file) {
		String licenseRes="";
		try{
			
			Runtime shell = Runtime.getRuntime();
			Process proc = shell.exec(ConfigString.ninka + " -d "+file,null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			licenseRes = stdIn.readLine();
			
			
			
		}
		catch (IOException e) {
			System.out.println("Failed Running ninka to get licence Results for file:"+"file"+"\n");
			e.printStackTrace();
		}
		String delims = ";";
		String[] tokens = licenseRes.split(delims);
		if (tokens!=null&&tokens.length>1)
			return tokens[1];
		else return null;
		// TODO Auto-generated constructor stub
	}

}
