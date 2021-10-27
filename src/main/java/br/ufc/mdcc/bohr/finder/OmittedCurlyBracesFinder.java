package br.ufc.mdcc.bohr.finder;

import java.util.ArrayList;
import java.util.List;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.TypeFilter;

public class OmittedCurlyBracesFinder extends AbstractProcessor<CtClass<?>> {
	
	private List<OmittedCurlyBracesAtom> confirmed = new ArrayList<OmittedCurlyBracesAtom>();

	public void process(CtClass<?> element) {
		
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			confirmed = new ArrayList<OmittedCurlyBracesAtom>();
			
			for (CtBlock<?> ctBlock : element.getElements(new TypeFilter<CtBlock<?>>(CtBlock.class))) {
				
				List<CtStatement> statements = ctBlock.getStatements();
				for (int i = 0; i < statements.size() - 1; i++) {
					
					try {
						getIfElseOmitted(statements.get(i), statements.get(i + 1));
						
						getLoopOmittedBraces(statements.get(i), statements.get(i + 1));
						
					} catch (SpoonException e) {
						// TODO: handle exception
					}
				}
			}

			for (OmittedCurlyBracesAtom atom : confirmed) {
				Dataset.store(qualifiedName, new AoCInfo(AoC.OCB, atom.getLine(), atom.getAtomSnippet()));
			}
		}
	}
	
	private void getIfElseOmitted(CtStatement stmt, CtStatement nextStmt) {
		
		if (stmt instanceof CtIf) {
			CtIf ifStmt = (CtIf) stmt;
			
			CtBlock<?> ifBlock = (CtBlock<?>) ifStmt.getThenStatement();
			
			if (ifBlock.getStatements().size() == 1) {
				
				if (!ifStmt.prettyprint().contains("{")) {
					
					int stmtEndLine = ifBlock.getStatement(0).getPosition().getEndLine();
					int nextStmtLine = nextStmt.getPosition().getLine();
					
					if(stmtEndLine == nextStmtLine) {
						OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
						atom.setBlockStmt(ifStmt);
						atom.setNextLineStmt(nextStmt);
						confirmed.add(atom);
					}
				}					
			}
			
			getElseOmittedBraces(ifStmt, nextStmt);
		}
	}

	private void getElseOmittedBraces(CtIf ifStmt, CtStatement nextStmt) {
		if (ifStmt.getElseStatement() != null) {
			
			CtBlock<?> elseBlock = (CtBlock<?>) ifStmt.getElseStatement();
			if (elseBlock.getStatements().size() == 1) {
				CtStatement elseStmt = ifStmt.getElseStatement();
				
				int stmtEndLine = elseStmt.getPosition().getEndLine();
				int nextStmtLine = nextStmt.getPosition().getLine();
				
				if (!elseStmt.prettyprint().contains("{")) {
					
					if(stmtEndLine == nextStmtLine) {
						
						if(!hasElseIf(elseStmt) ) {
							OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
							atom.setBlockStmt(ifStmt);
							atom.setNextLineStmt(nextStmt);
							confirmed.add(atom);
						} else {
							getIfElseOmitted((CtIf)elseStmt.getDirectChildren().get(0), nextStmt);
						}
					}
				}
			}
		}
	}
	
	private boolean hasElseIf(CtStatement elseStmt) {
		List<CtElement> directChildren = elseStmt.getDirectChildren();
		
		if (!directChildren.isEmpty()) {
			if(directChildren.get(0) instanceof CtIf) {
				return true;
			}
		}
		
		return false;
	}
	
	private void getLoopOmittedBraces(CtStatement stmt, CtStatement nextStmt) {
		
		if (stmt instanceof CtFor || stmt instanceof CtWhile) {
			CtStatement body = null;
			
			if(stmt instanceof CtFor) {
				body = ((CtFor) stmt).getBody();
			}else if(stmt instanceof CtWhile) {
				body = ((CtWhile) stmt).getBody();
			}
			
			if(body != null) {
				
				if(body.getDirectChildren().size() == 1) {
					
					CtElement childElement = body.getDirectChildren().get(0);
					if(!(childElement instanceof CtFor) && !(childElement instanceof CtWhile)) {
						
						if(!stmt.prettyprint().contains("{")) {
							
							int stmtEndLine = body.getDirectChildren().get(0).getPosition().getEndLine();
							int nextStmtLine = nextStmt.getPosition().getLine();
							
							if(stmtEndLine == nextStmtLine) {
								OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
								atom.setBlockStmt(stmt);
								atom.setNextLineStmt(nextStmt);
								confirmed.add(atom);
							}
						}
						
					} else {
						getLoopOmittedBraces((CtStatement) childElement, nextStmt);
					}
				}
			}
		}
	}
	
	private class OmittedCurlyBracesAtom {
		private CtStatement blockStmt;
		
		private CtStatement nextLineStmt;

		public void setBlockStmt(CtStatement blockStmt) {
			this.blockStmt = blockStmt;
		}

		public void setNextLineStmt(CtStatement nextLineStmt) {
			this.nextLineStmt = nextLineStmt;
		}
		
		public String getAtomSnippet() {
			return blockStmt.prettyprint() 
					+ nextLineStmt.prettyprint();
		}
		
		public int getLine() {
			return blockStmt.getPosition().getLine();
		}
	}

}