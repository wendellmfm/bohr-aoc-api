package br.ufc.mdcc.bohr.finder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class InfixOperatorPrecedenceFinder extends AbstractProcessor<CtType<?>> {

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
					
			TypeFilter<CtBinaryOperator<?>> binaryOprFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);
			for (CtBinaryOperator<?> binaryOpr : element.getElements(binaryOprFilter)) {
				
				try {
					
					getArithmeticalIOP(qualifiedName, binaryOpr);
					
					getLogicalIOP(qualifiedName, binaryOpr);
									
				} catch (SpoonException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	private void getArithmeticalIOP(String qualifiedName, CtBinaryOperator<?> binaryOpr) {
		if(binaryOpr.getKind() == BinaryOperatorKind.MUL 
				|| binaryOpr.getKind() == BinaryOperatorKind.DIV
				|| binaryOpr.getKind() == BinaryOperatorKind.MOD) {
								
			if(binaryOpr.getParent() instanceof CtBinaryOperator){
				CtBinaryOperator<?> binaryParent = (CtBinaryOperator<?>) binaryOpr.getParent();
						
				if(binaryParent.getKind() == BinaryOperatorKind.PLUS
						|| binaryParent.getKind() == BinaryOperatorKind.MINUS){
					
					if(!hasStringConcatenation(binaryParent) && !hasParentheses(binaryOpr)) {
						save(qualifiedName, binaryOpr);
					}
				}
			}
		}
	}

	private void getLogicalIOP(String qualifiedName, CtBinaryOperator<?> binaryOpr) {
		if(binaryOpr.getKind() == BinaryOperatorKind.AND || binaryOpr.getKind() == BinaryOperatorKind.OR) {
			
			if(binaryOpr.getParent() instanceof CtBinaryOperator) {
				
				boolean andCondition = binaryOpr.getKind() == BinaryOperatorKind.AND
						&& ((CtBinaryOperator<?>) binaryOpr.getParent()).getKind() == BinaryOperatorKind.OR;
				
				boolean orCondition = binaryOpr.getKind() == BinaryOperatorKind.OR
						&& ((CtBinaryOperator<?>) binaryOpr.getParent()).getKind() == BinaryOperatorKind.AND;
				
				if(andCondition || orCondition) {
					
					if(!hasParentheses(binaryOpr)) {
						save(qualifiedName, binaryOpr);
					}
				}
			}
			
		}
	}
	
	private void save(String qualifiedName, CtBinaryOperator<?> binaryOpr) {
		int lineNumber = binaryOpr.getPosition().getLine();
		String snippet = getFullExpression(binaryOpr).getOriginalSourceFragment().getSourceCode();
		Dataset.save(qualifiedName, new AoCInfo(AoC.IOP, lineNumber, snippet));
	}

	private CtElement getFullExpression(CtElement element) {
		if (element.getParent() != null 
				&& element.getParent() instanceof CtBinaryOperator) {
			return this.getFullExpression(element.getParent());
		} else {
			return element;
		}
	}
	
	private boolean hasParentheses(CtBinaryOperator<?> binaryOperator) {
		String expression = binaryOperator.getOriginalSourceFragment().getSourceCode();
		
		Pattern pattern = Pattern.compile(Util.EXPLICIT_CAST_PATTERN);
		Matcher matcher = pattern.matcher(expression);
		
		boolean hasTypeConversion = matcher.find();
		
		if(hasTypeConversion) {
			expression = Util.removeExplicitCast(expression);
		}
		
		if(isBetweenParentheses(expression)) {
			return true;
		}
		
		return false;
	}
	
	private boolean isBetweenParentheses(String expression) {
		int size = expression.length();
		
		char firstChar = expression.charAt(0);
		char lastChar = expression.charAt(size - 1);
		
		if(firstChar == '(' && lastChar == ')') {
			return true;
		}
		
		return false;
	}
	
	private boolean hasStringConcatenation(CtBinaryOperator<?> binaryParent) {
		if(binaryParent.getKind() == BinaryOperatorKind.PLUS) {
			String stringType = "java.lang.String";

			CtExpression<?> leftHandOperand = binaryParent.getLeftHandOperand();
			CtExpression<?> rightHandOperand = binaryParent.getRightHandOperand();
			
			boolean leftHandCondition = leftHandOperand.getType() != null 
					&& leftHandOperand.getType().toString().equalsIgnoreCase(stringType);
			
			boolean rightHandCondition = rightHandOperand.getType() != null 
				&& rightHandOperand.getType().toString().equalsIgnoreCase(stringType);
			
			if(leftHandCondition || rightHandCondition) {
				return true;
			}
		}
		
		return false;
	}
}