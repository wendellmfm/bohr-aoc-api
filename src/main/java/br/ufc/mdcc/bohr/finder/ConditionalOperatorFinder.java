package br.ufc.mdcc.bohr.finder;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class ConditionalOperatorFinder extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtConditional<?>> filter = new TypeFilter<CtConditional<?>>(CtConditional.class);
			for (CtConditional<?> condOpr : element.getElements(filter)) {
				if ((condOpr.getParent() != null) && ((condOpr.getParent() instanceof CtAssignment)
						|| condOpr.getParent() instanceof CtLocalVariable)) {
					int lineNumber = condOpr.getParent().getPosition().getLine();
					String snippet = condOpr.getParent().prettyprint();
					Dataset.store(qualifiedName, new AoCInfo(AoC.CoO, lineNumber, snippet));
				}
			}
		}
	}
}
