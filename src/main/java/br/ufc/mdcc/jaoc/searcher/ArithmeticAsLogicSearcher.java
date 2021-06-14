package br.ufc.mdcc.jaoc.searcher;

import br.ufc.mdcc.jaoc.model.AoC;
import br.ufc.mdcc.jaoc.model.AoCInfo;
import br.ufc.mdcc.jaoc.model.Dataset;
import br.ufc.mdcc.jaoc.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.TypeFilter;

public class ArithmeticAsLogicSearcher extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtBinaryOperator<?>> binaryOperatorFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);

			for (CtBinaryOperator<?> operator : element.getElements(binaryOperatorFilter)) {
				
				if(hasCompareOperators(operator)) {
					CtExpression<?> leftHandOperand = operator.getLeftHandOperand();
					for (CtElement e : leftHandOperand.getElements(binaryOperatorFilter)) {
						if(hasArithmeticOperators((CtBinaryOperator<?>) e)) {
							int lineNumber = operator.getPosition().getEndLine();
							String snippet = operator.getParent().getOriginalSourceFragment().getSourceCode();
							
							Dataset.store(qualifiedName, new AoCInfo(AoC.AaL, lineNumber, snippet));
							
							break;
						}
					}
				}
			}
		}
	}
	
	private boolean hasCompareOperators(CtBinaryOperator<?> operator) {

		switch (operator.getKind()) {
			case EQ:
				return true;
			
			case NE:
				return true;

			case GT:
				return true;

			case GE:
				return true;

			case LT:
				return true;
				
			case LE:
				return true;

			default:
				break;
		}

		return false;
	}
	
	private boolean hasArithmeticOperators(CtBinaryOperator<?> operator) {

		switch (operator.getKind()) {
			case PLUS:
				return true;

			case MINUS:
				return true;

			case MUL:
				return true;

			case DIV:
				return true;

			default:
				break;
		}

		return false;
	}
}