package edu.wm.cs.gitprojectminer.codefeatures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wm.cs.gitprojectminer.datatype.ASTRoot;
import edu.wm.cs.gitprojectminer.sql.MySQLConnection;

public class CodeFeatureExtractor {
	/**parse a single java source file
	 *read file content into string, call ast parser to extract code feature
	 *call license extractor to extract license specified in the top comments of a source file
	 * 
	 * @param filePath
	 * @return  an ast root containing all ast nodes and license info in the input source file
	 */
	public static ASTRoot parseJavaFile(String filePath) {
		
		try {
			ASTRoot astRoot=ASTFeatureParser.parse(readFileToString(filePath));
			astRoot.setLocalfile_path(filePath);
			astRoot.setLicense(SrcFileLicenseExtractor.LicenseExtractor(filePath));
			return astRoot;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//parse a list of java source file
	//when there are a large number of 
	//if get all the astroots, GC overhead limit exceeded, heap overflow!!! need to create one and persist one!!!
	/*
	public static List<ASTRoot> ParseFiles(List<String> filePaths){
		List<ASTRoot> astRoots=new ArrayList<ASTRoot>();
		for (String filePath : filePaths){
			//System.out.println(filePath);
			try {

				ASTRoot astRoot=ASTFeatureParser.parse(readFileToString(filePath));
				astRoot.setLocalfile_path(filePath);
				astRoot.setLicense(SrcFileLicenseExtractor.LicenseExtractor(filePath));
				astRoots.add(astRoot);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return astRoots;
		
		
	}
	*/
	
	
	//parse a list of java source file
	public static void ParseFilesNPersist(List<String> filePaths,String sha1,String url,MySQLConnection db){
		for (String filePath : filePaths){
			//System.out.println(filePath);
			try {

				ASTRoot astRoot=ASTFeatureParser.parse(readFileToString(filePath));
				astRoot.setLocalfile_path(filePath);
				astRoot.setProject_url(url);
				astRoot.setCommit_sha1(sha1);
				
				astRoot.setLicense(SrcFileLicenseExtractor.LicenseExtractor(filePath));
				astRoot.persistASTRoot(db);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}

	//read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		return  fileData.toString();	
	}

}
