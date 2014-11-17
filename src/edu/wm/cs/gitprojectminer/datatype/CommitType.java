package edu.wm.cs.gitprojectminer.datatype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import edu.wm.cs.gitprojectminer.sql.MySQLConnection;

public class CommitType implements Comparable<CommitType>{
	
	private static final String COMMIT_TABLE = "commit";
	private String sha1;
	private String project_url;
	private String developer_email;
	private int time;
	private String commit_msg;
	
	
	public void persistCommit(MySQLConnection db){
		PreparedStatement preparedStatement;
		try {
			preparedStatement = db.getConn().prepareStatement("insert into "+ COMMIT_TABLE +"(sha1,project_url,developer_email,time,commit_msg) values ( ?, ?, ?, ?, ?);");
		    preparedStatement.setString(1, sha1);
		    preparedStatement.setString(2, project_url);			
		    preparedStatement.setString(3, developer_email);
		    preparedStatement.setInt(4, time);
		    preparedStatement.setString(5, commit_msg);
		   // System.out.println(preparedStatement.toString());
		    preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("failed to insert commit "+sha1+ " into COMMIT table");
			e1.printStackTrace();
		}
	}
	public int compareTo(CommitType otherCommit) {
        return (this.time-otherCommit.time);
    }
	
	/**
	 * @return the sha1
	 */
	public String getSha1() {
		return sha1;
	}
	/**
	 * @param sha1 the sha1 to set
	 */
	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}
	/**
	 * @return the project_url
	 */
	public String getProject_url() {
		return project_url;
	}
	/**
	 * @param project_url the project_url to set
	 */
	public void setProject_url(String project_url) {
		this.project_url = project_url;
	}
	/**
	 * @return the developer_email
	 */
	public String getDeveloper_email() {
		return developer_email;
	}
	/**
	 * @param developer_email the developer_email to set
	 */
	public void setDeveloper_email(String developer_email) {
		this.developer_email = developer_email;
	}
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * @return the commit_msg
	 */
	public String getCommit_msg() {
		return commit_msg;
	}
	/**
	 * @param commit_msg the commit_msg to set
	 */
	public void setCommit_msg(String commit_msg) {
		this.commit_msg = commit_msg;
	}
	

	

}
