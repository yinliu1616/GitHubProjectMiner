package edu.wm.cs.gitprojectminer.codefeatures;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.wm.cs.gitprojectminer.datatype.ASTRoot;

public class CodeFeatureExtractorTest {

	private static String localrepo="localrepo";
	public static void main(String[] args) {
		/*
		try {
			GitMining.ParseFilesInDir();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		File dirs = new File(".");
		String startingDir=dirs.toString();
			try {
				//startingDir = dirs.getCanonicalPath() +File.separator+"localrepo"+File.separator+"commons-lang";
				startingDir = dirs.getCanonicalPath() +File.separator+"localrepo";
			} catch (IOException e1) {
				System.out.println("failed to get starting dir");
				e1.printStackTrace();
			}

        //find all the java source file
        String pattern = "*.java";
        String[] findCmd=new String[3];
        //startingDir
        findCmd[0]=startingDir;
        findCmd[1]="-name";
        findCmd[2]=pattern;
        List<String> resultMatches;
        List<ASTRoot> resultASTRoots;
		try {
			resultMatches = FindInputFiles.find(findCmd);
			if (resultMatches.size()==0) System.out.println("empty results match!");
			else {
				resultASTRoots=CodeFeatureExtractor.ParseFiles(resultMatches);
				for (ASTRoot astRoot : resultASTRoots){
					
					
					System.out.println(astRoot.toString());
					
					//System.out.println(astRoot.imports);
					
				}
				//System.out.println(resultASTRoots);
			}
			
			/*
	        for (String filePath : resultMatches){
				System.out.println(filePath);
				GitMining.parseJavaFile(filePath);
			}
			*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		
		
	} 


}
