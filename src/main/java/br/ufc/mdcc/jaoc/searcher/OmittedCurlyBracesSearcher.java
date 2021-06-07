package br.ufc.mdcc.jaoc.searcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import br.ufc.mdcc.jaoc.model.AoC;
import br.ufc.mdcc.jaoc.model.AoCInfo;
import br.ufc.mdcc.jaoc.model.Dataset;
import br.ufc.mdcc.jaoc.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class OmittedCurlyBracesSearcher extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {

		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			Stack<CtStatement> candidates = new Stack<CtStatement>();
			List<CtStatement> confirmed = new ArrayList<CtStatement>();

			for (CtMethod<?> method : element.getAllMethods()) {
				for (CtStatement stmt : method.getBody().getStatements()) {
					if (stmt instanceof CtIf) {
						CtIf ifStmt = (CtIf) stmt;
						if (((CtBlock<?>) ifStmt.getThenStatement()).getStatements().size() == 1) {
							if (!ifStmt.getThenStatement().prettyprint().startsWith("{")) {
								candidates.push(ifStmt.getThenStatement());
							}
						}
						
						if (ifStmt.getElseStatement() != null) {
							if (((CtBlock<?>) ifStmt.getElseStatement()).getStatements().size() == 1) {
								if (!ifStmt.getElseStatement().prettyprint().startsWith("{")) {
									candidates.pop();
									candidates.push(ifStmt.getElseStatement());
								}
							}
						}
					} else if (!candidates.empty()) {
						confirmed.add(candidates.pop());
					}
				}
			}

			for (CtStatement stmt : confirmed) {
				int lineNumber = stmt.getPosition().getEndLine();
				String snippet = stmt.prettyprint();
				Dataset.store(qualifiedName, new AoCInfo(AoC.OCB, lineNumber, snippet));
			}
		}
	}
}