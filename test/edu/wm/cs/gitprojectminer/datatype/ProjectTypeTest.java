package edu.wm.cs.gitprojectminer.datatype;

public class ProjectTypeTest {

	public static void main(String argv[]){
		//String url="https://github.com/AltBeacon/android-beacon-library.git";
		//String url="https://github.com/eclipse/jgit.git";
		//String url="https://github.com/apache/commons-lang.git";
		String url="https://github.com/centic9/jgit-cookbook.git";
		ProjectType newProject=new ProjectType(url);
		System.out.println("Project Name : "+newProject.getName());
		newProject.persistProject();
		System.out.println("DONE test!!!");
	}

}
