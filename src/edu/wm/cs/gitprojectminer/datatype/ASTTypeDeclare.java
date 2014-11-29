package edu.wm.cs.gitprojectminer.datatype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import edu.wm.cs.gitprojectminer.codefeatures.ASTModifierHelper;
import edu.wm.cs.gitprojectminer.config.ConfigString;
import edu.wm.cs.gitprojectminer.sql.MySQLConnection;

public class ASTTypeDeclare {
	
	//private static final String ASTTYPEDECLARE_TABLE = "asttypedeclare";	
	//private static final String NESTTYPE_TABLE = "asttypedeclare_nestedtype";	


	//private String project_url;
	
	//  list of type parameters of this type declaration List :  List	typeParameters() 
	
	//Get Super Class :    Type	getSuperclassType() 
	//list of superinterfaces of this type declaration   :   List	superInterfaceTypes() 
	// list of member type declarations of this type declaration  :  TypeDeclaration[]	getTypes() 
	

	private ASTRoot astRoot;
//	private String project_url;
//	private String commit_sha1;
	
	
	
	private List<String> modifiers=new ArrayList<>();
	private String identifier;
	//super class followed by extends
	private Type superClass;
	//super interfaces followed by implements
	private List<Type> superInterfaces=new ArrayList<>();
	
	
	private List<ASTFieldDeclare> fields=new ArrayList<>();
	private List<ASTMethodDeclare> methods=new ArrayList<>();
	
	//nested class declaration
	private List<ASTTypeDeclare> nestedTypeDeclares=new ArrayList<>();
	
	//outer class
	private ASTTypeDeclare outerTypeDeclare;
	
	
	public ASTTypeDeclare(ASTRoot astRoot) {
		this.astRoot=astRoot;
		
	}



	public void persistASTTypeDeclare(MySQLConnection db){

		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = db.getConn().prepareStatement("insert into "+ ConfigString.ASTTYPEDECLARE_TABLE +"(localfile_path,project_url,commit_sha1,typedeclare_name,modifiers,superclass) values ( ?, ?, ?, ?, ?, ?);");
		    preparedStatement.setString(1, astRoot.getLocalfile_path());
		    preparedStatement.setString(2, astRoot.getProject_url());			
		    preparedStatement.setString(3, astRoot.getCommit_sha1());
		    preparedStatement.setString(4, identifier);
		    preparedStatement.setString(5, modifiers.toString());
		    preparedStatement.setString(6, superClass==null?null:superClass.toString());
		    
		    //System.out.println(preparedStatement.toString());
		    preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("failed to insert astTypeDeclare "+identifier+ " into ASTTYPEDECLARE table");
			e1.printStackTrace();
		}	
		
		for (ASTTypeDeclare innerType:nestedTypeDeclares){
			PreparedStatement nestTypeStmt;
			try {
				nestTypeStmt = db.getConn().prepareStatement("insert into "+  ConfigString.NESTTYPE_TABLE +"(localfile_path,project_url,commit_sha1,outertype_name,innertype_name) values ( ?, ?, ?, ?, ?);");
				nestTypeStmt.setString(1, astRoot.getLocalfile_path());
				nestTypeStmt.setString(2, astRoot.getProject_url());			
				nestTypeStmt.setString(3, astRoot.getCommit_sha1());
				nestTypeStmt.setString(4, identifier);
				nestTypeStmt.setString(5, innerType.getIdentifier());

		    
				//System.out.println(nestTypeStmt.toString());
				nestTypeStmt.executeUpdate();
			} catch (SQLException e1) {
				System.out.println("failed to insert nested ASTTypeDelcare "+identifier+" "+innerType.getIdentifier()+ " into ASTTYPEDECLARE_NESTEDTYPE table");
				e1.printStackTrace();
			}	
			
			
		}		
		
		for (ASTFieldDeclare fieldDeclare:fields){
			fieldDeclare.persistASTFieldDeclare(db);	
		}
		
		for (ASTMethodDeclare methodDeclare:methods){
			methodDeclare.persistASTMethodDeclare(db);
		}
			
		
		
		
	}
	
	
	
	public void parseFromTypeDeclaration(TypeDeclaration node){
		
		int modiNum=node.getModifiers();
		modifiers.addAll(ASTModifierHelper.getModifier(modiNum));

		
		identifier=node.getName().toString();

		
		superClass=node.getSuperclassType();

		
		superInterfaces=node.superInterfaceTypes();
		
		
		TypeDeclaration[] typeDeclaration=node.getTypes();
		for(int i=0;i<typeDeclaration.length;i++){
			ASTTypeDeclare newASTTypeDeclare=new ASTTypeDeclare(astRoot);
			newASTTypeDeclare.parseFromTypeDeclaration(typeDeclaration[i]);
			nestedTypeDeclares.add(newASTTypeDeclare);

			
		}

		
		
		FieldDeclaration[]	fieldDeclarations=node.getFields();
		for(int i=0;i<fieldDeclarations.length;i++){
			ASTFieldDeclare newASTFieldDeclare=new ASTFieldDeclare(this);
			newASTFieldDeclare.parseFromFieldDeclaration(fieldDeclarations[i]);
			fields.add(newASTFieldDeclare);

			
		}
		
		MethodDeclaration[]	methodDeclarations=node.getMethods();
		for(int i=0;i<methodDeclarations.length;i++){
			ASTMethodDeclare newASTMethodDeclare=new ASTMethodDeclare(this);
			newASTMethodDeclare.parseFromMethodDeclaration(methodDeclarations[i]);
			methods.add(newASTMethodDeclare);

		}		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ASTTypeDeclare :" + "\n"
				+"modifiers=" + modifiers + "\n"
				+ " identifier="+ identifier + "\n"
				+ " superClass=" + superClass + "\n"
				+ " superInterfaces=" + superInterfaces + "\n"
				+ " fields=" + fields + "\n"
				+ " methods=" + methods + "\n";
	}











	/**
	 * @return the astRoot
	 */
	public ASTRoot getAstRoot() {
		return astRoot;
	}

	/**
	 * @param astRoot the astRoot to set
	 */
	public void setAstRoot(ASTRoot astRoot) {
		this.astRoot = astRoot;
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
	 * @return the superClass
	 */
	public Type getSuperClass() {
		return superClass;
	}

	/**
	 * @param superClass the superClass to set
	 */
	public void setSuperClass(Type superClass) {
		this.superClass = superClass;
	}

	/**
	 * @return the outerTypeDeclare
	 */
	public ASTTypeDeclare getOuterTypeDeclare() {
		return outerTypeDeclare;
	}

	/**
	 * @param outerTypeDeclare the outerTypeDeclare to set
	 */
	public void setOuterTypeDeclare(ASTTypeDeclare outerTypeDeclare) {
		this.outerTypeDeclare = outerTypeDeclare;
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
	 * @return the superInterfaces
	 */
	public List<Type> getSuperInterfaces() {
		return superInterfaces;
	}

	/**
	 * @param superInterfaces the superInterfaces to set
	 */
	public void setSuperInterfaces(List<Type> superInterfaces) {
		this.superInterfaces = superInterfaces;
	}

	/**
	 * @return the fields
	 */
	public List<ASTFieldDeclare> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<ASTFieldDeclare> fields) {
		this.fields = fields;
	}

	/**
	 * @return the methods
	 */
	public List<ASTMethodDeclare> getMethods() {
		return methods;
	}

	/**
	 * @param methods the methods to set
	 */
	public void setMethods(List<ASTMethodDeclare> methods) {
		this.methods = methods;
	}

	/**
	 * @return the nestedTypeDeclares
	 */
	public List<ASTTypeDeclare> getNestedTypeDeclares() {
		return nestedTypeDeclares;
	}

	/**
	 * @param nestedTypeDeclares the nestedTypeDeclares to set
	 */
	public void setNestedTypeDeclares(List<ASTTypeDeclare> nestedTypeDeclares) {
		this.nestedTypeDeclares = nestedTypeDeclares;
	}
	
	
	/**
	 * @param modifier the modifier to add
	 */
	public void addModifier(String modifier) {
		modifiers.add(modifier);;
	}


	/**
	 * @param superInterface the superInterface to add
	 */
	public void addSuperInterface(Type superInterface) {
		superInterfaces.add(superInterface);
	}


	/**
	 * @param field the field to add
	 */
	public void addField(ASTFieldDeclare field) {
		fields.add(field);
	}


	/**
	 * @param methods the methods to set
	 */
	public void addMethod(ASTMethodDeclare method) {
		methods.add(method);
	}

	/**
	 * @param nestedTypeDeclare the nestedTypeDeclare to set
	 */
	public void addNestedTypeDeclare(ASTTypeDeclare nestedTypeDeclare) {
		nestedTypeDeclares.add(nestedTypeDeclare);
	}
	
	

}
