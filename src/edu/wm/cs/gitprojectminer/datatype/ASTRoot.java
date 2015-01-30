package edu.wm.cs.gitprojectminer.datatype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.wm.cs.gitprojectminer.config.ConfigString;
import edu.wm.cs.gitprojectminer.sql.MySQLConnection;

/**
 * @author liuyin
 * Container class that holds a file's parsed AST
 */
public class ASTRoot {
	//private static final String ASTROOT_TABLE = "astroot";
	//private static final String IMPORT_TABLE = "astroot_import";
	private String localfile_path;
	private String project_url;
	private String commit_sha1;
	private String license;
	
	
	private List<String> imports=new ArrayList<String>();
	private String packageDeclare;
	private ASTTypeDeclare astTypeDeclare;

	
	public void persistASTRoot(MySQLConnection db){
		PreparedStatement preparedStatement;
		try {
			preparedStatement = db.getConn().prepareStatement("insert into "+ ConfigString.ASTROOT_TABLE +"(localfile_path,project_url,commit_sha1,license,packageDeclare,TypeDeclare_name) values ( ?, ?, ?, ?, ?, ?);");
		    preparedStatement.setString(1, localfile_path);
		    preparedStatement.setString(2, project_url);			
		    preparedStatement.setString(3, commit_sha1);
		    preparedStatement.setString(4, license);
		    preparedStatement.setString(5, packageDeclare);
		    
		    if (astTypeDeclare==null){
		    	preparedStatement.setString(6, null);
		    }else {
		    	String astTypeDeclare_name=astTypeDeclare.getIdentifier();
		    	if (astTypeDeclare_name.length()>ConfigString.MAX_TYPEDECLARENAMELEN)
		    		astTypeDeclare_name=astTypeDeclare_name.substring(0, ConfigString.MAX_TYPEDECLARENAMELEN)+"...";
		    	preparedStatement.setString(6, astTypeDeclare_name);	
		    }
		    //System.out.println(preparedStatement.toString());
		    preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("failed to insert ASTROOT"+localfile_path+ " into ASTROOT table");
			e1.printStackTrace();
		}
		
		for (String importdeclare:imports){
			PreparedStatement prepStmt;
			try {
				prepStmt = db.getConn().prepareStatement("insert into "+ ConfigString.IMPORT_TABLE +"(localfile_path,project_url,commit_sha1,importdeclare) values ( ?, ?, ?, ?);");
				prepStmt.setString(1, localfile_path);
				prepStmt.setString(2, project_url);			
				prepStmt.setString(3, commit_sha1);
				prepStmt.setString(4, importdeclare);
		    	//System.out.println(prepStmt.toString());
		    	prepStmt.executeUpdate();
			} catch (SQLException e1) {
				System.out.println("failed to insert "+importdeclare+ " into ASTROOT_IMPORT table");
				e1.printStackTrace();
			}			
			
			
		}
		if (astTypeDeclare!=null) astTypeDeclare.persistASTTypeDeclare(db);
				
		
	}


	/**
	 * @return the localfile_path
	 */
	public String getLocalfile_path() {
		return localfile_path;
	}


	/**
	 * @param localfile_path the localfile_path to set
	 */
	public void setLocalfile_path(String localfile_path) {
		this.localfile_path = localfile_path;
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
	 * @return the commit_sha1
	 */
	public String getCommit_sha1() {
		return commit_sha1;
	}


	/**
	 * @param commit_sha1 the commit_sha1 to set
	 */
	public void setCommit_sha1(String commit_sha1) {
		this.commit_sha1 = commit_sha1;
	}


	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}


	/**
	 * @param license the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}


	/**
	 * @return the imports
	 */
	public List<String> getImports() {
		return imports;
	}


	/**
	 * @param imports the imports to set
	 */
	public void setImports(List<String> imports) {
		this.imports = imports;
	}


	/**
	 * @return the packageDeclare
	 */
	public String getPackageDeclare() {
		return packageDeclare;
	}


	/**
	 * @param packageDeclare the packageDeclare to set
	 */
	public void setPackageDeclare(String packageDeclare) {
		this.packageDeclare = packageDeclare;
	}


	/**
	 * @return the astTypeDeclare
	 */
	public ASTTypeDeclare getAstTypeDeclare() {
		return astTypeDeclare;
	}


	/**
	 * @param astTypeDeclare the astTypeDeclare to set
	 */
	public void setAstTypeDeclare(ASTTypeDeclare astTypeDeclare) {
		this.astTypeDeclare = astTypeDeclare;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ASTRoot [localfile_path=" + localfile_path + ", project_url="
				+ project_url + ", commit_sha1=" + commit_sha1 + ", license="
				+ license + ", imports=" + imports + ", packageDeclare="
				+ packageDeclare + ", astTypeDeclare=" + astTypeDeclare + "]";
	}
	




	
	
	
	
	
}
