import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

public class JGitCloneTest {

	private static final String REMOTE_URL = "https://github.com/centic9/jgit-cookbook.git";

    public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        // prepare a new folder for the cloned repository
    	String path = "./localrepo/jgit-cookbook";
    	File localDir = new File(path);
    	localDir.mkdir();
  
        //File localPath = File.createTempFile("TestGitRepository", "");
        //localPath.delete();
    	//File localDir = new File(path);

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localDir);
        Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localDir)
                .call();

        try {
	        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
	        System.out.println("Having repository: " + result.getRepository().getDirectory());
        } finally {
        	result.close();
        }
    }

}
