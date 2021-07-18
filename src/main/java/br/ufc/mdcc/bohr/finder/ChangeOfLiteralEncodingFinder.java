package br.ufc.mdcc.bohr.finder;

import java.util.Arrays;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class ChangeOfLiteralEncodingFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			for (CtLiteral<?> literal : element.getElements(new TypeFilter<CtLiteral<?>>(CtLiteral.class))) {
				if ((literal.getParent() != null) && !(literal.getParent() instanceof CtLiteral<?>)
						&& (literal.getParent() instanceof CtAssignment)
						|| (literal.getParent() instanceof CtLocalVariable)) {

					if(hasChangeOfLiteralEncoding(literal.prettyprint())) {
						int lineNumber = literal.getPosition().getEndLine();
						String snippet = literal.getParent().getOriginalSourceFragment().getSourceCode();
						Dataset.store(qualifiedName, new AoCInfo(AoC.CoLE, lineNumber, snippet));
					}
				}
			}
			
			for (CtBinaryOperator<?> operator : element.getElements(new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class))) {
				if(operator.getKind() == BinaryOperatorKind.BITAND) {
					if(hasChangeOfLiteralEncoding(operator)) {
						int lineNumber = operator.getPosition().getEndLine();
						String snippet = operator.getParent().getOriginalSourceFragment().getSourceCode();
						Dataset.store(qualifiedName, new AoCInfo(AoC.CoLE, lineNumber, snippet));
					}
				}
			}
		}
	}
	
	private boolean hasChangeOfLiteralEncoding(String literal) {
		
		if(literal.length() > 1 && literal.startsWith("0") && literal.matches("[0-9]+")) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasChangeOfLiteralEncoding(CtBinaryOperator<?> operator) {
		CtExpression<?> leftHandOperand = operator.getLeftHandOperand();
		CtExpression<?> rightHandOperand = operator.getRightHandOperand();
		
		String[] binaryLiterals = {"byte", "short", "int", "long"};
		
		if(leftHandOperand.getType() != null) {
			if(Arrays.asList(binaryLiterals).contains(leftHandOperand.getType().toString())
					&& Arrays.asList(binaryLiterals).contains(operator.getRightHandOperand().getType().toString())) {
				
				String leftHandOperandString = leftHandOperand.prettyprint();
				String rightHandOperandString = rightHandOperand.prettyprint();
				
				String binaryPattern = "-?0[bB][01][01]+";
				
				if(leftHandOperandString.matches(binaryPattern) && rightHandOperandString.matches(binaryPattern)) {
					return false;
				} else {
					return true;
				}
			}
		}
		
		return false;
	}
}
