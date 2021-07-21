package br.ufc.mdcc.bohr.finder;

import java.util.List;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class LogicAsControlFlowFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			
			TypeFilter<CtBinaryOperator<?>> binaryOperatorFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);

			for (CtBinaryOperator<?> operator : element.getElements(binaryOperatorFilter)) {
				
				if(operator.getKind() == BinaryOperatorKind.AND || operator.getKind() == BinaryOperatorKind.OR) {

					CtExpression<?> rightHandOperand = operator.getRightHandOperand();
					
					if(hasUnaryOperator(rightHandOperand)
							|| hasMethodInvocation(rightHandOperand)
							|| hasAssignment(rightHandOperand)) {
						int lineNumber = operator.getPosition().getEndLine();
						String snippet = operator.getParent().prettyprint();
						
						Dataset.store(qualifiedName, new AoCInfo(AoC.LaCTRF, lineNumber, snippet));
					}
				}
			}
		}
	}
	
	private boolean hasMethodInvocation(CtExpression<?> rightHandOperand) {
		
		if(!rightHandOperand.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class)).isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasAssignment(CtExpression<?> rightHandOperand) {
		if(!rightHandOperand.getElements(new TypeFilter<CtAssignment<?, ?>>(CtAssignment.class)).isEmpty()) {
			return true;
		}
		
		return false;
	}

	private boolean hasUnaryOperator(CtExpression<?> rightHandOperand) {
		
		List<CtUnaryOperator<?>> unaryOperators = rightHandOperand.getElements(new TypeFilter<CtUnaryOperator<?>>(CtUnaryOperator.class));
		if(!unaryOperators.isEmpty() && hasIncrementDecrementUnaryOperators(unaryOperators)) {
			return true;
		}
		
		return false;
	}	
	
	private boolean hasIncrementDecrementUnaryOperators(List<CtUnaryOperator<?>> unaryOperators) {
		
		if(unaryOperators.isEmpty()) {
			return false;
		}
		
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