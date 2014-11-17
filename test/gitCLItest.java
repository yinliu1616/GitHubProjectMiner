import java.util.regex.Pattern;

import edu.wm.cs.gitprojectminer.git.GitWrapper;
import edu.wm.cs.gitprojectminer.git.MetaDataParser;


public class gitCLItest {

	public static void main(String argv[]){
		//GitWrapper gw = new GitWrapper();
		//gw.cloneCLI("https://github.com/AltBeacon/android-beacon-library.git","./localrepo");
		String phrase="https://github.com/AltBeacon/android-beacon-library.git";
		String delims = "/";
		String[] tokens = phrase.split(delims);
		for (int i = 0; i < tokens.length; i++)
		    System.out.println(tokens[i]);
		/*
		// Test path: /Users/cvendome/apache_repos/
		String path = "./localrepo/commons-lang";
		MetaDataParser p = new MetaDataParser();
		//String res = p.generateMetaQuery(path);
		String res = p.generateMetaCountsQuery(path);
		String query = "INSERT INTO proj_stats  VALUES" + res;
//		MySQLDriver db = new MySQLDriver();
//		db.connectDB("root", "", "test");
//		db.insert(query);
//		db.closeConnection();
		System.out.println(query);
		*/
		System.out.println("DONE");
		
	}

}
