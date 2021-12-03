package br.ufc.mdcc.bohr.finder;

import java.util.List;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtAssignmentImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtOperatorAssignmentImpl;
import spoon.support.reflect.code.CtUnaryOperatorImpl;

public class RepurposedVariablesFinder extends AbstractProcessor<CtType<?>> {

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			getLoopCase(element, qualifiedName);

			getForUpdateCase(element, qualifiedName);
		}
	}

	private void getForUpdateCase(CtType<?> element, String qualifiedName) {
		TypeFilter<CtFor> filter = new TypeFilter<CtFor>(CtFor.class);
		for (CtFor forLoop : element.getElements(filter)) {
			
			if(forLoop.getParent().getParent() instanceof CtFor) {
				
				CtFor forParent = (CtFor) forLoop.getParent().getParent();
				
				if(!forParent.getForInit().isEmpty() && !forLoop.getForUpdate().isEmpty()) {
					
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
					
					if(forLoopUpdate instanceof CtUnaryOperatorImpl) {
						CtUnaryOperatorImpl<?> forLoopUpdateVariable = (CtUnaryOperatorImpl<?>) forLoopUpdate;
						
						if(forParentInitVariableString.equals(forLoopUpdateVariable.getOperand().prettyprint())) {
							save(qualifiedName, forParent);
						}
						
					} else if(forLoopUpdate instanceof CtOperatorAssignmentImpl<?, ?>) {
						CtOperatorAssignmentImpl<?, ?> forLoopUpdateVariable = (CtOperatorAssignmentImpl<?, ?>) forLoopUpdate;
						
						if(forParentInitVariableString.equals(forLoopUpdateVariable.getAssigned().prettyprint())) {
							save(qualifiedName, forParent);
						}
					}
					
				}
			}
		}
	}
	
	private void getLoopCase(CtType<?> element, String qualifiedName) {
		TypeFilter<CtLoop> whileFilter = new TypeFilter<CtLoop>(CtLoop.class);
		TypeFilter<CtArrayRead<?>> arrayReadFilter = new TypeFilter<CtArrayRead<?>>(CtArrayRead.class);
		TypeFilter<CtArrayWrite<?>> arrayWriteFilter = new TypeFilter<CtArrayWrite<?>>(CtArrayWrite.class);
		
		for (CtLoop ctLoop : element.getElements(whileFilter)) {
			
			CtExpression<?> loopingExpression = null;
			if(ctLoop instanceof CtFor) {
				loopingExpression = ((CtFor) ctLoop).getExpression();
			}else if(ctLoop instanceof CtWhile) {
				loopingExpression = ((CtWhile) ctLoop).getLoopingExpression();
			}
			
			if(loopingExpression != null) {
				List<CtArrayRead<?>> arraysRead = loopingExpression.getElements(arrayReadFilter);
				
				if(!arraysRead.isEmpty()) {
					for (CtArrayRead<?> read : arraysRead) {
						CtStatement body = ctLoop.getBody();
						
						if(body != null) {
							List<CtArrayWrite<?>> arraysWrite = body.getElements(arrayWriteFilter);
							if(!arraysWrite.isEmpty()) {
								for (CtArrayWrite<?> write : arraysWrite) {
									if(write.getTarget().prettyprint().equalsIgnoreCase(read.getTarget().prettyprint())) {
										save(qualifiedName, ctLoop);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void save(String qualifiedName, CtLoop loop) {
		int lineNumber = loop.getPosition().getLine();
		String snippet = loop.prettyprint();
		Dataset.save(qualifiedName, new AoCInfo(AoC.RV, lineNumber, snippet));
	}
	
}