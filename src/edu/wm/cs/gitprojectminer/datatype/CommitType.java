package edu.wm.cs.gitprojectminer.datatype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import edu.wm.cs.gitprojectminer.config.ConfigString;
import edu.wm.cs.gitprojectminer.sql.MySQLConnection;

public class CommitType implements Comparable<CommitType>{
	
	//private static final String COMMIT_TABLE = "commit";
	private String sha1;
	private String project_url;
	private String developer_email;
	private int time;
	private String commit_msg;
	private int commit_msglen;
	private boolean isBugFix;
	
	
	public void persistCommit(MySQLConnection db){
		PreparedStatement preparedStatement;
		try {
			preparedStatement = db.getConn().prepareStatement("insert into "+ ConfigString.COMMIT_TABLE +"(sha1,project_url,developer_email,time,commit_msg,commit_msglen,isBugFix) values ( ?, ?, ?, ?, ?, ?, ?);");
		    preparedStatement.setString(1, sha1);
		    preparedStatement.setString(2, project_url);			
		    preparedStatement.setString(3, developer_email);
		    preparedStatement.setInt(4, time);
		    preparedStatement.setString(5, commit_msg);
		    preparedStatement.setInt(6, commit_msglen);
		    preparedStatement.setBoolean(7, isBugFix);
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
	/**
	 * @return the commit_msglen
	 */
	public int getCommit_msglen() {
		return commit_msglen;
	}
	/**
	 * @param commit_msglen the commit_msglen to set
	 */
	public void setCommit_msglen(int commit_msglen) {
		this.commit_msglen = commit_msglen;
	}
	/**
	 * @return the isBugFix
	 */
	public boolean isBugFix() {
		return isBugFix;
	}
	/**
	 * @param isBugFix the isBugFix to set
	 */
	public void setBugFix(boolean isBugFix) {
		this.isBugFix = isBugFix;
	}
	

	

}
