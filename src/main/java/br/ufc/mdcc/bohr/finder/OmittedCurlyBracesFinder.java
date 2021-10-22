package br.ufc.mdcc.bohr.finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

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
						
						getLoopOmitted(statements.get(i), statements.get(i + 1));
						
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
			
			boolean inlineIf = false;
			int line = ifStmt.getPosition().getLine();
			if (ifBlock.getStatements().size() == 1) {
				
				if (!ifStmt.prettyprint().contains("{")) {
					
					int stmtLine = ifBlock.getStatement(0).getPosition().getLine();
					int stmtEndLine = ifBlock.getStatement(0).getPosition().getEndLine();
					int nextStmtLine = nextStmt.getPosition().getLine();
					
					inlineIf = line == stmtLine && line != nextStmtLine;
					
					if(!inlineIf) {
						String filePath = ifStmt.getPosition().getFile().getAbsolutePath();

						boolean hasIndentationProblem = false;
						boolean nextStatementSameLine = false;
						
						hasIndentationProblem = hasIndentationProblem(filePath, stmtLine, stmtEndLine, nextStmtLine);
						nextStatementSameLine = line == nextStmtLine;
						
						if(hasIndentationProblem || nextStatementSameLine) {
							OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
							atom.setBlockStmt(ifStmt);
							atom.setNextLineStmt(nextStmt);
							confirmed.add(atom);
						}
					}
				}					
			}
			
			if (ifStmt.getElseStatement() != null) {
				if (((CtBlock<?>) ifStmt.getElseStatement()).getStatements().size() == 1) {
					CtStatement elseStmt = ifStmt.getElseStatement();
					
					String filePath = ifStmt.getPosition().getFile().getAbsolutePath();
					int stmtLine = elseStmt.getPosition().getLine();
					int stmtEndLine = elseStmt.getPosition().getEndLine();
					int nextStmtLine = nextStmt.getPosition().getLine();
					
					boolean inlineElse = !hasElseIf(elseStmt) 
							&& (ifBlock.getStatements().size() == 1 && (line == stmtLine - 1 || line == stmtLine - 2))
							|| (inlineIf && line == stmtLine);
					
					if(!inlineElse) {
						boolean hasIndentationProblem = hasIndentationProblem(filePath, stmtLine, stmtEndLine, nextStmtLine);
						
						if (!elseStmt.prettyprint().contains("{")) {
							if(!hasElseIf(elseStmt) ) {
								if(hasIndentationProblem) {
									OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
									atom.setBlockStmt(ifStmt);
									atom.setNextLineStmt(nextStmt);
									confirmed.add(atom);
								}
							} else {
								getIfElseOmitted((CtIf)elseStmt.getDirectChildren().get(0), nextStmt);
							}
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
	
	private void getLoopOmitted(CtStatement stmt, CtStatement nextStmt) {
		
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
						
						boolean inlineLoop = body.getPosition().getLine() == body.getPosition().getEndLine()
								&& stmt.getPosition().getLine() == body.getPosition().getLine()
								&& stmt.getPosition().getLine() != nextStmt.getPosition().getLine();
						
						if(!inlineLoop) {
							if(!stmt.prettyprint().contains("{")) {
								
								String filePath = stmt.getPosition().getFile().getAbsolutePath();
								int stmtLine = body.getDirectChildren().get(0).getPosition().getLine();
								int stmtEndLine = body.getDirectChildren().get(0).getPosition().getEndLine();
								int nextStmtLine = nextStmt.getPosition().getLine();
								
								boolean hasIndentationProblem = hasIndentationProblem(filePath, stmtLine, stmtEndLine, nextStmtLine);
								boolean nextStatementSameLine = stmt.getPosition().getLine() == nextStmt.getPosition().getLine();
								
								if(hasIndentationProblem || nextStatementSameLine) {
									OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
									atom.setBlockStmt(stmt);
									atom.setNextLineStmt(nextStmt);
									confirmed.add(atom);
								}
							}
						}
					} else {
						getLoopOmitted((CtStatement) childElement, nextStmt);
					}
				}
			}
		}
	}

	private boolean hasIndentationProblem(String filePath, int stmtLine, int stmtEndLine, int nextStmtLine) {
		
		if(nextStmtLine == stmtEndLine + 1) {
			
			try {
				String ommitedCurlyBraceLine = "";
				String nextLine = "";
				
				try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
					ommitedCurlyBraceLine = lines.skip(stmtLine - 1).findFirst().get();
				}
				
				try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
					nextLine = lines.skip(stmtEndLine).findFirst().get();
					
					if(StringUtils.isAllBlank(nextLine)) {
						return false;
					}
				}
				
				int count = countLineTabs(ommitedCurlyBraceLine);
				
				int countNextLine = countLineTabs(nextLine);
				
				if(countNextLine >= count) {
					return true;
				}
				
			} catch (IOException e) {
				// TODO: handle exception
			}
			
		}
		
		return false;
		
	}

	private int countLineTabs(String line) {
		int count = 0;
		for(char c : line.toCharArray()) {
			if("\t".equals("" + c)) {
				count = count + 4;
			} else if(' ' == c) {
				count++;
			} else {
				break;
			}
		}
		return count;
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