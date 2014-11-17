package edu.wm.cs.gitprojectminer.git;


public class MetaDataParser {
	
	/* 
	 * Generate MetaData query
	 * MUST take absolute path to git repository 
	 */
	public String generateMetaQuery(String path) {
		String query = "";
		GitWrapper gw = new GitWrapper();
		String url = gw.getURL(path);
		
		// projectInfo[0]: owner, projectInfo[1]: project name 
		String[] projectInfo = url.substring(15, url.length()-4).split("/");
		String[] rawMeta = gw.getMetaData(path);
		int size = rawMeta.length;
		for(int i = 0; i < size; i++ ){
			query += "( \"" + projectInfo[1] + "\",\"" + projectInfo[0] + "\","
					+ rawMeta[i] + ",\"" + url+ "\"),";
		}
		query = query.substring(0,query.length()-1);
		return query;
	} 
	
	/* 
	 * Generate MetaData statistics query
	 * MUST take absolute path to git repository 
	 */
	public String generateMetaCountsQuery(String path){
		String query = "";
		GitWrapper gw = new GitWrapper();
		String url = gw.getURL(path);
		String[] projectInfo = url.substring(15, url.length()-4).split("/");
		int devCount = gw.getDeveloperCount(path); 
		int commitCount =  gw.getCommitCount(path);
		query += "( \"" + projectInfo[1] + "\"," + devCount + "," + commitCount + ",\"" + url +"\"),";
		return query;
	}
	
}