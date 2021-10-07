package br.ufc.mdcc.bohr.finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			List<OmittedCurlyBracesAtom> confirmed = new ArrayList<OmittedCurlyBracesAtom>();
			
			for (CtBlock<?> ctBlock : element.getElements(new TypeFilter<CtBlock<?>>(CtBlock.class))) {
				
				List<CtStatement> statements = ctBlock.getStatements();
				for (int i = 0; i < statements.size() - 1; i++) {
					
					try {
						getIfElseOmitted(confirmed, statements.get(i), statements.get(i + 1));
						
						getWhileOmitted(confirmed, statements.get(i), statements.get(i + 1));
						
						getForOmitted(confirmed, statements.get(i), statements.get(i + 1));
						
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
	
	private void getIfElseOmitted(List<OmittedCurlyBracesAtom> confirmed, CtStatement stmt, CtStatement nextStmt) {
		if (stmt instanceof CtIf) {
			CtIf ifStmt = (CtIf) stmt;
			CtBlock<?> ifBlock = (CtBlock<?>) ifStmt.getThenStatement();
			
			boolean ifInline = false;
			if (ifBlock.getStatements().size() == 1) {
				if (!ifStmt.prettyprint().contains("{")) {
		
					ifInline = ifBlock.getStatement(0).getPosition().getLine() == ifStmt.getPosition().getLine()
							&& ifStmt.getPosition().getLine() != nextStmt.getPosition().getLine();
					
					if(!ifInline) {
						boolean hasIndentationProblem = hasIndentationProblem(ifStmt.getPosition().getFile().getAbsolutePath(), ifStmt.getPosition().getLine());
						boolean nextStatementSameLine = ifStmt.getPosition().getLine() == nextStmt.getPosition().getLine();
						
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
					
					boolean elseInline = (ifBlock.getStatements().size() == 1
							&& ifStmt.getPosition().getLine() == elseStmt.getPosition().getLine() - 2)
							|| (ifInline && ifStmt.getPosition().getLine() == elseStmt.getPosition().getLine() - 1);
						
					if(!elseInline) {
						boolean hasIndentationProblem = hasIndentationProblem(ifStmt.getPosition().getFile().getAbsolutePath(), elseStmt.getPosition().getLine() - 1);
						boolean hasElseIf = hasElseIf(elseStmt);
						
						if (!elseStmt.prettyprint().contains("{")) {
							if(!hasElseIf && hasIndentationProblem) {
								OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
								atom.setBlockStmt(ifStmt);
								atom.setNextLineStmt(nextStmt);
								confirmed.add(atom);
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

	private void getForOmitted(List<OmittedCurlyBracesAtom> confirmed, CtStatement stmt, CtStatement nextStmt) {
		if (stmt instanceof CtFor) {
			CtFor ctFor = (CtFor) stmt;
			CtStatement body = ctFor.getBody();
			
			boolean forInline = body.getPosition().getLine() == body.getPosition().getEndLine()
					&& ctFor.getPosition().getLine() == body.getPosition().getLine()
					&& ctFor.getPosition().getLine() != nextStmt.getPosition().getLine();
			
			if(!forInline) {
				addConfirmedOCB(confirmed, stmt, nextStmt);
			}
		}
	}

	private void getWhileOmitted(List<OmittedCurlyBracesAtom> confirmed, CtStatement stmt, CtStatement nextStmt) {
		if (stmt instanceof CtWhile) {
			CtWhile ctWhile = (CtWhile) stmt;
			CtStatement body = ctWhile.getBody();
			
			boolean whileInline = body.getPosition().getLine() == body.getPosition().getEndLine()
					&& ctWhile.getPosition().getLine() == body.getPosition().getLine()
					&& ctWhile.getPosition().getLine() != nextStmt.getPosition().getLine();
			
			if(!whileInline) {
				addConfirmedOCB(confirmed, stmt, nextStmt);
			}
		}
	}

	private void addConfirmedOCB(List<OmittedCurlyBracesAtom> confirmed, CtStatement stmt, CtStatement nextStmt) {
		if(!stmt.prettyprint().contains("{")) {
			boolean hasIndentationProblem = hasIndentationProblem(stmt.getPosition().getFile().getAbsolutePath(), stmt.getPosition().getLine());
			boolean nextStatementSameLine = stmt.getPosition().getLine() == nextStmt.getPosition().getLine();
			
			if(hasIndentationProblem || nextStatementSameLine) {
				OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
				atom.setBlockStmt(stmt);
				atom.setNextLineStmt(nextStmt);
				confirmed.add(atom);
			}
		}
	}
	
	private boolean hasIndentationProblem(String filePath, int ommitedCurlyBraceLineNumber) {
		
		try {
			String ommitedCurlyBraceLine = "";
			String nextLine = "";
			
			try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
				ommitedCurlyBraceLine = lines.skip(ommitedCurlyBraceLineNumber).findFirst().get();
			}
			
			try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
				nextLine = lines.skip(ommitedCurlyBraceLineNumber + 1).findFirst().get();
			}
		    
		    int count = countLineTabs(ommitedCurlyBraceLine);
		    
		    int countNextLine = countLineTabs(nextLine);
		    
		    if(countNextLine >= count) {
		    	return true;
		    }
		    
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		return false;
	}

	private int countLineTabs(String line) {
		int tabCount = 0;
		boolean containsTab = line.contains("\t");
		for(char c : line.toCharArray()) {
			if(containsTab) {
				if("\t".equals("" + c)) {
					tabCount++;
				} 
			}else {
				if(' ' == c) {
					tabCount++;
				} else {
					break;
				}
			}
		}
		return tabCount;
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