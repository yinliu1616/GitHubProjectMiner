package edu.wm.cs.gitprojectminer.datatype;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Type;

import edu.wm.cs.gitprojectminer.codefeatures.ASTModifierHelper;

public class ASTVariableDeclare {
	private Type type;
	
	private String identifier;
	
	private String initializer;
	
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  type +" ";
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
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
	 * @return the initializer
	 */
	public String getInitializer() {
		return initializer;
	}

	/**
	 * @param initializer the initializer to set
	 */
	public void setInitializer(String initializer) {
		this.initializer = initializer;
	}

	

}
