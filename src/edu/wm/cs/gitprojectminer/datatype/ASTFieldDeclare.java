package edu.wm.cs.gitprojectminer.datatype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import edu.wm.cs.gitprojectminer.codefeatures.ASTModifierHelper;
import edu.wm.cs.gitprojectminer.config.ConfigString;
import edu.wm.cs.gitprojectminer.sql.MySQLConnection;

public class ASTFieldDeclare {
	//private static final String ASTFIELDDECLARE_TABLE = "astfielddeclare";
	//private static final int MAX_INITIALIZER=255;
	private ASTTypeDeclare astTypeDeclare;

	private List<String> modifiers=new ArrayList<>();
	
	//   PrimitiveType
    //   ArrayType
    //   SimpleType 
	//
	//private ASTVariableDeclare field;
	private Type type;
	
	private String identifier;
	
	private String initializer;
	
	

	public ASTFieldDeclare(ASTTypeDeclare astTypeDeclare) {
		this.astTypeDeclare=astTypeDeclare;
	}	
		

	public void persistASTFieldDeclare(MySQLConnection db) {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = db.getConn().prepareStatement("insert into "+ ConfigString.ASTFIELDDECLARE_TABLE +"(localfile_path,project_url,commit_sha1,type,name,initializer,modifiers) values ( ?, ?, ?, ?, ?, ?, ?);");
		    preparedStatement.setString(1, astTypeDeclare.getAstRoot().getLocalfile_path());
		    preparedStatement.setString(2, astTypeDeclare.getAstRoot().getProject_url());			
		    preparedStatement.setString(3, astTypeDeclare.getAstRoot().getCommit_sha1());
		    preparedStatement.setString(4, type.toString());
		    preparedStatement.setString(5, identifier);
		    
		    if (initializer!=null && initializer.length()>ConfigString.MAX_INITIALIZER){
		    	preparedStatement.setString(6, initializer.substring(0, ConfigString.MAX_INITIALIZER));
		    }else {
		    	preparedStatement.setString(6, initializer);
		    }
		    
		    preparedStatement.setString(7, modifiers.toString());
		    //System.out.println(preparedStatement.toString());
		    preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("failed to insert ASTFieldDeclare"+type+" "+identifier+" "+initializer+ " into ASTFIELDDECLARE table");
			e1.printStackTrace();
		}		
		
		
		
	}
	
	public void parseFromFieldDeclaration(FieldDeclaration node){
		int modiNum=node.getModifiers();
		modifiers.addAll(ASTModifierHelper.getModifier(modiNum));
		//System.out.println(modifiers);	
		
		type=node.getType();
		VariableDeclarationFragment fragment=(VariableDeclarationFragment)node.fragments().get(0);
		identifier=fragment.getName().toString();
		initializer=fragment.getInitializer()==null?null:fragment.getInitializer().toString();

		
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ASTFieldDeclare [modifiers=" + modifiers + ", type=" + type
				+ ", identifier=" + identifier + ", initializer=" + initializer
				+ "]"+"\n";
	}





	/**
	 * @return the modifiers
	 */
	public List<String> getModifiers() {
		return modifiers;
	}

	/**
	 * @param modifiers the modifiers to set
	 */
	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}
	
	/**
	 * @param modifiers the modifiers to set
	 */
	public void addModifier(String modifier) {
		modifiers.add(modifier);
	}

	

}
