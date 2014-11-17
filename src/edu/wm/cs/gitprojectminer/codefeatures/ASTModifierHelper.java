package edu.wm.cs.gitprojectminer.codefeatures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Modifier;

public class ASTModifierHelper {

	public static List<String> getModifier(int modiNum){
		List<String> modifiers=new ArrayList<>();
		if (Modifier.isPublic(modiNum)) {
			modifiers.add("public");
		}
		if (Modifier.isProtected(modiNum)) {
			modifiers.add("protected");
		}
		if (Modifier.isPrivate(modiNum)) {
			modifiers.add("private");
		}
		if (Modifier.isStatic(modiNum)) {
			modifiers.add("static");
		}
		if (Modifier.isAbstract(modiNum)) {
			modifiers.add("abstract");
		}
		if (Modifier.isFinal(modiNum)) {
			modifiers.add("final");
		}
		if (Modifier.isNative(modiNum)) {
			modifiers.add("native");
		}
		if (Modifier.isSynchronized(modiNum)) {
			modifiers.add("synchronized");
		}
		if (Modifier.isTransient(modiNum)) {
			modifiers.add("transient");
		}
		if (Modifier.isVolatile(modiNum)) {
			modifiers.add("volatile");
		}
		if (Modifier.isStrictfp(modiNum)) {
			modifiers.add("strictfp");
		}
		return modifiers;
	}

}
