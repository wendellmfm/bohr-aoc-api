package br.ufc.mdcc.bohr.finder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class TypeConversionFinder extends AbstractProcessor<CtClass<?>> {

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
	
	private static boolean hasTypeConversionAtom(CtLocalVariable<?> localVariable) {
		
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
				
				return checkTypesConversion(sourceCode, assignmentType, assignedType);				
			}
		}
		
		return false;
	}
	
	private static boolean hasTypeConversionAtom(CtAssignment<?, ?> assignment) {
		
		if(assignment.getAssignment().getType() == null
				|| assignment.getAssigned().getType() == null) {
			return false;
		}
		
		String sourceCode = assignment.prettyprint();
		String assignmentType = assignment.getAssignment().getType().toString();
		String assignedType = assignment.getAssigned().getType().toString();
		
		if(checkTypesConversion(sourceCode, assignmentType, assignedType)) {
			return true;
		}
		
		return false;
	}

	private static boolean checkTypesConversion(String sourceCode, String assignmentType, String assignedType) {
		if(assignmentType.equalsIgnoreCase("float")
				&& assignedType.equalsIgnoreCase("int")) {
			return hasIntTypeConversionProblem(sourceCode) || hasByteTypeConversionProblem(sourceCode);
		}
		
		if(assignmentType.equalsIgnoreCase("int")
				&& assignedType.equalsIgnoreCase("byte")) {
			return hasIntTypeConversionProblem(sourceCode) || hasByteTypeConversionProblem(sourceCode);
		}
		
		return false;
	}
	
	private static boolean hasIntTypeConversionProblem(String statement) {
		Pattern pattern = Pattern.compile("\\((\\s*int\\s*)\\)");
		Matcher matcher = pattern.matcher(statement); 
		
		String javaAPIUsageString = "Math.floor";
		if(matcher.find() && !statement.contains(javaAPIUsageString)) {
			return true;
		}
		
		return false;
	}
	
	private static boolean hasByteTypeConversionProblem(String statement) {
		Pattern pattern = Pattern.compile("\\((\\s*byte\\s*)\\)");
		Matcher matcher = pattern.matcher(statement);
		boolean hasByteCasting = matcher.find();
				
		pattern = Pattern.compile("\\(\\s*[\\d\\w]+\\s*%\\s*[\\d\\w]*\\s*\\)");
		matcher = pattern.matcher(statement);
		boolean hasModulusOperation = matcher.find();
		
		if(hasByteCasting && !hasModulusOperation) {
			return true;
		}
		
		return false;
	}

}
