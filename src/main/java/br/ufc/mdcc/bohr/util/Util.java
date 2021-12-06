package br.ufc.mdcc.bohr.util;

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
	
	public static String removeExplicitCast(String expression) {
		expression = expression.replaceAll(EXPLICIT_CAST_PATTERN, "");
		
		return expression;
	}
	
}
