package edu.wm.cs.gitprojectminer.codefeatures;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import edu.wm.cs.gitprojectminer.datatype.ASTRoot;
import edu.wm.cs.gitprojectminer.datatype.ASTTypeDeclare;

/**
 * @author liuyin
 * ASTFeatureParser extract code feature of a java source file
 * 
 */
public class ASTFeatureParser {

	/*Take a java source file as input stream
	 *Extract code features from AST(typede claration, field declaration, method declaration)
	 *Return an ast root containing all ast nodes info in the input source file
	 * 
	 */
	public static ASTRoot parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		//System.out.println("ClassDeclaration******************");
		//System.out.println(cu.toString());
		//System.out.println("Package: "+cu.getPackage());
		
		ASTRoot astRoot=new ASTRoot();
		
		cu.accept(new ASTRootVisitor(astRoot) {
			public boolean visit(PackageDeclaration node) {
				//System.out.println("PackageDeclaration: " + node.getName());

				astRoot.setPackageDeclare(node.getName().toString());
				return false;
				
			}
			
			
			public boolean visit(ImportDeclaration node) {
				//System.out.println("Import: " + node.getName());
				astRoot.getImports().add(node.getName().toString());
				return false;
				
			}
			
			public boolean visit(TypeDeclaration node) {
				//System.out.println("TypeDeclaration: " + node.getName());
				ASTTypeDeclare astTypeDeclare=new ASTTypeDeclare(astRoot);
				//astTypeDeclare.setAstRoot(astRoot);
				astTypeDeclare.parseFromTypeDeclaration(node);
				
				astRoot.setAstTypeDeclare(astTypeDeclare);
				
				
				return false;
				
			}
			
			public boolean visit(FieldDeclaration node) {
				System.out.println("FieldDeclaration******************");
				System.out.println("Fragments: " + node.fragments());
				System.out.println("Modifiers: " + node.getModifiers());
			    System.out.println("Type: " + node.getType());
				
/*					for (int i=0;i<node.fragments().size();i++){
					VarDeclare varDeclare=new VarDeclare();
					VariableDeclarationFragment frag=(VariableDeclarationFragment) node.fragments().get(i);
					varDeclare.name=node.fragments().get(i).getName().toString();
				}
				
				FieldDeclare fieldDeclare=new FieldDeclare();
				fieldDeclare.name=node.fragments().toString();
				astRoot.typeDeclare.fields.add(fieldDeclare);
*/					
				
				return false;
				
			}
			
			public boolean visit(MethodDeclaration node) {
				
					System.out.println("MethodDeclaration******************");
					System.out.println("Name: " + node.getName());
					System.out.println("Modifiers: " + node.getModifiers());

					System.out.println("ReturnType: " + node.getReturnType2());
					System.out.println("Parameters: " + node.parameters().toString());

				
/*					MethodDeclare methodDeclare=new MethodDeclare();
				methodDeclare.name=node.getName().toString();
				astRoot.typeDeclare.methods.add(methodDeclare);
*/					
/*
                    Block block = node.getBody();
                    block.accept(new ASTVisitor() {
                        public boolean visit(MethodInvocation node) {
                            System.out.println("Name: " + node.getName());

                            Expression expression = node.getExpression();
                            if (expression != null) {
                                System.out.println("Expr: " + expression.toString());
                                ITypeBinding typeBinding = expression.resolveTypeBinding();
                                if (typeBinding != null) {
                                    System.out.println("Type: " + typeBinding.getName());
                                }
                            }
                            IMethodBinding binding = node.resolveMethodBinding();
                            if (binding != null) {
                                ITypeBinding type = binding.getDeclaringClass();
                                if (type != null) {
                                    System.out.println("Decl: " + type.getName());
                                }
                            }
	
                            return true;
                        }
                    });
 */	               
                return true;
            }
			
/*			
			Set names = new HashSet();
 
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());
				System.out.println("Declaration of '" + name + "' at line"
						+ cu.getLineNumber(name.getStartPosition()));
				return false; // do not continue 
			}
 
			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
					System.out.println("Usage of '" + node + "' at line "
							+ cu.getLineNumber(node.getStartPosition()));
				}
				return true;
			}
 */			
		});
		
		
		//System.out.println(astRoot.packageDeclare);
		//System.out.println(astRoot.imports);
		return astRoot;

		
	}	


}
