package br.aoc.bohr.finder;

import br.aoc.bohr.model.AoC;
import br.aoc.bohr.model.AoCInfo;
import br.aoc.bohr.model.Dataset;
import br.aoc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class PreAndPostIncrementDecrementFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtUnaryOperator<?>> unaryOprFilter = new TypeFilter<CtUnaryOperator<?>>(CtUnaryOperator.class);

			for (CtUnaryOperator<?> unaryOpr : element.getElements(unaryOprFilter)) {
				if ((unaryOpr.getParent() != null) && !(unaryOpr.getParent() instanceof CtUnaryOperator<?>)
						&& (unaryOpr.getParent() instanceof CtAssignment)
						|| (unaryOpr.getParent() instanceof CtBinaryOperator)) {

					int lineNumber = unaryOpr.getParent().getPosition().getEndLine();
					String snippet = unaryOpr.getParent().prettyprint();
					this.save(qualifiedName, unaryOpr, lineNumber, snippet);
				}
			}
		}

	}

	private void save(String qualifiedName, CtUnaryOperator<?> unaryOpr, int lineNumber, String snippet) {

		switch (unaryOpr.getKind()) {
		case POSTDEC:
			Dataset.store(qualifiedName, new AoCInfo(AoC.PostDEC, lineNumber, snippet));
			break;
			
		case POSTINC:
			Dataset.store(qualifiedName, new AoCInfo(AoC.PostINC, lineNumber, snippet));
			break;
		
		case PREDEC:
			Dataset.store(qualifiedName, new AoCInfo(AoC.PreDEC, lineNumber, snippet));
			break;
		
		case PREINC:
			Dataset.store(qualifiedName, new AoCInfo(AoC.PreINC, lineNumber, snippet));
			break;

		default:
			break;
		}
	}
}
