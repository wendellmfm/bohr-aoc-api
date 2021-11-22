package br.ufc.mdcc.bohr.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class TypeConversionFinder extends AbstractProcessor<CtType<?>> {
	private static final String CHAR = "char";
	private static final String BYTE = "byte";
	private static final String SHORT = "short";
	private static final String INT = "int";
	private static final String LONG = "long";
	private static final String FLOAT = "float";
	private static final String DOUBLE = "double";

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			String snippet = "";
			
			for (CtStatement statement : element.getElements(new TypeFilter<CtStatement>(CtStatement.class))) {
				if(hasTypeConversionAtom(statement)) {
					snippet = statement.prettyprint();
					int lineNumber = statement.getPosition().getLine();
					Dataset.store(qualifiedName, new AoCInfo(AoC.TP, lineNumber, snippet));
				}
			}
			
			for (CtMethod<?> method : element.getElements(new TypeFilter<CtMethod<?>>(CtMethod.class))) {
				
				for (CtReturn<?> rtrn : method.getElements(new TypeFilter<CtReturn<?>>(CtReturn.class))) {
					
					if(rtrn.getReturnedExpression() != null
							&& rtrn.getReturnedExpression().getType() != null) {
						String rtrnExpressionType = rtrn.getReturnedExpression().getType().toString();
						String methodType = method.getType().toString();
						
						boolean hasTypeConversionAtom = checkConversions(rtrn, rtrnExpressionType, methodType);
						if(hasTypeConversionAtom) {
							snippet = rtrn.prettyprint();
							int lineNumber = rtrn.getPosition().getLine();
							Dataset.store(qualifiedName, new AoCInfo(AoC.TP, lineNumber, snippet));
						}
					}
				}
			}
		}	
	}
	
	private boolean hasTypeConversionAtom(CtStatement statement) {
		String assignmentType = "";
		String assignedType = "";

		if(statement instanceof CtLocalVariable) {
			CtLocalVariable<?> localVariable = (CtLocalVariable<?>) statement;
			
			if(localVariable.getAssignment() != null ) {
				if(localVariable.getAssignment().getType() != null) {
					
					assignmentType = localVariable.getAssignment().getType().toString();
					assignedType = localVariable.getType().toString();
				}
			}
		} else if (statement instanceof CtAssignment) {
			CtAssignment<?, ?> assignment = (CtAssignment<?, ?>) statement;
			
			if(assignment.getAssignment().getType() != null
					&& assignment.getAssigned().getType() != null) {
				
				assignmentType = assignment.getAssignment().getType().toString();
				assignedType = assignment.getAssigned().getType().toString();
			}
		}
		
		boolean result = checkConversions(statement, assignmentType, assignedType);
		
		return result;
	}

	private boolean checkConversions(CtStatement statement, String assignmentType, String assignedType) {
		boolean result = false;
		switch (assignedType) {
			case BYTE:
				result = checkByteConversions(statement, assignmentType, assignedType);
				break;
			case SHORT:
				result = checkShortConversions(statement, assignmentType, assignedType);
				break;
			case INT:
				result = checkIntConversions(statement, assignmentType, assignedType);
				break;
			case LONG:
				result = checkLongConversions(statement, assignmentType, assignedType);
				break;
			case FLOAT:
				result = checkFloatConversions(statement, assignmentType, assignedType);
				break;
			case CHAR:
				result = checkCharConversions(statement, assignmentType, assignedType);
				break;
	
			default:
				break;
		}
		return result;
	}
	
	private boolean checkByteConversions(CtStatement statement, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(SHORT)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(statement, BYTE);
		}
		
		if(assignmentType.equalsIgnoreCase(CHAR)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(statement, BYTE);
		}
		
		if(assignmentType.equalsIgnoreCase(INT)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(statement, BYTE);
		}

		if(assignmentType.equalsIgnoreCase(LONG)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(statement, BYTE);
		}
		
		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(statement, BYTE);
		}

		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(BYTE)) {
			return hasTypeConversionProblem(statement, BYTE);
		}

		return false;
	}
	
	private boolean checkShortConversions(CtStatement statement, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(CHAR)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(statement, SHORT);
		}
		
		if(assignmentType.equalsIgnoreCase(INT)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(statement, SHORT);
		}

		if(assignmentType.equalsIgnoreCase(LONG)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(statement, SHORT);
		}

		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(statement, SHORT);
		}

		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(SHORT)) {
			return hasTypeConversionProblem(statement, SHORT);
		}
		
		return false;
	}
	
	private boolean checkIntConversions(CtStatement statement, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(LONG)
				&& assignedType.equalsIgnoreCase(INT)) {
			return hasTypeConversionProblem(statement, INT);
		}
		
		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(INT)) {
			return hasTypeConversionProblem(statement, INT);
		}
		
		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(INT)) {
			return hasTypeConversionProblem(statement, INT);
		}
		
		return false;
	}
	
	private boolean checkLongConversions(CtStatement statement, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(LONG)) {
			return hasTypeConversionProblem(statement, LONG);
		}
		
		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(LONG)) {
			return hasTypeConversionProblem(statement, LONG);
		}
		
		return false;
	}

	private boolean checkFloatConversions(CtStatement statement, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(FLOAT)) {
			return hasTypeConversionProblem(statement, FLOAT);
		}
		
		return false;
	}
	
	private boolean checkCharConversions(CtStatement statement, String assignmentType, String assignedType) {
		
		if(assignmentType.equalsIgnoreCase(SHORT)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasTypeConversionProblem(statement, CHAR);
		}
		
		if(assignmentType.equalsIgnoreCase(INT)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasTypeConversionProblem(statement, CHAR);
		}

		if(assignmentType.equalsIgnoreCase(LONG)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasTypeConversionProblem(statement, CHAR);
		}
		
		if(assignmentType.equalsIgnoreCase(FLOAT)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasTypeConversionProblem(statement, CHAR);
		}

		if(assignmentType.equalsIgnoreCase(DOUBLE)
				&& assignedType.equalsIgnoreCase(CHAR)) {
			return hasTypeConversionProblem(statement, CHAR);
		}
		
		return false;
	}
	
	private boolean hasTypeConversionProblem(CtStatement statement, String type) {
		String source = statement.prettyprint();
		
		if(hasExplicitTypeConversion(source, type)) {
			
			if(!hasModulusOperation(source)) {
				
				if(!hasMethodInvocation(statement)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean hasExplicitTypeConversion(String source, String type) {
		Pattern pattern = Pattern.compile("\\((\\s*" + type +"\\s*)\\)");
		Matcher matcher = pattern.matcher(source);
		boolean hasTypeConversion = matcher.find();
		
		if(hasTypeConversion) {
			return true;
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
	
	private boolean hasMethodInvocation(CtStatement statement) {
		List<CtInvocation<?>> methods = new ArrayList<>();
		TypeFilter<CtInvocation<?>> filter = new TypeFilter<CtInvocation<?>>(CtInvocation.class);
		
		methods = statement.getElements(filter);
		
		if(!methods.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
}
