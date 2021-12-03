package br.ufc.mdcc.bohr.finder;

import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.Dataset;
import br.ufc.mdcc.bohr.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtConditional;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class ConditionalOperatorFinder extends AbstractProcessor<CtType<?>> {

	public void process(CtType<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			TypeFilter<CtConditional<?>> filter = new TypeFilter<CtConditional<?>>(CtConditional.class);
			for (CtConditional<?> condOpr : element.getElements(filter)) {
				int lineNumber = condOpr.getPosition().getLine();
				String snippet = condOpr.prettyprint();
				Dataset.save(qualifiedName, new AoCInfo(AoC.CO, lineNumber, snippet));
			}
		}
	}
}
