package br.ufc.mdcc.jaoc.searcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.ufc.mdcc.jaoc.model.AoC;
import br.ufc.mdcc.jaoc.model.AoCInfo;
import br.ufc.mdcc.jaoc.model.Dataset;
import br.ufc.mdcc.jaoc.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.TypeFilter;

public class InfixOperatorPrecedenceSearcher extends AbstractProcessor<CtClass<?>> {

	private Set<CtElement> fullExpressions = new HashSet<CtElement>();

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtExpression<?>> expressionFilter = new TypeFilter<CtExpression<?>>(CtExpression.class);

			for (CtExpression<?> expression : element.getElements(expressionFilter)) {
				if (isCandidate(expression)) {
					int lineNumber = expression.getPosition().getEndLine();
					String snippet = expression.prettyprint();
					snippet = expression.getOriginalSourceFragment().getSourceCode();
					Dataset.store(qualifiedName, new AoCInfo(AoC.IOP, lineNumber, snippet));
				}
			}
		}
	}

	private boolean isCandidate(CtExpression<?> expression) {
		if (!fullExpressions.contains(getHighLevelParent(expression))) {
			fullExpressions.add(expression);
			if (hasCombinationOfDifferentOperators(expression)) {
				return true;
			}
		}
		return false;
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

		return (firstSetOfOperators > 1) && (secondSetOfOperators > 1);
	}

	private CtExpression<?> getHighLevelParent(CtExpression<?> expression) {
		//System.out.println("Expression type: " + expression.getType());
		if ((expression.getParent() != null) && (expression.getParent() instanceof CtExpression<?>)) {
			return this.getHighLevelParent((CtExpression<?>) expression.getParent());
		} else {
			return expression;
		}
	}

}