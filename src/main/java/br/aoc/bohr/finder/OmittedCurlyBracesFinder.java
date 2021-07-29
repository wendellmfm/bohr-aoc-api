package br.aoc.bohr.finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import br.aoc.bohr.model.AoC;
import br.aoc.bohr.model.AoCInfo;
import br.aoc.bohr.model.Dataset;
import br.aoc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class OmittedCurlyBracesFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			List<OmittedCurlyBracesAtom> confirmed = new ArrayList<OmittedCurlyBracesAtom>();
			
			for (CtBlock<?> ctBlock : element.getElements(new TypeFilter<CtBlock<?>>(CtBlock.class))) {
				
				List<CtStatement> statements = ctBlock.getStatements();
				for (int i = 0; i < statements.size() - 1; i++) {
					
					getIfElseOmitted(confirmed, statements.get(i), statements.get(i + 1));
						
					getWhileOmitted(confirmed, statements.get(i), statements.get(i + 1));
						
					getForOmitted(confirmed, statements.get(i), statements.get(i + 1));
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
			if (((CtBlock<?>) ifStmt.getThenStatement()).getStatements().size() == 1) {
				try {
					if ((!ifStmt.prettyprint().contains("{")
							&& hasIndentationProblem(stmt.getPosition().getFile().getAbsolutePath(), stmt.getPosition().getLine()))
							|| (!ifStmt.prettyprint().contains("{") 
									&& stmt.getPosition().getLine() == nextStmt.getPosition().getLine())) {
						OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
						atom.setBlockStmt(ifStmt);
						atom.setNextLineStmt(nextStmt);
						confirmed.add(atom);
					}					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
			}
			
			if (ifStmt.getElseStatement() != null) {
				if (((CtBlock<?>) ifStmt.getElseStatement()).getStatements().size() == 1) {
					CtStatement elseStmt = ifStmt.getElseStatement();
					if ((!elseStmt.prettyprint().contains("{")
							&& hasIndentationProblem(stmt.getPosition().getFile().getAbsolutePath(), elseStmt.getPosition().getLine() - 1))
							|| (!elseStmt.prettyprint().contains("{") 
									&& elseStmt.getPosition().getLine() == nextStmt.getPosition().getLine())) {
						OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
						atom.setBlockStmt(ifStmt);
						atom.setNextLineStmt(nextStmt);
						confirmed.add(atom);
					}
				}
			}
		} 
	}

	private void getForOmitted(List<OmittedCurlyBracesAtom> confirmed, CtStatement stmt, CtStatement nextStmt) {
		if (stmt instanceof CtFor) {
			addConfirmedOCB(confirmed, stmt, nextStmt);
		}
	}

	private void getWhileOmitted(List<OmittedCurlyBracesAtom> confirmed, CtStatement stmt, CtStatement nextStmt) {
		if (stmt instanceof CtWhile) {
			addConfirmedOCB(confirmed, stmt, nextStmt);
		}
	}

	private void addConfirmedOCB(List<OmittedCurlyBracesAtom> confirmed, CtStatement stmt, CtStatement nextStmt) {
		if ((!stmt.prettyprint().contains("{")
				&& hasIndentationProblem(stmt.getPosition().getFile().getAbsolutePath(), stmt.getPosition().getLine()))
				|| (!stmt.prettyprint().contains("{") 
						&& stmt.getPosition().getLine() == nextStmt.getPosition().getLine())) {
			OmittedCurlyBracesAtom atom = new OmittedCurlyBracesAtom();
			atom.setBlockStmt(stmt);
			atom.setNextLineStmt(nextStmt);
			confirmed.add(atom);
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
			e.printStackTrace();
		}
		
		return false;
	}

	private int countLineTabs(String ommitedCurlyBraceLine) {
		int tabCount = 0;
		for(char c : ommitedCurlyBraceLine.toCharArray()) {
		    if("\t".equals("" + c)) {
		    	tabCount++;
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