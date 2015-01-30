package edu.wm.cs.gitprojectminer.datatype;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;

import edu.wm.cs.gitprojectminer.codefeatures.CodeFeatureExtractor;
import edu.wm.cs.gitprojectminer.codefeatures.FindInputFiles;
import edu.wm.cs.gitprojectminer.codefeatures.SeperateLicenseFileExtractor;
import edu.wm.cs.gitprojectminer.config.ConfigString;
import edu.wm.cs.gitprojectminer.git.GitWrapper;
import edu.wm.cs.gitprojectminer.jgitwrapper.GitRepoMetaData;
import edu.wm.cs.gitprojectminer.sql.MySQLConnection;

public class ProjectType {
	//private static final String LOCAL_REPO_DIR = "./localrepo";
	//private static final String PROJECT_TABLE = "project";
	//private String id;
	private String name;
	private String url;
	private String localPath;
	//private String description;
	
	private int create_time;
	//private List<String> developers;
	//private int num_developers;
	private List<CommitType> commits;
	private int num_commits=0;
	private List<String> licenses=new ArrayList<String>();
	
	//private List<String> languages;
	//private Map<String,List<ASTRoot>> astRootMap=new HashMap<>();
	
	
	public ProjectType(String url, String localname){
		if (url!=null){
			setUrl(url);
			parseNameFromUrl(url);	
			//TODO: Check if Project locally exists, 
		
			//if not, git clone it from GitHub, extract meta data and code features store them into database
			GitWrapper gw = new GitWrapper();
			gw.cloneCLI(url,ConfigString.LOCAL_REPO_DIR);			
			
			
			
		}else{
			setName(localname);
			GitWrapper gw = new GitWrapper();
			setUrl(gw.getURL(ConfigString.LOCAL_REPO_DIR+"/"+localname));
		}
		
		setLocalPath(ConfigString.LOCAL_REPO_DIR+"/"+this.name);
		extractProjectLicense();
		extractCommits();
		num_commits=commits.size();
		//if exist, query directly from database.
		
		
		
		//extractASTFeatures();
	
	}
	


	/**
	 * @param url the url to set
	 */
	private void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param name the name to set
	 * Only used internally, but parse url string get project name
	 */
	private void parseNameFromUrl(String url) {
		String delims = "/";
		String[] tokens = url.split(delims);
		String projectName=tokens[tokens.length-1];
		this.name = projectName.substring(0, projectName.length()-4);
	}	
	
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @param localPath the localPath to set
	 */
	private void setLocalPath(String localPath) {
		this.localPath = localPath;
	}	
	
	
	private void extractProjectLicense(){
		String pattern = "LICENSE";
        String[] findCmd=new String[3];
        findCmd[0]=localPath;
        findCmd[1]="-name";
        findCmd[2]=pattern;
        List<String> resultMatches;
		try {
			resultMatches = FindInputFiles.find(findCmd);
			if (resultMatches.size()==0) System.out.println("empty results match LICENSE file!");
			else {
				//System.out.println("found "+ resultMatches.size()+ " LICENSE file!");
				for (String file:resultMatches){
					licenses.add(SeperateLicenseFileExtractor.LicenseExtractor(file));
				}


			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
		
	}
	
	
	
	
	private void extractCommits(){
		GitRepoMetaData metaParser=new GitRepoMetaData(localPath);
		try {
			this.commits=metaParser.getCommits();
			setCreate_time(commits.get(0).getTime());
			/*
			for (CommitType commit:commits){
				System.out.println("\n"+"Commit: " + commit.getSha1()+"\n"+"\n"+commit.getDeveloper_email()+"\n"+commit.getTime()+"\n"+commit.getCommit_msg()+"\n"+commit.getProject_url());
			
			}
			System.out.println("Had " + commits.size() + " commits overall on current branch");
			*/
			
		} catch (GitAPIException e) {
			System.out.println("failed to get all the commits for project "+ url);
			e.printStackTrace();
		}
	
	}
	//Extract ast code features
	//First find all *.java in project local path
	//For each java source file, parse it to AstRoot instance
	//if get all the astroots, GC overhead limit exceeded, heap overflow!!! need to create one and persist one!!!
	private void extractASTFeaturesNPersist(String sha1,MySQLConnection db){
        String pattern = "*.java";
        String[] findCmd=new String[3];
        findCmd[0]=localPath;
        findCmd[1]="-name";
        findCmd[2]=pattern;
        List<String> resultMatches;
		try {
			resultMatches = FindInputFiles.find(findCmd);
			if (resultMatches.size()==0) System.out.println("empty results match *.java file!");
			else {
				//System.out.println("found "+ resultMatches.size()+ " *.java file!");
				CodeFeatureExtractor.ParseFilesNPersist(resultMatches, sha1, url, db);

			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		//Extract ast code features for commit sha1
		//For each java source file, parse it to AstRoot instance
		private void extractASTFeaturesForCommit(String sha1,MySQLConnection db){
			
			GitWrapper gw = new GitWrapper();
			gw.setRevisionCLI(localPath, sha1);
			extractASTFeaturesNPersist(sha1,db);
			
		}
	
	
	
	
	public void persistProject() {
		MySQLConnection db=new MySQLConnection();
		
		db.openConnection();
		
	    // preparedStatements can use variables and are more efficient
		PreparedStatement preparedStatement;
		try {
			preparedStatement = db.getConn().prepareStatement("insert into "+ ConfigString.PROJECT_TABLE +"(name,url,localpath,create_time,num_commits,licenses) values ( ?, ?, ?, ?, ?, ?);");
		    preparedStatement.setString(1, name);
		    preparedStatement.setString(2, url);			
		    preparedStatement.setString(3, localPath);
		    preparedStatement.setInt(4, create_time);
		    preparedStatement.setInt(5, num_commits);
		    preparedStatement.setString(6, licenses.toString());
		    //System.out.println(preparedStatement.toString());
		    preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("failed to insert project "+url+ " into PROJECT table");
			e1.printStackTrace();
		}
		for (CommitType commit:commits){
			commit.persistCommit(db);
			
			extractASTFeaturesForCommit(commit.getSha1(), db);
			
			/*
			List<ASTRoot> astRoots=extractASTFeaturesForCommit(commit.getSha1());
			for (ASTRoot astRoot : astRoots){					
				astRoot.setProject_url(url);
				astRoot.setCommit_sha1(commit.getSha1());
				astRoot.persistASTRoot(db);		
			}
			*/
		}
		GitWrapper gw = new GitWrapper();
		gw.setRevisionCLI(localPath, commits.get(commits.size()-1).getSha1());
		//only extract the most updated revision
		//extractASTFeaturesNPersist(commits.get(commits.size()-1).getSha1(), db);
		
		
		
		
		//if get all the astroots, GC overhead limit exceeded, heap overflow!!! need to create one and persist one!!!
		/*
		for (ASTRoot astRoot : astRoots){					
			astRoot.setProject_url(url);
			astRoot.setCommit_sha1(commits.get(commits.size()-1).getSha1());
			astRoot.persistASTRoot(db);		
		}
		*/
		
		db.closeConnection();
		
	}
	
	

	/**
	 * @return the create_time
	 */
	public int getCreate_time() {
		return create_time;
	}



	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}



	/**
	 * @return the num_commits
	 */
	public int getNum_commits() {
		return num_commits;
	}



	/**
	 * @param num_commits the num_commits to set
	 */
	public void setNum_commits(int num_commits) {
		this.num_commits = num_commits;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the localPath
	 */
	public String getLocalPath() {
		return localPath;
	}





	
	
	

}
