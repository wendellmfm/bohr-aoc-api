package br.ufc.mdcc.bohr.finder;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class TypeConversionFinder extends AbstractProcessor<CtType<?>> {
	private final String CHAR = "char";
	private final String BYTE = "byte";
	private final String SHORT = "short";
	private final String INT = "int";
	private final String LONG = "long";
	private final String FLOAT = "float";
	private final String DOUBLE = "double";

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			
			String snippet = "";
			
			try {
				
				for (CtExpression<?> expression : element.getElements(new TypeFilter<CtExpression<?>>(CtExpression.class))) {
					List<CtTypeReference<?>> typeCasts = expression.getTypeCasts();
					if(!typeCasts.isEmpty()) {
						if(hasTypeConversionAtom(typeCasts.get(0).prettyprint(), expression)) {
							int lineNumber = expression.getPosition().getLine();
							snippet = expression.getOriginalSourceFragment().getSourceCode();
							Dataset.save(qualifiedName, new AoCInfo(AoC.TC, lineNumber, snippet));
						}
					}
				}
				
			} catch (SpoonException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}	
	}
	
	private boolean hasTypeConversionAtom(String castType, CtExpression<?> expression) {
		String variableType = "";
		
		if(castType != null) {
			
			if(expression instanceof CtBinaryOperator) {
				CtBinaryOperator<?> binaryOperator = (CtBinaryOperator<?>) expression;
				
				if(binaryOperator.getKind() == BinaryOperatorKind.MOD) {
					return false;
				}

				if(binaryOperator.getParent() != null 
						&& !(binaryOperator.getParent() instanceof BinaryOperator)) {
					String source = binaryOperator.getOriginalSourceFragment().getSourceCode();
					
					source = Util.removeExplicitCast(source).trim();
					
					boolean hasParentheses = source.charAt(0) == '(' 
							&& source.charAt(source.length() - 1) == ')';
					
					if(hasParentheses) {
						if(binaryOperator.getType() != null) {
							variableType = binaryOperator.getType().toString();
							if (checkNarrowingConversion(castType, variableType)) {
								return true;
							}
						}
					}
				}
			} else if(expression instanceof CtLiteral) {
				if(expression.getType() != null) {
					variableType = expression.getType().toString();
					if (checkNarrowingConversion(castType, variableType)) {
						CtLiteral<?> literal = (CtLiteral<?>) expression;
						if(checkLiteralOutOfRange(literal, castType)) {
							return true;
						}
					}
				}
			} else if(!(expression instanceof CtInvocation)) { //Invocation arguments are cover by VariableRead expressions.
				if(expression.getType() != null) {
					variableType = expression.getType().toString();
					if (checkNarrowingConversion(castType, variableType)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean checkNarrowingConversion (String assignmentType, String assignedType) {
		final String[] CHAR_CONVERSIONS = new String[] {BYTE, SHORT};
		final String[] SHORT_CONVERSIONS = new String[] {BYTE, CHAR};
		final String[] INT_CONVERSIONS = new String[] {BYTE, SHORT, CHAR};
		final String[] LONG_CONVERSIONS = new String[] {BYTE, SHORT, CHAR, INT};
		final String[] FLOAT_CONVERSIONS = new String[] {BYTE, SHORT, CHAR, INT, LONG};
		final String[] DOUBLE_CONVERSIONS = new String[] {BYTE, SHORT, CHAR, INT, LONG, FLOAT};
		
		boolean result = false;
		
		switch (assignedType) {
			case SHORT:
				result = Arrays.asList(SHORT_CONVERSIONS).contains(assignmentType);
				break;
			case INT:
				result = Arrays.asList(INT_CONVERSIONS).contains(assignmentType);
				break;
			case LONG:
				result = Arrays.asList(LONG_CONVERSIONS).contains(assignmentType);
				break;
			case FLOAT:
				result = Arrays.asList(FLOAT_CONVERSIONS).contains(assignmentType);
				break;
			case DOUBLE:
				result = Arrays.asList(DOUBLE_CONVERSIONS).contains(assignmentType);
				break;
			case CHAR:
				result = Arrays.asList(CHAR_CONVERSIONS).contains(assignmentType);
				break;
	
			default:
				break;
		}
		
		return result;
	}
	
	private boolean checkLiteralOutOfRange(CtLiteral<?> literal, String castType) {
		if(literal.getValue() == null) {
			return false;
		}
		
		if(literal.getValue() != null && literal.getValue().toString().isBlank()) {
			return false;
		}
		
		try {
			
			String value = literal.getValue().toString();
			
			switch (castType) {
			case BYTE:
				if(Double.valueOf(value) < Byte.MIN_VALUE && Short.valueOf(value) > Byte.MIN_VALUE) {
					return true;
				}
				break;
			case SHORT:
				if(Double.valueOf(value) < Short.MIN_VALUE && Short.valueOf(value) > Short.MIN_VALUE) {
					return true;
				}
				break;
			case INT:
				if(Double.valueOf(value) < Integer.MIN_VALUE && Integer.valueOf(value) > Integer.MIN_VALUE) {
					return true;
				}
				break;
			case LONG:
				if(Double.valueOf(value) < Long.MIN_VALUE && Long.valueOf(value) > Long.MIN_VALUE) {
					return true;
				}
				break;
			case FLOAT:
				if(Double.valueOf(value) < Float.MIN_VALUE && Float.valueOf(value) > Float.MIN_VALUE) {
					return true;
				}
				break;
			case DOUBLE:
				if(Double.valueOf(value) < Double.MIN_VALUE && Double.valueOf(value) > Double.MIN_VALUE) {
					return true;
				}
				break;
			default:
				break;
			}
			
		} catch (NumberFormatException e) {
			// TODO: handle exception
			//e.printStackTrace();
		}
		
		return false;
	}
	
}
