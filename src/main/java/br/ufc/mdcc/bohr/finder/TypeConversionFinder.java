package br.ufc.mdcc.bohr.finder;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class TypeConversionFinder extends AbstractProcessor<CtType<?>> {

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			
			String snippet = "";
			
			try {
				
				for (CtExpression<?> variableRead : element.getElements(new TypeFilter<CtExpression<?>>(CtExpression.class))) {
					if(hasTypeConversionAtom(variableRead)) {
						int lineNumber = variableRead.getPosition().getLine();
						snippet = variableRead.getOriginalSourceFragment().getSourceCode();
						Dataset.save(qualifiedName, new AoCInfo(AoC.TC, lineNumber, snippet));
					}
				}
				
			} catch (SpoonException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}	
	}
	
	private boolean hasTypeConversionAtom(CtExpression<?> expression) {
		boolean hasTypeConversion = hasTypeConversion(expression);
		boolean hasModulusOperation = hasModulusOperation(expression.prettyprint());
		
		if(hasTypeConversion
				&& !hasModulusOperation) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasTypeConversion(CtExpression<?> expression) {
		String castType = getExplicitTypeConversion(expression);
		String variableType = "";
		
		if(castType != null) {
			
			if(expression instanceof CtBinaryOperator) {
				CtBinaryOperator<?> binaryOperator = (CtBinaryOperator<?>) expression;

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
			} else if(!(expression instanceof CtInvocation)) {
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
	
	private String getExplicitTypeConversion(CtExpression<?> expression) {
		String castType = null;

		Pattern pattern = Pattern.compile(Util.EXPLICIT_CAST_PATTERN);
		Matcher matcher = pattern.matcher(expression.prettyprint());
		
		if(matcher.find()) {
			castType = matcher.group(1);
		}
		
		return castType;
	}
	
	private boolean hasModulusOperation(String source) {
		Pattern pattern = Pattern.compile("\\(\\s*[\\d\\w]+\\s*%\\s*[\\d\\w]*\\s*\\)");
		Matcher matcher = pattern.matcher(source);
		boolean hasModulusOperation = matcher.find();
		
		if(hasModulusOperation) {
			return true;
		}
		
		return false;
	}
	
	private boolean checkNarrowingConversion (String assignmentType, String assignedType) {
		final String CHAR = "char";
		final String BYTE = "byte";
		final String SHORT = "short";
		final String INT = "int";
		final String LONG = "long";
		final String FLOAT = "float";
		final String DOUBLE = "double";
		
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
	
}
