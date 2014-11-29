package edu.wm.cs.gitprojectminer.jgitwrapper;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import edu.wm.cs.gitprojectminer.config.ConfigString;


public class GitClone {

	 //private static final String LOCAL_REPO_DIR = "./localrepo/";
	 
	 public static void Clone(String url,String path) throws IOException, InvalidRemoteException, TransportException, GitAPIException{
	        // prepare a new folder for the cloned repository
	    	File localDir = new File(ConfigString.LOCAL_REPO_DIR+"/"+path);
	    	localDir.mkdir();
	  

	        // then clone
	        System.out.println("Cloning from " + url + " to " + localDir);
	        Git result = Git.cloneRepository()
	                .setURI(url)
	                .setDirectory(localDir)
	                .call();

	        try {
		        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
		        System.out.println("Having repository: " + result.getRepository().getDirectory());
	        } finally {
	        	result.close();
	        }
	}

}
