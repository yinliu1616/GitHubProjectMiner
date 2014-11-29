package edu.wm.cs.gitprojectminer.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import edu.wm.cs.gitprojectminer.datatype.ProjectType;



public class GitWrapper {

	private static String gitPath;
	
	// Set path upon creation
	public GitWrapper() {
		this.setPath();
	}
	
	// Configure git path variable 
	private void setPath(){
		try {
			Runtime shell = Runtime.getRuntime();
			Process proc = shell.exec("which git");
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			gitPath = stdIn.readLine();
		}
		catch (Exception e){
			System.out.println("Failed to exec 'which git':\n");
			e.printStackTrace();
		}
	}
	
	/*
	 * Extract metadata and return it in a string array
	 * Fields are comma separated
	 * 
	 * git show format parameters:
	 * %[a,c]n: [author,contributor] name
	 * %[a,c]e: [author,contributor] email
	 * %[a,c]i: [author,contributor] date in ISO 8601 format
	 * %[a,c]t: [author,contributor] date in UNIX timestamp
	 * %h: abbreviated commit hash
	 * %H: full commit hash
	 * %b: commit body
	 * %s: commit subject
	 * %f: sanitized subject
	 */
	
	protected String[] getMetaData(String path) {
		String result="";
		String tmp;
		try{
			File dir = new File(path);
			Runtime shell = Runtime.getRuntime();
			Process proc = shell.exec(gitPath + " --no-pager log --date=short --format="
					+ "\"%H\",\"%an\",\"%ae\",\"%ad\",%ct,\"%f\"",null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((tmp = stdIn.readLine()) != null){
				result += tmp + "-EOL-";
			}
		}
		catch (IOException e){
			System.out.println("Failed Running or Reading Results from 'git log':\n");
			e.printStackTrace();
		}
		
		return result.split("-EOL-");
	}
	
	/*
	 * Extract metadata counts and return it in a string array
	 * Fields are comma separated
	 * 
	 * Fields: number of developers, number of commits, number of 
	 */
	protected String[] aggregateMetaCounts(String path){

		return null;
	}
	
	/*
	 * Extracts the number of developers for a project
	 */	
	protected int getDeveloperCount(String path){
		HashMap<String, Integer> devs = new HashMap<String, Integer>();
		String line;
		int value;
		try{
			File dir = new File(path);
			Runtime shell = Runtime.getRuntime();
			Process proc = shell.exec(gitPath + " --no-pager log --date=short --format="
					+ "\"%an\"",null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = stdIn.readLine()) != null){
				// Commented code for developer contributions
//				if( devs.containsKey(line) ){
//					value = devs.get(line) + 1;
//					devs.put(line, value); 
//				}
//				else{
					devs.put(line, 1);
//				}
			}
		}
		catch (IOException e){
			System.out.println("Failed Running or Reading Results from 'git log':\n");
			e.printStackTrace();
		}
		
		return devs.size();
	}
	
	/*
	 * Extracts the number of commits for a project
	 */	
	protected int getCommitCount(String path){
		String line;
		int count = 0;
		try{
			File dir = new File(path);
			Runtime shell = Runtime.getRuntime();
			Process proc = shell.exec(gitPath + " --no-pager log --date=short --format="
					+ "\"%H\"",null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			// Each line is a unique commit hash
			while ((line = stdIn.readLine()) != null){
				count++;
			}
		}
		catch (IOException e){
			System.out.println("Failed Running or Reading Results from 'git log':\n");
			e.printStackTrace();
		}

		return count;
	} 
	
	public String getURL(String path){
		String result="";
		try{
			File dir = new File(path);
			Runtime shell = Runtime.getRuntime();
			Process proc = shell.exec(gitPath + " config --get remote.origin.url",null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			result = stdIn.readLine();
		}
		catch (IOException e) {
			System.out.println("Failed Running or Reading Results for git url:\n");
			e.printStackTrace();
		}	
		return result;		
	} 
	
	public String getGit(){
		return gitPath;
	}
	
	public void cloneCLI(String url,String path){

			File dir = new File(path);
			Runtime shell = Runtime.getRuntime();
			
			try {
				Process proc = shell.exec(gitPath + " clone "+url,null,dir);
				System.out.println("Waiting for cloning ...");
				try {
					
					proc.waitFor();
				} catch (InterruptedException e) {
					System.err.println("Failed to finish cloning");
					e.printStackTrace();
				}
				System.out.println("Cloning Done.");			
				
				
			} catch (IOException e) {
				System.err.println("Failed to call git clone CLI");
				e.printStackTrace();
			}

	}
	
	public String backOneRevisionCLI(String path){
		String result="";
		File dir = new File(path);
		Runtime shell = Runtime.getRuntime();		
		try {
			Process proc = shell.exec(gitPath + " reset --hard HEAD^ ",null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			result = stdIn.readLine();
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				System.err.println("Failed to finish reset head");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.err.println("Failed to call git reset --hard HEAD^");
			e.printStackTrace();
		}
		System.out.println("set revision to previous one");
		return result;	
		
	}
	public String setRevisionCLI(String path,String commit_id){
		String result="";
		File dir = new File(path);
		Runtime shell = Runtime.getRuntime();		
		try {
			Process proc = shell.exec(gitPath + " reset --hard "+commit_id,null,dir);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			result = stdIn.readLine();
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				System.err.println("Failed to finish reset head");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.err.println("Failed to call git reset --hard HEAD^");
			e.printStackTrace();
		}
		System.out.println("set revision to commit_id="+commit_id);		
		return result;	
		
		
		
		
		
	}
	
}