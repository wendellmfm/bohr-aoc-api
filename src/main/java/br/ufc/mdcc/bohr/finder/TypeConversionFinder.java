package br.ufc.mdcc.bohr.finder;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class TypeConversionFinder extends AbstractProcessor<CtType<?>> {

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			
			String snippet = "";

			for (CtVariableRead<?> variableRead : element.getElements(new TypeFilter<CtVariableRead<?>>(CtVariableRead.class))) {
				if(hasTypeConversionAtom(variableRead)) {
					int lineNumber = variableRead.getPosition().getLine();
					snippet = variableRead.getOriginalSourceFragment().getSourceCode();
					Dataset.store(qualifiedName, new AoCInfo(AoC.TC, lineNumber, snippet));
				}
			}
			
			for (CtBinaryOperator<?> binaryOperator : element.getElements(new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class))) {
				CtElement parent = binaryOperator.getParent();
				if(parent != null && !(parent instanceof CtBinaryOperator)) {
					if(hasTypeConversionAtom(binaryOperator)) {
						int lineNumber = binaryOperator.getPosition().getLine();
						snippet = binaryOperator.getOriginalSourceFragment().getSourceCode();
						Dataset.store(qualifiedName, new AoCInfo(AoC.TC, lineNumber, snippet));
					}
				}
			}
			
			for (CtUnaryOperator<?> unaryOperator : element.getElements(new TypeFilter<CtUnaryOperator<?>>(CtUnaryOperator.class))) {
				if(hasTypeConversionAtom(unaryOperator)) {
					int lineNumber = unaryOperator.getPosition().getLine();
					snippet = unaryOperator.getOriginalSourceFragment().getSourceCode();
					Dataset.store(qualifiedName, new AoCInfo(AoC.TC, lineNumber, snippet));
				}
			}
		}	
	}
	
	private boolean hasTypeConversionAtom(CtExpression<?> expression) {
		boolean hasExplicitTypeConversion = hasExplicitTypeConversion(expression);
		boolean hasModulusOperation = hasModulusOperation(expression.prettyprint());
		
		if(hasExplicitTypeConversion
				&& !hasModulusOperation) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasExplicitTypeConversion(CtExpression<?> expression) {
		Pattern pattern = Pattern.compile("^\\(\\((\\s*(byte|short|int|long|float|double|char)\\s*)\\)");
		Matcher matcher = pattern.matcher(expression.prettyprint());
		boolean hasTypeConversion = matcher.find();
		
		if(hasTypeConversion) {
			if(expression instanceof CtVariableRead) {
				CtVariableRead<?> variableRead = (CtVariableRead<?>) expression;
				String variableType = variableRead.getType().toString();
				if (checkNarrowingConversion(matcher.group(1), variableType)) {
					return true;
				}
			} else if(expression instanceof CtUnaryOperator) {
				CtUnaryOperator<?> unaryOperator = (CtUnaryOperator<?>) expression;
				if(unaryOperator.getType() != null) {
					String variableType = unaryOperator.getType().toString();
					if (checkNarrowingConversion(matcher.group(1), variableType)) {
						return true;
					}
				}
			} else if(expression instanceof CtBinaryOperator) {
				return true;
			}
		}
		
		return false;
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
