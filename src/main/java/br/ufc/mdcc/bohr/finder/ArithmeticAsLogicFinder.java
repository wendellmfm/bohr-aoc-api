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
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class ArithmeticAsLogicFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtBinaryOperator<?>> binaryOperatorFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);

			for (CtBinaryOperator<?> operator : element.getElements(binaryOperatorFilter)) {
				
				if(hasCompareOperator(operator)) {
					
					CtExpression<?> leftHandOperand = operator.getLeftHandOperand();
					CtExpression<?> rightHandOperand = operator.getRightHandOperand();
					
					if(leftHandOperand.prettyprint().equalsIgnoreCase("0") 
							|| rightHandOperand.prettyprint().equalsIgnoreCase("0")) {					
						
						List<CtBinaryOperator<?>> leftHandBinaryOperators = leftHandOperand.getElements(binaryOperatorFilter);
						List<CtBinaryOperator<?>> rightHandBinaryOperators = rightHandOperand.getElements(binaryOperatorFilter);
						
						if(hasBinaryOperators(leftHandBinaryOperators)
								|| hasBinaryOperators(rightHandBinaryOperators)
								|| hasAALExpression(leftHandOperand)
								|| hasAALExpression(rightHandOperand)) {
							
							int lineNumber = operator.getPosition().getEndLine();
							String snippet = operator.getParent().prettyprint();
							
							Dataset.store(qualifiedName, new AoCInfo(AoC.AaL, lineNumber, snippet));
						}
					}
				}
			}
		}
	}
	
	private boolean hasCompareOperator(CtBinaryOperator<?> operator) {

		switch (operator.getKind()) {
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
			if(hasPlusOrMinusOperators(operator)) {
				if(operator.getParent() instanceof CtBinaryOperator) {
					if(hasCompareOperator((CtBinaryOperator<?>) operator.getParent())) {
						return true;			
					}
				}
			}
		}

		return false;
	}
	
	private boolean hasAALExpression(CtExpression<?> handOperand) {
		String expression = handOperand.prettyprint();
		
		Pattern pattern = Pattern.compile("(\\(?(.+)[+-](.+)\\)?\\s*[*/]\\s*\\(?(.+)[+-](\\s*[^\\s]+)\\)?)");
		Matcher matcher = pattern.matcher(expression); 
		
		if(matcher.find()) {
			return true;
		}
		
		return false;
	}
	
	
	private boolean hasPlusOrMinusOperators(CtBinaryOperator<?> operator) {

		switch (operator.getKind()) {
			case PLUS:
				return true;

			case MINUS:
				return true;

			default:
				break;
		}

		return false;
	}
	
}