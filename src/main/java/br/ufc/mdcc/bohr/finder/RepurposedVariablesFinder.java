package br.ufc.mdcc.bohr.finder;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtAssignmentImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtUnaryOperatorImpl;

public class RepurposedVariablesFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtFor> filter = new TypeFilter<CtFor>(CtFor.class);
			for (CtFor forLoop : element.getElements(filter)) {
				
				if(forLoop.getParent().getParent() instanceof CtFor) {
					
					CtFor forParent = (CtFor) forLoop.getParent().getParent();
					
					CtStatement forParentInit = forParent.getForInit().get(0);
					CtStatement forLoopUpdate = forLoop.getForUpdate().get(0);
					
					String forParentInitVariableString = "";
					if(forParentInit instanceof CtLocalVariableImpl) {
						CtLocalVariableImpl<?> forParentInitVariable = (CtLocalVariableImpl<?>) forParentInit;
						forParentInitVariableString = forParentInitVariable.getSimpleName();
					} else if(forParentInit instanceof CtAssignmentImpl){
						CtAssignmentImpl<?, ?> forParentInitAssignment = (CtAssignmentImpl<?, ?>) forParentInit;
						forParentInitVariableString = forParentInitAssignment.getAssigned().prettyprint();
					}
					
					CtUnaryOperatorImpl<?> forLoopUpdateVariable = (CtUnaryOperatorImpl<?>) forLoopUpdate;
					
					if(forParentInitVariableString.equals(forLoopUpdateVariable.getOperand().prettyprint())) {
						int lineNumber = forParent.getPosition().getLine();
						String snippet = forParent.prettyprint();
						Dataset.store(qualifiedName, new AoCInfo(AoC.RVar, lineNumber, snippet));
					}
				}
			}
		}
	}
}