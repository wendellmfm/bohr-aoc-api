package br.ufc.mdcc.bohr.finder;


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
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class ChangeOfLiteralEncodingFinder extends AbstractProcessor<CtType<?>> {

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			for (CtLiteral<?> literal : element.getElements(new TypeFilter<CtLiteral<?>>(CtLiteral.class))) {
				if (literal.getParent() != null) {
					if((literal.getParent() instanceof CtAssignment)
							|| literal.getParent() instanceof CtLocalVariable) {
						
						if(hasOctalChangeOfLiteralEncoding(literal.prettyprint())) {
							int lineNumber = literal.getPosition().getEndLine();
							String snippet = literal.getParent().prettyprint();
							Dataset.save(qualifiedName, new AoCInfo(AoC.CLE, lineNumber, snippet));
						}
					}
				}
			}
			
			for (CtBinaryOperator<?> operator : element.getElements(new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class))) {
				BinaryOperatorKind operatorKind = operator.getKind();
				if(operatorKind == BinaryOperatorKind.BITAND
						|| operatorKind == BinaryOperatorKind.BITOR
						|| operatorKind == BinaryOperatorKind.BITXOR) {
					if(hasChangeOfLiteralEncoding(operator)) {
						int lineNumber = operator.getPosition().getEndLine();
						String snippet = operator.getParent().prettyprint();
						Dataset.save(qualifiedName, new AoCInfo(AoC.CLE, lineNumber, snippet));
					}
				}
			}
		}
	}
	
	private boolean hasOctalChangeOfLiteralEncoding(String literal) {
		
		if(literal.length() > 1 && literal.matches("0[0-9]+")) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasChangeOfLiteralEncoding(CtBinaryOperator<?> operator) {
		CtExpression<?> leftHandOperand = operator.getLeftHandOperand();
		CtExpression<?> rightHandOperand = operator.getRightHandOperand();
		
		if(hasLiteralBitwiseOperation(leftHandOperand) 
				|| hasLiteralBitwiseOperation(rightHandOperand)) {		
			return true;
		}
		
		return false;
	}
	
	private boolean hasLiteralBitwiseOperation(CtExpression<?> operand) {
		if(operand instanceof CtLiteral) {
			String operandString = operand.prettyprint();
			
			if(Util.checkNoDecimalNumberNotation(operandString)) {
				return false;
			} else {
				return true;
			}
		}
		
		return false;
	}
		
}
