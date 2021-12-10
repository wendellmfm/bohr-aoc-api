package br.ufc.mdcc.bohr.finder;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
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
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
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
	
		if(castType != null) {
			
			if(expression instanceof CtLiteral) {
				
				CtLiteral<?> literal = (CtLiteral<?>) expression;
				if(hasLiteralCase(literal, castType)) {
					return true;
				}
				
			} else if(expression instanceof CtUnaryOperator) { 
				
				CtUnaryOperator<?> unary = (CtUnaryOperator<?>) expression;
				if(hasUnaryCase(unary, castType)) {
					return true;
				}
				
			} else if(expression instanceof CtBinaryOperator) {
				
				CtBinaryOperator<?> binaryOperator = (CtBinaryOperator<?>) expression;
				if(hasModulusOperation(binaryOperator)) {
					return false;
				}
				
				if(hasBinaryOperatorCase(binaryOperator, castType)) {
					return true;
				}

			} else if(!(expression instanceof CtInvocation)) { //Invocation arguments are covered by VariableRead expressions.
				
				if(expression.getType() != null) {
					String variableType = expression.getType().toString();
					if (checkNarrowingConversion(castType, variableType)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean hasLiteralCase(CtLiteral<?> literal, String castType) {
		
		if(literal.getType() != null) {
			String variableType = literal.getType().toString();
			if (checkNarrowingConversion(castType, variableType)) {
				if(checkLiteralOutOfRange(literal.getValue().toString(), castType)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean hasUnaryCase(CtUnaryOperator<?> unary, String castType) {
		
		if(unary.getType() != null) {
			String variableType = unary.getType().toString();
			if(unary.getKind() == UnaryOperatorKind.NEG) { // Handle literal negative numbers.
				String literalValue = extractNegativeLiteral(unary);
				if (checkNarrowingConversion(castType, variableType)) {
					if(checkLiteralOutOfRange(literalValue, castType)) {
						return true;
					}
				}
			} else {
				if (checkNarrowingConversion(castType, variableType)) {
					return true;
				}
			}
		}

		return false;
	}
	
	private boolean hasBinaryOperatorCase(CtBinaryOperator<?> binaryOperator, String castType) {
		
		if(binaryOperator.getParent() != null 
				&& !(binaryOperator.getParent() instanceof BinaryOperator)) {
			String source = binaryOperator.getOriginalSourceFragment().getSourceCode();
			
			source = Util.removeExplicitCast(source).trim();
			
			boolean hasParentheses = source.charAt(0) == '(' 
					&& source.charAt(source.length() - 1) == ')';
			
			if(hasParentheses) {
				if(binaryOperator.getType() != null) {
					String variableType = binaryOperator.getType().toString();
					if (checkNarrowingConversion(castType, variableType)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean hasModulusOperation(CtBinaryOperator<?> binaryOperator) {
		if(binaryOperator.getKind() == BinaryOperatorKind.MOD) {
			if(binaryOperator.getParent() != null &&
					!(binaryOperator.getParent() instanceof CtBinaryOperator)) {
				return true;
			}
		}
		
		return false;
	}

	private String extractNegativeLiteral(CtUnaryOperator<?> unary) {
		String result = "";
		
		Pattern pattern = Pattern.compile("(-\\s*\\d+)");
		Matcher matcher = pattern.matcher(unary.prettyprint());
		
		boolean hasTypeConversion = matcher.find();
		
		if(hasTypeConversion) {
			result = matcher.group(0);
		}
		
		return result;
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
	
	private boolean checkLiteralOutOfRange(String value, String castType) {
		
		try {
			switch (castType) {
			case BYTE:
				if(Double.valueOf(value) < Byte.MIN_VALUE || Double.valueOf(value) > Byte.MAX_VALUE) {
					return true;
				}
				break;
			case SHORT:
				if(Double.valueOf(value) < Short.MIN_VALUE || Double.valueOf(value) > Short.MAX_VALUE) {
					return true;
				}
				break;
			case INT:
				if(Double.valueOf(value) < Integer.MIN_VALUE || Double.valueOf(value) > Integer.MAX_VALUE) {
					return true;
				}
				break;
			case LONG:
				if(Double.valueOf(value) < Long.MIN_VALUE || Double.valueOf(value) > Long.MAX_VALUE) {
					return true;
				}
				break;
			case FLOAT:
				if(Double.valueOf(value) < Float.MIN_VALUE || Double.valueOf(value) > Float.MAX_VALUE) {
					return true;
				}
				break;
			case DOUBLE:
				if(Double.valueOf(value) < Double.MIN_VALUE || Double.valueOf(value) > Double.MAX_VALUE) {
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
