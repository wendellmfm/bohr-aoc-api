package br.ufc.mdcc.bohr.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spoon.reflect.code.CtNewClass;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;

public class Util {
	public static final String EXPLICIT_CAST_PATTERN = "\\((\\s*(byte|short|int|long|float|double|char)\\s*)\\)";
	
	public static boolean isValid(CtType<?> element) {
		return element != null && element.getQualifiedName() != null
				&& (element.isClass() || element.isInterface())
				&& !element.isShadow() // && !element.isAnonymous() && !element.isLocalType()
				&& !(element.getParent() instanceof CtClass<?>)
				&& !(element.getParent() instanceof CtNewClass<?>);
	}
	
	public static boolean checkNoDecimalNumberNotation(String expression) {
		String binaryPattern = "0[bB][01]+";
		String octalPattern = "0[0-9]+";
		String hexPattern = "0[xX][0-9a-fA-F]+";
		
		boolean binaryNotation = checkNumberNotation(expression, binaryPattern);
		boolean octalNotation = checkNumberNotation(expression, octalPattern);
		boolean hexNotation = checkNumberNotation(expression, hexPattern);
		
		if(binaryNotation 
				|| octalNotation
				|| hexNotation) {
			return true;
		} 
		
		return false;
	}
	
	private static boolean checkNumberNotation(String expression, String notationPattern) {
		Pattern pattern = Pattern.compile(notationPattern);
		Matcher matcher = pattern.matcher(expression);
		
		if(matcher.find()) {
			return true;
		}
		
		return false;
	}
	
	public static String removeExplicitCast(String expression) {
		expression = expression.replaceAll(EXPLICIT_CAST_PATTERN, "");
		
		return expression;
	}
	
}
