package br.ufc.mdcc.bohr.finder;

import java.util.List;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class OmittedCurlyBracesFinder extends AbstractProcessor<CtType<?>> {
	
	public void process(CtType<?> element) {
		
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			
			for (CtBlock<?> ctBlock : element.getElements(new TypeFilter<CtBlock<?>>(CtBlock.class))) {
				
				List<CtStatement> statements = ctBlock.getStatements();
				for (int i = 0; i < statements.size() - 1; i++) {
					
					try {
						getIfElseOmitted(qualifiedName, statements.get(i), statements.get(i + 1));
						
						getLoopOmittedBraces(qualifiedName, statements.get(i), statements.get(i + 1));
						
					} catch (SpoonException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void save(String qualifiedName, CtStatement stmt, CtStatement nextStmt) {
		Dataset.save(qualifiedName, new AoCInfo(AoC.OCB, stmt.getPosition().getLine(), stmt.prettyprint() + nextStmt.prettyprint()));
	}
	
	private void getIfElseOmitted(String qualifiedName, CtStatement stmt, CtStatement nextStmt) {
		
		if (stmt instanceof CtIf) {
			CtIf ifStmt = (CtIf) stmt;
			
			CtBlock<?> ifBlock = (CtBlock<?>) ifStmt.getThenStatement();
			
			if (ifBlock.getStatements().size() == 1) {
				
				if (!ifStmt.prettyprint().contains("{")) {
					
					int stmtEndLine = ifBlock.getStatement(0).getPosition().getEndLine();
					int nextStmtLine = nextStmt.getPosition().getLine();
					
					if(stmtEndLine == nextStmtLine) {
						save(qualifiedName, ifStmt, nextStmt);
					}
				}					
			}

			if (ifStmt.getElseStatement() != null) {
				getElseOmittedBraces(qualifiedName, ifStmt, nextStmt);
			}
		}
	}

	private void getElseOmittedBraces(String qualifiedName, CtIf ifStmt, CtStatement nextStmt) {
		CtBlock<?> elseBlock = (CtBlock<?>) ifStmt.getElseStatement();
		if (elseBlock.getStatements().size() == 1) {
			CtStatement elseStmt = ifStmt.getElseStatement();
			
			int stmtEndLine = elseStmt.getPosition().getEndLine();
			int nextStmtLine = nextStmt.getPosition().getLine();
			
			if (!elseStmt.prettyprint().contains("{")) {
				
				if(stmtEndLine == nextStmtLine) {
					
					if(!hasElseIf(elseStmt) ) {
						save(qualifiedName, nextStmt, ifStmt);
					} else {
						getIfElseOmitted(qualifiedName, (CtIf)elseStmt.getDirectChildren().get(0), nextStmt);
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
	
	private void getLoopOmittedBraces(String qualifiedName, CtStatement stmt, CtStatement nextStmt) {
		
		if (stmt instanceof CtFor 
				|| stmt instanceof CtWhile
				|| stmt instanceof CtForEach) {
			
			CtStatement body = null;
			
			if(stmt instanceof CtFor) {
				body = ((CtFor) stmt).getBody();
			} else if(stmt instanceof CtWhile) {
				body = ((CtWhile) stmt).getBody();
			} else if(stmt instanceof CtForEach) {
				body = ((CtForEach) stmt).getBody();
			}
			
			if(body != null) {
				
				if(body.getDirectChildren().size() == 1) {
					
					CtElement childElement = body.getDirectChildren().get(0);
					if(!(childElement instanceof CtFor) 
							&& !(childElement instanceof CtWhile)
							&& !(childElement instanceof CtForEach)) {
						
						if(!stmt.prettyprint().contains("{")) {
							
							int stmtEndLine = body.getDirectChildren().get(0).getPosition().getEndLine();
							int nextStmtLine = nextStmt.getPosition().getLine();
							
							if(stmtEndLine == nextStmtLine) {
								save(qualifiedName, stmt, nextStmt);
							}
						}
						
					} else {
						getLoopOmittedBraces(qualifiedName, (CtStatement) childElement, nextStmt);
					}
				}
			}
		}
	}

}