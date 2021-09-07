package br.aoc.bohr.finder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.aoc.bohr.model.AoC;
import br.aoc.bohr.model.AoCInfo;
import br.aoc.bohr.model.Dataset;
import br.aoc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class TypeConversionFinder extends AbstractProcessor<CtClass<?>> {
	private static final String CHAR = "char";
	private static final String BYTE = "byte";
	private static final String SHORT = "short";
	private static final String INT = "int";
	private static final String LONG = "long";
	private static final String FLOAT = "float";
	private static final String DOUBLE = "double";

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			String snippet = "";
			for (CtLocalVariable<?> localVariable : element.getElements(new TypeFilter<CtLocalVariable<?>>(CtLocalVariable.class))) {
				if(hasTypeConversionAtom(localVariable)) {
					snippet = localVariable.prettyprint();
					int lineNumber = localVariable.getPosition().getEndLine();
					Dataset.store(qualifiedName, new AoCInfo(AoC.TPC, lineNumber, snippet));
				}
			}
			
			for (CtAssignment<?, ?> assignment : element.getElements(new TypeFilter<CtAssignment<?, ?>>(CtAssignment.class))) {
				if(hasTypeConversionAtom(assignment)) {
					snippet = assignment.prettyprint();
					int lineNumber = assignment.getPosition().getEndLine();
					Dataset.store(qualifiedName, new AoCInfo(AoC.TPC, lineNumber, snippet));
				}
			}
			
		}
		
	}
	
	private boolean hasTypeConversionAtom(CtLocalVariable<?> localVariable) {
		
		if(localVariable.getAssignment() != null ) {
			if(localVariable.getAssignment().getType() != null) {
				String sourceCode = "";
				String assignmentType = "";
				String assignedType = "";
				try {
					sourceCode = localVariable.prettyprint();
					assignmentType = localVariable.getAssignment().getType().toString();
					assignedType = localVariable.getType().toString();
				} catch (SpoonException e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
				
				if(checkByteConversions(sourceCode, assignmentType, assignedType)) {
					return true;
				}
				
				if(checkShortConversions(sourceCode, assignmentType, assignedType)) {
					return true;
				}
				
				if(checkIntConversions(sourceCode, assignmentType, assignedType)) {
					return true;
				}
				
				if(checkLongConversions(sourceCode, assignmentType, assignedType)) {
					return true;
				}
				
				if(checkFloatConversions(sourceCode, assignmentType, assignedType)) {
					return true;
				}
				
				if(checkCharConversions(sourceCode, assignmentType, assignedType)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean hasTypeConversionAtom(CtAssignment<?, ?> assignment) {
		
		if(assignment.getAssignment().getType() == null
				|| assignment.getAssigned().getType() == null) {
			return false;
		}
		
		String sourceCode = assignment.prettyprint();
		String assignmentType = assignment.getAssignment().getType().toString();
		String assignedType = assignment.getAssigned().getType().toString();
		
		if(checkByteConversions(sourceCode, assignmentType, assignedType)) {
			return true;
		}
		
		if(checkShortConversions(sourceCode, assignmentType, assignedType)) {
			return true;
		}
		
		if(checkIntConversions(sourceCode, assignmentType, assignedType)) {
			return true;
		}
		
		if(checkLongConversions(sourceCode, assignmentType, assignedType)) {
			return true;
		}
		
		if(checkFloatConversions(sourceCode, assignmentType, assignedType)) {
			return true;
		}
		
		if(checkCharConversions(sourceCode, assignmentType, assignedType)) {
			return true;
		}
		
		return false;
	}
	
	private boolean checkByteConversions(String sourceCode, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(SHORT)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(sourceCode, BYTE);
		}
		
		if(assignmentType.equalsIgnoreCase(INT)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(sourceCode, BYTE);
		}

		if(assignmentType.equalsIgnoreCase(LONG)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(sourceCode, BYTE);
		}
		
		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(sourceCode, BYTE);
		}

		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(sourceCode, BYTE);
		}

		return false;
	}
	
	private boolean checkShortConversions(String sourceCode, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(INT)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(sourceCode, SHORT);
		}

		if(assignmentType.equalsIgnoreCase(LONG)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(sourceCode, SHORT);
		}

		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(sourceCode, SHORT);
		}

		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(sourceCode, SHORT);
		}
		
		return false;
	}
	
	private boolean checkIntConversions(String sourceCode, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(LONG)
				&& assignedType.equalsIgnoreCase(INT)) {
			return hasTypeConversionProblem(sourceCode, INT);
		}
		
		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(INT)) {
			return hasTypeConversionProblem(sourceCode, INT);
		}
		
		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(INT)) {
			return hasTypeConversionProblem(sourceCode, INT);
		}
		
		return false;
	}
	
	private boolean checkLongConversions(String sourceCode, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(LONG)) {
			return hasTypeConversionProblem(sourceCode, LONG);
		}
		
		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(LONG)) {
			return hasTypeConversionProblem(sourceCode, LONG);
		}
		
		return false;
	}

	private boolean checkFloatConversions(String sourceCode, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(FLOAT)) {
			return hasTypeConversionProblem(sourceCode, FLOAT);
		}
		
		return false;
	}
	
	private boolean checkCharConversions(String sourceCode, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(SHORT)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasCharConversionProblem(sourceCode, CHAR);
		}
		
		if(assignmentType.equalsIgnoreCase(INT)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasCharConversionProblem(sourceCode, CHAR);
		}

		if(assignmentType.equalsIgnoreCase(LONG)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasCharConversionProblem(sourceCode, CHAR);
		}
		
		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasCharConversionProblem(sourceCode, CHAR);
		}

		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasCharConversionProblem(sourceCode, CHAR);
		}
		
		return false;
	}
	
	private boolean hasTypeConversionProblem(String statement, String type) {
		Pattern pattern = Pattern.compile("\\((\\s*" + type +"\\s*)\\)");
		Matcher matcher = pattern.matcher(statement);
		boolean hasTypeConversion = matcher.find();

		pattern = Pattern.compile("\\(\\s*[\\d\\w]+\\s*%\\s*[\\d\\w]*\\s*\\)");
		matcher = pattern.matcher(statement);
		boolean hasModulusOperation = matcher.find();
		
		String javaAPIUsageString = "Math.";
		if(hasTypeConversion && !hasModulusOperation 
				&& !statement.contains(javaAPIUsageString)) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasCharConversionProblem(String statement, String type) {
		String javaAPIUsageString = "Character.forDigit";
		
		if(!statement.contains(javaAPIUsageString)) {
			return true;
		}
		
		return false;
	}
	
}
