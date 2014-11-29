package edu.wm.cs.gitprojectminer.jgitwrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import edu.wm.cs.gitprojectminer.config.ConfigString;
import edu.wm.cs.gitprojectminer.datatype.CommitType;

public class GitRepoMetaData {
	//private static final int MAX_COMMITMSG=255;
	private String localPath;
	private Repository repo;
	private String url;
	// Set path upon creation
	public GitRepoMetaData(String localPath) {
		this.localPath = localPath;
		try {
			this.repo = openJGitRepository();
		} catch (IOException e) {
			System.err.println("Failed to create Repository instance:\n");
			e.printStackTrace();
		}
		setUrl();
	}
	
	
	/**
	 * @return the localPath
	 */
	public String getLocalPath() {
		return localPath;
	}
	/**
	 * @param localPath the localPath to set
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}


    /**
	 * @return the repo
	 */
	public Repository getRepo() {
		return repo;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * @param url the url to set
	 */
	public void setUrl() {
		this.url = repo.getConfig().getString("remote", "origin", "url");;
	}


	public Repository openJGitRepository() throws IOException {
    	File gitDir=new File(localPath+"/.git");
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder
        		.setGitDir(gitDir)
                .readEnvironment() // scan environment GIT_* variables
               // .findGitDir() // scan up the file system tree
                .build();
        return repository;
    }
	
	public List<CommitType> getCommits() throws NoHeadException, GitAPIException{
		List<CommitType> commits=new ArrayList<CommitType>();
		
		Iterable<RevCommit> logs = new Git(repo).log()
                .call();
        int count = 0;
        for (RevCommit rev : logs) {
            //System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
        	//System.out.println("Commit: " + rev.getCommitTime()+"\n"+rev.getName()+"\n"+"\n"+rev.getCommitterIdent()+"\n"+rev.getFullMessage());
        	CommitType newCommit=new CommitType();
        	newCommit.setSha1(rev.getName());
        	newCommit.setDeveloper_email(rev.getCommitterIdent().getEmailAddress());
        	newCommit.setProject_url(url);
        	String fullMsg=rev.getFullMessage();
        	String shortMsg=rev.getShortMessage();
        	int fullMsgLen=rev.getFullMessage().length();
        	if (fullMsgLen>ConfigString.MAX_COMMITMSG){
        		if (shortMsg.length()>ConfigString.MAX_COMMITMSG){
        			newCommit.setCommit_msg(shortMsg.substring(0, ConfigString.MAX_COMMITMSG));
        		}else
        			newCommit.setCommit_msg(shortMsg);
        	}else{
        		newCommit.setCommit_msg(fullMsg);
        	}
        	newCommit.setTime(rev.getCommitTime());
        	newCommit.setCommit_msglen(rev.getFullMessage().length());
        	String fix=" fix ";
        	String fixed=" fixed ";
        	if (fullMsg.toLowerCase().contains(fix.toLowerCase())||fullMsg.toLowerCase().contains(fixed.toLowerCase())){
        		newCommit.setBugFix(true);
        	}else newCommit.setBugFix(false);
        	
        	
        	
        	commits.add(newCommit);
            count++;
        }
        System.out.println("Had " + count + " commits overall on current branch\n");
        Collections.sort(commits);
        return commits;
	}
	

	
}
