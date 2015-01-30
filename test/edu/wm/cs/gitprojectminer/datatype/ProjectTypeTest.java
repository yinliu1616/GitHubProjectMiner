package edu.wm.cs.gitprojectminer.datatype;

import java.util.ArrayList;
import java.util.List;

public class ProjectTypeTest {

	public static void main(String argv[]){
		//String url="https://github.com/AltBeacon/android-beacon-library.git";
		//String url="https://github.com/eclipse/jgit.git";
		//String url="https://github.com/apache/commons-lang.git";
		//String url="https://github.com/centic9/jgit-cookbook.git";
		List<String> urlArray=new ArrayList<>();
		urlArray.add("https://github.com/apache/camel.git");
		urlArray.add("https://github.com/apache/cxf.git");
		urlArray.add("https://github.com/apache/derby.git");
		urlArray.add("https://github.com/apache/felix.git");
		urlArray.add("https://github.com/apache/hadoop.git");
		urlArray.add("https://github.com/apache/hbase.git");
		urlArray.add("https://github.com/apache/hive.git");
		urlArray.add("https://github.com/apache/lucene.git");
		urlArray.add("https://github.com/apache/openejb.git");
		urlArray.add("https://github.com/apache/openjpa.git");
		urlArray.add("https://github.com/apache/qpid.git");
		urlArray.add("https://github.com/apache/wicket.git");
		
		for (String url: urlArray){
			ProjectType newProject=new ProjectType(url,null);
			System.out.println("Project Name : "+newProject.getName());
			newProject.persistProject();
		}
		
		System.out.println("DONE test!!!");
	}

}
