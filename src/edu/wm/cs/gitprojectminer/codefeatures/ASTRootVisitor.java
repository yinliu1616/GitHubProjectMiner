package edu.wm.cs.gitprojectminer.codefeatures;

import edu.wm.cs.gitprojectminer.datatype.ASTRoot;
import org.eclipse.jdt.core.dom.ASTVisitor;

public class ASTRootVisitor extends ASTVisitor {
	ASTRoot astRoot;
	

	public ASTRootVisitor(ASTRoot astRootNode) {
		super();
		astRoot=astRootNode;
		// TODO Auto-generated constructor stub
	}


	public ASTRootVisitor(boolean visitDocTags) {
		super(visitDocTags);
		// TODO Auto-generated constructor stub
	}

}
