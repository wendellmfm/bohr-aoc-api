package br.ufc.mdcc.bohr.finder;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.SpoonException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class PreAndPostIncrementDecrementFinder extends AbstractProcessor<CtType<?>> {

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtUnaryOperator<?>> unaryOprFilter = new TypeFilter<CtUnaryOperator<?>>(CtUnaryOperator.class);

			for (CtUnaryOperator<?> unaryOpr : element.getElements(unaryOprFilter)) {
				
				CtElement parent = unaryOpr.getParent();
				if ((parent != null)) {
					
					if (parent instanceof CtBinaryOperator
							|| parent instanceof CtAssignment
							|| parent instanceof CtLocalVariable
							|| parent instanceof CtInvocation
							|| parent instanceof CtArrayAccess
							|| parent instanceof CtReturn) {
						
						try {
							int lineNumber = parent.getPosition().getLine();
							String snippet = parent.prettyprint();
							
							save(qualifiedName, unaryOpr, lineNumber, snippet);
							
						} catch (SpoonException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}

				}
			}
		}

	}

	private void save(String qualifiedName, CtUnaryOperator<?> unaryOpr, int lineNumber, String snippet) {

		switch (unaryOpr.getKind()) {
		case POSTINC:
			Dataset.save(qualifiedName, new AoCInfo(AoC.POSTINCDEC, lineNumber, snippet));
			break;

		case POSTDEC:
			Dataset.save(qualifiedName, new AoCInfo(AoC.POSTINCDEC, lineNumber, snippet));
			break;	
		
		case PREINC:
			Dataset.save(qualifiedName, new AoCInfo(AoC.PREINCDEC, lineNumber, snippet));
			break;

		case PREDEC:
			Dataset.save(qualifiedName, new AoCInfo(AoC.PREINCDEC, lineNumber, snippet));
			break;

		default:
			break;
		}
	}
}
