package edu.wm.cs.gitprojectminer.codefeatures;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.wm.cs.gitprojectminer.config.ConfigString;

public class SeperateLicenseFileExtratorTest {
	private static File dir = new File(".");
	public static void main(String argv[]){
		
		String file="LICENSE_hive";
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
			Process proc2 = shell.exec(ConfigString.ninkalicense + " -d "+file,null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc2.getInputStream()));
			licenseRes = stdIn.readLine();
			System.out.println(licenseRes);
			
			
			
		}
		catch (IOException e) {
			System.out.println("Failed Running ninka to get licence Results for file:"+"file"+"\n");
			e.printStackTrace();
		}
	}

}
