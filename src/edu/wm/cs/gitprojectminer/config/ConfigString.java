package edu.wm.cs.gitprojectminer.config;

public class ConfigString {

	//public static final String LOCAL_REPO_DIR = "../localrepo";
	//remote host
	public static final String LOCAL_REPO_DIR = "/data2";

	
	// JDBC driver name and database URL
	public  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	//public  static final String DB_URL = "jdbc:mysql://localhost/finalTest";
	//important data
	//public  static final String DB_URL = "jdbc:mysql://localhost/test_metadata";
	//public  static final String DB_URL = "jdbc:mysql://localhost/test_revisions";
	public  static final String DB_URL = "jdbc:mysql://localhost/gitminer";
	//  Database credentials
	//public  static final String USER = "root";
	public  static final String USER = "liu";

	//public  static final String PASS = "521616";
	public  static final String PASS = "t3mpP@ss";
	
	public static String ninka="./ninka/ninka.pl";
	public static String ninkalicense="./ninka/ninkaLICENSE.pl";

	
	public static final String PROJECT_TABLE = "project";
	
	public static final String COMMIT_TABLE = "commit";
	public static final int MAX_COMMITMSG=255;
	
	
	public static final String ASTROOT_TABLE = "astroot";
	public static final String IMPORT_TABLE = "astroot_import";
	
	
	public static final String ASTTYPEDECLARE_TABLE = "asttypedeclare";	
	public static final String NESTTYPE_TABLE = "asttypedeclare_nestedtype";
	
	public static final String ASTMETHODDECLARE_TABLE = "astmethoddeclare";
	
	
	public static final String ASTFIELDDECLARE_TABLE = "astfielddeclare";
	public static final int MAX_INITIALIZER=255;
	public static final int MAX_PARAMSLEN=250;
	public static final int MAX_TYPEDECLARENAMELEN=250;

}
