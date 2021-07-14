package br.ufc.mdcc.jaoc.searcher;

import br.ufc.mdcc.jaoc.model.AoC;
import br.ufc.mdcc.jaoc.model.AoCInfo;
import br.ufc.mdcc.jaoc.model.Dataset;
import br.ufc.mdcc.jaoc.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class LogicAsControlFlowSearcher extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtBinaryOperator<?>> binaryOperatorFilter = new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class);

			for (CtBinaryOperator<?> operator : element.getElements(binaryOperatorFilter)) {
				
				if(operator.getKind() == BinaryOperatorKind.AND || operator.getKind() == BinaryOperatorKind.OR) {
					
					CtExpression<?> leftHandOperand = operator.getLeftHandOperand();
					if(!leftHandOperand.getElements(binaryOperatorFilter).isEmpty()) {
						int lineNumber = operator.getPosition().getEndLine();
						String snippet = operator.getParent().prettyprint();
						
						Dataset.store(qualifiedName, new AoCInfo(AoC.LaCTRF, lineNumber, snippet));
					}
				}
			}
		}
	}
}