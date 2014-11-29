package edu.wm.cs.gitprojectminer.git;

public class GitWrapperSetRevisionCLITest {

	public static void main(String argv[]){
		GitWrapper gw = new GitWrapper();
		
		// Test path: 
		String path = "./localrepo/jgit";
		
		//Head :2532c28cb93ccb8e674ef374f08fb3bf246c353b
		//8a398a91477b5e03fcb1dfb2d96e066b5bf3434c
		//c017ac4c85cab7449a49b681843b9bc33e54d2b6
		
		String commit_id="2532c28cb93ccb8e674ef374f08fb3bf246c353b";
		String result=gw.setRevisionCLI(path, commit_id);
		

		
		System.out.println("DONE: "+result);
		
	}

}
