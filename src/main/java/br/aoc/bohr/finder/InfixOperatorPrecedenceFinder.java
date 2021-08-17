package br.aoc.bohr.finder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.aoc.bohr.model.AoC;
import br.aoc.bohr.model.AoCInfo;
import br.aoc.bohr.model.Dataset;
import br.aoc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.TypeFilter;

public class InfixOperatorPrecedenceFinder extends AbstractProcessor<CtClass<?>> {

	private Set<CtElement> fullExpressions = new HashSet<CtElement>();

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtExpression<?>> expressionFilter = new TypeFilter<CtExpression<?>>(CtExpression.class);

			for (CtExpression<?> expression : element.getElements(expressionFilter)) {
				if (isCandidate(expression)) {
					int lineNumber = expression.getPosition().getEndLine();
					String snippet = expression.getOriginalSourceFragment().getSourceCode();
					Dataset.store(qualifiedName, new AoCInfo(AoC.IOP, lineNumber, snippet));
				}
			}
		}
	}

	private boolean isCandidate(CtExpression<?> expression) {
		if (!fullExpressions.contains(getHighLevelParent(expression))) {
			fullExpressions.add(expression);
			if (hasCombinationOfDifferentOperators(expression)) {
				if(hasParenthesesProblem(expression)) {
					return true;
				}
			}
		}

		return false;
	}
	
	private boolean hasParenthesesProblem(CtExpression<?> expression) {
		ArrayList<Boolean> results = new ArrayList<>();

		TypeFilter<CtBinaryOperator<?>> binaryOprFilter = null;
		binaryOprFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);
		List<CtBinaryOperator<?>> elements = expression.getElements(binaryOprFilter);
		
		String leftHandOperand = "";
		String rightHandOperand = "";
		for (CtBinaryOperator<?> binaryOpr : elements) {
			leftHandOperand = binaryOpr.getLeftHandOperand().getOriginalSourceFragment().getSourceCode();
			rightHandOperand = binaryOpr.getRightHandOperand().getOriginalSourceFragment().getSourceCode();
			
			if(binaryOpr.getKind() == BinaryOperatorKind.MUL || (binaryOpr.getKind() == BinaryOperatorKind.DIV)) {
				String binaryExpression = binaryOpr.getOriginalSourceFragment().getSourceCode();
				if(binaryExpression.startsWith("(") && binaryExpression.endsWith(")")) {
					results.add(Boolean.FALSE);
				} else if((leftHandOperand.startsWith("(") && leftHandOperand.endsWith(")")) 
						|| (rightHandOperand.startsWith("(") && rightHandOperand.endsWith(")"))) {
					results.add(Boolean.FALSE);
				} else {
					results.add(Boolean.TRUE);
				}
			}
		}
		
		return results.contains(Boolean.TRUE);
	}

	private boolean hasCombinationOfDifferentOperators(CtExpression<?> expression) {
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

	private CtExpression<?> getHighLevelParent(CtExpression<?> expression) {
		if ((expression.getParent() != null) && (expression.getParent() instanceof CtExpression<?>)) {
			return this.getHighLevelParent((CtExpression<?>) expression.getParent());
		} else {
			return expression;
		}
	}

}