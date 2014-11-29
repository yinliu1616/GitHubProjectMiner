package edu.wm.cs.gitprojectminer.config;

import java.util.ArrayList;
import java.util.List;

import edu.wm.cs.gitprojectminer.datatype.ProjectType;

public class Main {

	public static void main(String argv[]){
		ProjectType newProject=new ProjectType(null,argv[0]);
		//ProjectType newProject=new ProjectType(null,"android-beacon-library");
		System.out.println("Project url : "+newProject.getUrl());
		newProject.persistProject();
		
		System.out.println("DONE test!!!");
	}

}
