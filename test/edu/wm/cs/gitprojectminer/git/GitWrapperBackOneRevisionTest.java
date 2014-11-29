package edu.wm.cs.gitprojectminer.git;

public class GitWrapperBackOneRevisionTest {

	public static void main(String argv[]){
		GitWrapper gw = new GitWrapper();
		
		// Test path: 
		String path = "./localrepo/jgit";
		String result=gw.backOneRevisionCLI(path);
		

		
		System.out.println("DONE: "+result);
		
	}

}
