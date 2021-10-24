package br.ufc.mdcc.bohr.finder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.TypeFilter;

public class InfixOperatorPrecedenceFinder extends AbstractProcessor<CtClass<?>> {
	private List<Integer> atomLines = new ArrayList<Integer>();

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();
			
			TypeFilter<CtBinaryOperator<?>> binaryOprFilter = null;
			binaryOprFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);
			List<CtBinaryOperator<?>> elements = element.getElements(binaryOprFilter);
			
			for (CtBinaryOperator<?> binaryOpr : elements) {
				try {
					CtElement fullExpression = getHighLevelParent(binaryOpr);
					
					if (isCandidate(fullExpression)) {
						int lineNumber = fullExpression.getPosition().getLine();
						if(!atomLines.contains(Integer.valueOf(lineNumber))) {
							atomLines.add(lineNumber);
							String snippet = fullExpression.getOriginalSourceFragment().getSourceCode();
							Dataset.store(qualifiedName, new AoCInfo(AoC.IOP, lineNumber, snippet));
						}
					}
					
				} catch (SpoonException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	private CtElement getHighLevelParent(CtElement element) {
		if ((element.getParent() != null) && (element.getParent() instanceof CtBinaryOperator)) {
			return this.getHighLevelParent(element.getParent());
		} else {
			return element;
		}
	}

	private boolean isCandidate(CtElement expression) {

		if (hasCombinationOfArithmeticalOperators(expression)) {
			if(isMissingParenthesesInArithmeticalOperations(expression)) {
				return true;
			}
		}
		
		if (hasCombinationLogicalOperators(expression)) {
			if(isMissingParenthesesInLogicalOperations(expression)) {
				return true;
			}
		}

		return false;
	}

	private boolean hasCombinationOfArithmeticalOperators(CtElement expression) {
		TypeFilter<CtBinaryOperator<?>> binaryOprFilter = null;
		binaryOprFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);
		
		Map<String, Integer> differentOperatorsMap = new HashMap<String, Integer>();
		differentOperatorsMap.put("PLUS", 0);
		differentOperatorsMap.put("MINUS", 0);
		differentOperatorsMap.put("MOD", 0);
		differentOperatorsMap.put("MUL", 0);
		differentOperatorsMap.put("DIV", 0);

		for (CtBinaryOperator<?> binaryOpr : expression.getElements(binaryOprFilter)) {
			switch (binaryOpr.getKind()) {
			case PLUS:
				differentOperatorsMap.put("PLUS", differentOperatorsMap.get("PLUS") + 1);
				break;

			case MINUS:
				differentOperatorsMap.put("MINUS", differentOperatorsMap.get("MINUS") + 1);
				break;

			case MOD:
				differentOperatorsMap.put("MOD", differentOperatorsMap.get("MOD") + 1);
				break;

			case MUL:
				differentOperatorsMap.put("MUL", differentOperatorsMap.get("MUL") + 1);
				break;

			case DIV:
				differentOperatorsMap.put("DIV", differentOperatorsMap.get("DIV") + 1);
				break;

			default:
				break;
			}
		}

		int firstSetOfOperators = 0;

		if (differentOperatorsMap.get("PLUS") > 0) {
			firstSetOfOperators++;
		}

		if (differentOperatorsMap.get("MINUS") > 0) {
			firstSetOfOperators++;
		}

		int secondSetOfOperators = 0;

		if (differentOperatorsMap.get("MUL") > 0) {
			secondSetOfOperators++;
		}

		if (differentOperatorsMap.get("DIV") > 0) {
			secondSetOfOperators++;
		}

		if (differentOperatorsMap.get("MOD") > 0) {
			secondSetOfOperators++;
		}

		return (firstSetOfOperators > 0) && (secondSetOfOperators > 0);
	}
	
	private boolean isMissingParenthesesInArithmeticalOperations(CtElement expression) {
		TypeFilter<CtBinaryOperator<?>> binaryOprFilter = null;
		binaryOprFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);
		List<CtBinaryOperator<?>> elements = expression.getElements(binaryOprFilter);
		
		for (CtBinaryOperator<?> binaryOpr : elements) {
			if(binaryOpr.getKind() == BinaryOperatorKind.MUL 
					|| binaryOpr.getKind() == BinaryOperatorKind.DIV
					|| binaryOpr.getKind() == BinaryOperatorKind.MOD) {
				
				boolean binaryOprCondition = false;
				
				if(binaryOpr.getParent() instanceof CtBinaryOperator){
					CtBinaryOperator<?> binaryParent = (CtBinaryOperator<?>) binaryOpr.getParent();
					
					boolean condition = binaryParent.getKind() == BinaryOperatorKind.PLUS
							|| binaryParent.getKind() == BinaryOperatorKind.MINUS;
					
					if(condition && !hasStringConcatenation(binaryParent)){
						String binaryExpression = binaryOpr.getOriginalSourceFragment().getSourceCode();
						binaryOprCondition = !(binaryExpression.startsWith("(") && binaryExpression.endsWith(")"));
					}
				}
				
				if(binaryOprCondition) {
					return true;
				}		
			}
		}
		
		return false;
	}
	
	private boolean hasStringConcatenation(CtBinaryOperator<?> binaryParent) {
		if(binaryParent.getKind() == BinaryOperatorKind.PLUS) {
			String stringType = "java.lang.String";

			CtExpression<?> leftHandOperand = binaryParent.getLeftHandOperand();
			CtExpression<?> rightHandOperand = binaryParent.getRightHandOperand();
			if((leftHandOperand.getType() != null 
					&& leftHandOperand.getType().toString().equalsIgnoreCase(stringType))
					|| 
					(rightHandOperand.getType() != null 
						&& rightHandOperand.getType().toString().equalsIgnoreCase(stringType))) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasCombinationLogicalOperators(CtElement expression) {
		TypeFilter<CtBinaryOperator<?>> binaryOprFilter = null;
		binaryOprFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);
		
		Map<String, Integer> differentOperatorsMap = new HashMap<String, Integer>();
		differentOperatorsMap.put("AND", 0);
		differentOperatorsMap.put("OR", 0);

		for (CtBinaryOperator<?> binaryOpr : expression.getElements(binaryOprFilter)) {
			switch (binaryOpr.getKind()) {
			case AND:
				differentOperatorsMap.put("AND", differentOperatorsMap.get("AND") + 1);
				break;

			case OR:
				differentOperatorsMap.put("OR", differentOperatorsMap.get("OR") + 1);
				break;
				
			default:
				break;
			}
		}

		int andOperators = 0;

		if (differentOperatorsMap.get("AND") > 0) {
			andOperators++;
		}

		int orOperators = 0;

		if (differentOperatorsMap.get("OR") > 0) {
			orOperators++;
		}

		return (andOperators > 0) && (orOperators > 0);
	}
	
	private boolean isMissingParenthesesInLogicalOperations(CtElement expression) {
		TypeFilter<CtBinaryOperator<?>> binaryOprFilter = null;
		binaryOprFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);
		List<CtBinaryOperator<?>> elements = expression.getElements(binaryOprFilter);
		
		for (CtBinaryOperator<?> binaryOpr : elements) {
			if(binaryOpr.getKind() == BinaryOperatorKind.AND || binaryOpr.getKind() == BinaryOperatorKind.OR) {
				
				boolean binaryOprCondition = false;
				
				if(binaryOpr.getParent() instanceof CtBinaryOperator){
					
					boolean andCondition = binaryOpr.getKind() == BinaryOperatorKind.AND
							&& ((CtBinaryOperator<?>) binaryOpr.getParent()).getKind() == BinaryOperatorKind.OR;
					
					boolean orCondition = binaryOpr.getKind() == BinaryOperatorKind.OR
							&& ((CtBinaryOperator<?>) binaryOpr.getParent()).getKind() == BinaryOperatorKind.AND;
					
					if(andCondition || orCondition){
						String binaryExpression = binaryOpr.getOriginalSourceFragment().getSourceCode();
						binaryOprCondition = !(binaryExpression.startsWith("(") && binaryExpression.endsWith(")"));
					}
				}
				
				if(binaryOprCondition) {
					return true;
				}
				
			}
		}
		
		return false;
	}

}