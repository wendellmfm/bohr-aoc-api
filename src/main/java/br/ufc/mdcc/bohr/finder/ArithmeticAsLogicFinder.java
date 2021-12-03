package br.ufc.mdcc.bohr.finder;

import java.util.List;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class ArithmeticAsLogicFinder extends AbstractProcessor<CtType<?>> {

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtBinaryOperator<?>> binaryOperatorFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);

			for (CtBinaryOperator<?> operator : element.getElements(binaryOperatorFilter)) {
				
				if(hasEqualsOrNotEqualsOperator(operator)) {
					
					CtExpression<?> leftHandOperand = operator.getLeftHandOperand();
					CtExpression<?> rightHandOperand = operator.getRightHandOperand();
					
					if(leftHandOperand.prettyprint().equalsIgnoreCase("0") 
							|| rightHandOperand.prettyprint().equalsIgnoreCase("0")) {					
						
						List<CtBinaryOperator<?>> leftHandBinaryOperators = leftHandOperand.getElements(binaryOperatorFilter);
						List<CtBinaryOperator<?>> rightHandBinaryOperators = rightHandOperand.getElements(binaryOperatorFilter);
						
						if(hasAALExpression(leftHandBinaryOperators)
								|| hasAALExpression(rightHandBinaryOperators)) {
							
							int lineNumber = operator.getPosition().getEndLine();
							String snippet = operator.getParent().prettyprint();
							
							Dataset.save(qualifiedName, new AoCInfo(AoC.AL, lineNumber, snippet));
						}
					}
				}
			}
		}
	}
	
	private boolean hasAALExpression(List<CtBinaryOperator<?>> operators) {
		for (CtBinaryOperator<?> operator : operators) {
			if(operator.getParent() instanceof CtBinaryOperator) {
				if(hasEqualsOrNotEqualsOperator((CtBinaryOperator<?>) operator.getParent())) {
					if(hasPlusMinusOrMultOperators(operator)){
						return true;			
					}
				}
			}
		}

		return false;
	}
	
	private boolean hasEqualsOrNotEqualsOperator(CtBinaryOperator<?> operator) {

		switch (operator.getKind()) {
			case EQ:
				return true;

			case NE:
				return true;
				
			default:
				break;
		}

		return false;
	}
	
	private boolean hasPlusMinusOrMultOperators(CtBinaryOperator<?> operator) {

		switch (operator.getKind()) {
			case PLUS:
				return true;

			case MINUS:
				return true;
				
			case MUL:
				return true;

			default:
				break;
		}

		return false;
	}
	
}