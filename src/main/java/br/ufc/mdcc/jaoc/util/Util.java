package br.ufc.mdcc.jaoc.util;

import spoon.reflect.declaration.CtType;

public class Util {

	public static boolean isValid(CtType<?> element) {
		return element != null && element.getQualifiedName() != null && (element.isClass() || element.isInterface())
				&& !element.isShadow(); // && !element.isAnonymous() && !element.isLocalType()
	}
}
