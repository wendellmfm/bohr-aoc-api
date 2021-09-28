package br.ufc.mdcc.bohr.finder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class ArithmeticAsLogicFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtBinaryOperator<?>> binaryOperatorFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);

			for (CtBinaryOperator<?> operator : element.getElements(binaryOperatorFilter)) {
				
				if(hasCompareOperators(operator)) {
					
					CtExpression<?> leftHandOperand = operator.getLeftHandOperand();
					CtExpression<?> rightHandOperand = operator.getRightHandOperand();
					
					if(leftHandOperand.prettyprint().equalsIgnoreCase("0") 
							|| rightHandOperand.prettyprint().equalsIgnoreCase("0")) {					
						
						List<CtBinaryOperator<?>> leftHandBinaryOperators = leftHandOperand.getElements(binaryOperatorFilter);
						List<CtBinaryOperator<?>> rightHandBinaryOperators = rightHandOperand.getElements(binaryOperatorFilter);
						
						if(hasBinaryOperators(leftHandBinaryOperators)
								|| hasBinaryOperators(rightHandBinaryOperators)) {
							
							if(!hasArithmeticOperatorsAsMethodParameter(operator, binaryOperatorFilter)) {
								int lineNumber = operator.getPosition().getEndLine();
								String snippet = operator.getParent().prettyprint();
								
								Dataset.store(qualifiedName, new AoCInfo(AoC.AaL, lineNumber, snippet));
							}
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
	
	private boolean hasBinaryOperators(List<CtBinaryOperator<?>> operators) {
		for (CtBinaryOperator<?> operator : operators) {
			if(operator.getParent() != null 
					&& operator.getParent() instanceof CtBinaryOperator
					&& !isOperatorBetweenBrackets(operator.getParent().prettyprint())) {
				
				if(hasCompareOperators((CtBinaryOperator<?>) operator.getParent())) {
					if(hasArithmeticOperators(operator)) {
						return true;				
					}
				}
				
			}
		}

		return false;
	}
	
	private boolean isOperatorBetweenBrackets(String statement) {
		Pattern pattern = Pattern.compile("\\[(.*?)\\]");
		Matcher matcher = pattern.matcher(statement); 
		
		if(matcher.find()) {
			return true;
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
	
	private boolean hasArithmeticOperatorsAsMethodParameter(CtBinaryOperator<?> operator, TypeFilter<CtBinaryOperator<?>> binaryOperatorFilter) {
		List<CtInvocation<?>> methodsInvocation = operator.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
		for (CtInvocation<?> method : methodsInvocation) {
			List<CtBinaryOperator<?>> operators = method.getElements(binaryOperatorFilter);
			for (CtBinaryOperator<?> op : operators) {
				if(hasArithmeticOperators(op)) {
					return true;
				}
			}
		}

		return false;
	}
	
}