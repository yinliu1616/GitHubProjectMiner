package edu.wm.cs.gitprojectminer.jgitwrapper;

import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;

import edu.wm.cs.gitprojectminer.datatype.CommitType;

public class GitRepoMetaDataTest {

	public static void main(String[] args){
		
		String localPath="./localrepo/commons-lang";
		GitRepoMetaData metaParser=new GitRepoMetaData(localPath);
		List<CommitType> commits;
		try {
			commits=metaParser.getCommits();
			for (CommitType commit:commits){
				//System.out.println("\n"+"Commit: " + commit.getSha1()+"\n"+"\n"+commit.getDeveloper_email()+"\n"+commit.getTime()+"\n"+commit.getCommit_msg()+"\n"+commit.getProject_url());
			
			}
			System.out.println("Had " + commits.size() + " commits overall on current branch");
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	

}
