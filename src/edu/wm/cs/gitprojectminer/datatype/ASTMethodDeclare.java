package edu.wm.cs.gitprojectminer.datatype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import edu.wm.cs.gitprojectminer.codefeatures.ASTModifierHelper;
import edu.wm.cs.gitprojectminer.config.ConfigString;
import edu.wm.cs.gitprojectminer.sql.MySQLConnection;

public class ASTMethodDeclare {


	//private static final String ASTMETHODDECLARE_TABLE = "astmethoddeclare";	

	private ASTTypeDeclare astTypeDeclare;
	private List<String> modifiers=new ArrayList<>();
	
	private boolean isConstructor;
	
	private Type return_type;
	
	private String identifier;
	
	private List<ASTVariableDeclare> parameters=new ArrayList<>();
	
	public ASTMethodDeclare(ASTTypeDeclare astTypeDeclare) {
		this.astTypeDeclare=astTypeDeclare;
	}
	
	
	
	
	
	public void persistASTMethodDeclare(MySQLConnection db) {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = db.getConn().prepareStatement("insert into "+ ConfigString.ASTMETHODDECLARE_TABLE +"(localfile_path,project_url,commit_sha1,isConstructor,return_type,name,parameters,modifiers) values ( ?, ?, ?, ?, ?, ?, ?, ?);");
		    preparedStatement.setString(1, astTypeDeclare.getAstRoot().getLocalfile_path());
		    preparedStatement.setString(2, astTypeDeclare.getAstRoot().getProject_url());			
		    preparedStatement.setString(3, astTypeDeclare.getAstRoot().getCommit_sha1());

		    
		    preparedStatement.setBoolean(4, isConstructor);
		    preparedStatement.setString(5, return_type==null?null:return_type.toString());
		    preparedStatement.setString(6, identifier);
		    String params=parameters.toString();
		    if (params.length()>ConfigString.MAX_PARAMSLEN){
		    	params=params.substring(0, ConfigString.MAX_PARAMSLEN);
		    	params=params+"...";
		    }
		    
		    preparedStatement.setString(7, params);
		    preparedStatement.setString(8, modifiers.toString());
		    
		    
		    //System.out.println(preparedStatement.toString());
		    preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("failed to insert ASTMethodDeclare "+identifier+ " into identifier table");
			System.out.println("parameters: "+parameters.toString());
			e1.printStackTrace();
		}
			
		
		
		
	}
	
	
	
	public void parseFromMethodDeclaration(MethodDeclaration node){
		int modiNum=node.getModifiers();
		modifiers.addAll(ASTModifierHelper.getModifier(modiNum));
		//System.out.println(modifiers);		
		
		isConstructor=node.isConstructor();
		
		return_type=node.getReturnType2();
		
		identifier=node.getName().toString();	
		
		List<SingleVariableDeclaration> varParameters=node.parameters();
		for (int i=0;i<varParameters.size();i++){
			SingleVariableDeclaration varParameter=varParameters.get(i);
			ASTVariableDeclare newParameter=new ASTVariableDeclare();
			newParameter.setType(varParameter.getType());
			newParameter.setIdentifier(varParameter.getName().toString());
			parameters.add(newParameter);
		}
		
		
		
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ASTMethodDeclare [modifiers=" + modifiers + ", isConstructor="
				+ isConstructor + ", return_type=" + return_type
				+ ", identifier=" + identifier + ", parameters=" + parameters
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
	 * @return the isConstructor
	 */
	public boolean isConstructor() {
		return isConstructor;
	}

	/**
	 * @param isConstructor the isConstructor to set
	 */
	public void setConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	/**
	 * @return the return_type
	 */
	public Type getReturn_type() {
		return return_type;
	}

	/**
	 * @param return_type the return_type to set
	 */
	public void setReturn_type(Type return_type) {
		this.return_type = return_type;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the parameters
	 */
	public List<ASTVariableDeclare> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<ASTVariableDeclare> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * @param modifiers the modifiers to set
	 */
	public void addModifier(String modifier) {
		modifiers.add(modifier);
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void addParameters(ASTVariableDeclare parameter) {
		parameters.add(parameter);
	}	
	/**
	 * @param exceptions the exceptions to set
	 */

}
