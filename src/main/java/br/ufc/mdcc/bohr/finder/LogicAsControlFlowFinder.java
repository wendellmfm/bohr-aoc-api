package br.ufc.mdcc.bohr.finder;

import java.util.List;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
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

					try {
						CtExpression<?> rightHandOperand = operator.getRightHandOperand();
						
						if(hasLogicAsControlFlow(rightHandOperand)) {
							int lineNumber = operator.getPosition().getLine();
							String snippet = operator.getParent().prettyprint();
							
							Dataset.store(qualifiedName, new AoCInfo(AoC.LaCTRF, lineNumber, snippet));
						}
					} catch (SpoonException e) {
						// TODO: handle exception
					}
				}
			}
		}
	}
	
	private boolean hasLogicAsControlFlow(CtExpression<?> handOperand) {
		if(hasUnaryOperator(handOperand)
				|| hasMethodInvocation(handOperand)
				|| hasAssignment(handOperand)) {
			
			return true;
		}
		
		return false;
	}
	
	private boolean hasMethodInvocation(CtExpression<?> handOperand) {
		
		List<CtInvocation<?>> methods = handOperand.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
		
		if(!methods.isEmpty()) {
			for (CtInvocation<?> method : methods) {
				if(method.getType() != null
						&& !method.getType().toString().equalsIgnoreCase("boolean")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean hasAssignment(CtExpression<?> handOperand) {
		if(!handOperand.getElements(new TypeFilter<CtAssignment<?, ?>>(CtAssignment.class)).isEmpty()) {
			return true;
		}
		
		return false;
	}

	private boolean hasUnaryOperator(CtExpression<?> handOperand) {
		
		List<CtUnaryOperator<?>> unaryOperators = handOperand.getElements(new TypeFilter<CtUnaryOperator<?>>(CtUnaryOperator.class));
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