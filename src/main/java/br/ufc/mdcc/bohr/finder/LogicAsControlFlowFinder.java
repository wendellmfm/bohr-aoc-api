package br.ufc.mdcc.bohr.finder;

import java.util.List;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class LogicAsControlFlowFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			
			TypeFilter<CtBinaryOperator<?>> binaryOperatorFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);
			TypeFilter<CtUnaryOperator<?>> unaryOperatorFilter = new TypeFilter<CtUnaryOperator<?>>(CtUnaryOperator.class);

			for (CtBinaryOperator<?> operator : element.getElements(binaryOperatorFilter)) {
				
				if(operator.getKind() == BinaryOperatorKind.AND || operator.getKind() == BinaryOperatorKind.OR) {
					
					CtExpression<?> leftHandOperand = operator.getLeftHandOperand();
					CtExpression<?> rightHandOperand = operator.getRightHandOperand();
					
					List<CtUnaryOperator<?>> leftHandUnaryOperators = leftHandOperand.getElements(unaryOperatorFilter);
					List<CtUnaryOperator<?>> rightHandUnaryOperators = rightHandOperand.getElements(unaryOperatorFilter);
					
					if(!leftHandUnaryOperators.isEmpty() || !rightHandUnaryOperators.isEmpty()) {
						
						if(hasUnaryOperators(leftHandUnaryOperators) || hasUnaryOperators(rightHandUnaryOperators)) {
							int lineNumber = operator.getPosition().getEndLine();
							String snippet = operator.getParent().prettyprint();
							
							Dataset.store(qualifiedName, new AoCInfo(AoC.LaCTRF, lineNumber, snippet));
						}
						
					}
				}
			}
		}
	}
	
	private boolean hasUnaryOperators(List<CtUnaryOperator<?>> unaryOperators) {
		
		for (CtUnaryOperator<?> operator : unaryOperators) {

			switch (operator.getKind()) {
				case PREINC:
					return true;
					
				case PREDEC:
					return true;
					
				case POS:
					return true;
					
				case POSTINC:
					return true;
					
				case POSTDEC:
					return true;
					
				default:
					break;
				}
		}

		return false;
	}
	
}