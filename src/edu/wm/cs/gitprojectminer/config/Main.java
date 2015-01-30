package edu.wm.cs.gitprojectminer.config;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.wm.cs.gitprojectminer.datatype.ProjectType;

public class Main {

	public static void main(String argv[]){
		String inputFileName = argv[0];
		List<String> nameArray=new ArrayList<>();
		
		// Open the file that is the first
					// command line parameter
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(inputFileName);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null){
				nameArray.add(strLine);
				System.out.println("Project name : "+strLine);
				
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		
		String tmp;
		try{
			File dir = new File(ConfigString.LOCAL_REPO_DIR);
			Runtime shell = Runtime.getRuntime();
			Process proc = shell.exec("ls",null,dir);
			
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((tmp = stdIn.readLine()) != null){
				System.out.println("Project name : "+tmp);
				nameArray.add(tmp);
			}
		}
		catch (IOException e){
			System.out.println("Failed Running or Reading Results from 'git log':\n");
			e.printStackTrace();
		}
		*/
		
		//ProjectType newProject=new ProjectType(null,argv[0]);
		
		
		
		//nameArray.add("cxf");
		//nameArray.add("hadoop");
		//nameArray.add("hive");
		//nameArray.add("openjpa");
		//nameArray.add("jgit");
		//nameArray.add("eclipse.jdt.core");
		//nameArray.add("recommenders");
		//nameArray.add("pdt");
		//nameArray.add("android");
		//nameArray.add("android-beacon-library");
		//nameArray.add("android-async-http");
		//nameArray.add("StickyListHeaders");
		
		for (String name: nameArray){
			System.out.println("Project name : "+name);
			
			long startTime = System.nanoTime();
			
			ProjectType newProject=new ProjectType(null,name);
			
			System.out.println("Project url : "+newProject.getUrl());
			
			newProject.persistProject();
				
			
			long endTime = System.nanoTime();

			long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
			
			System.out.println("Total execution time: " + duration/1000000000.0 );
			
			
		}
		
		System.out.println("DONE test!!!");
		
		
	}

}
