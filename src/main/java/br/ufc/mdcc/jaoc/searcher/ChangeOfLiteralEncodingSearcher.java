package br.ufc.mdcc.jaoc.searcher;

import br.ufc.mdcc.jaoc.model.AoC;
import br.ufc.mdcc.jaoc.model.AoCInfo;
import br.ufc.mdcc.jaoc.model.Dataset;
import br.ufc.mdcc.jaoc.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class ChangeOfLiteralEncodingSearcher extends AbstractProcessor<CtClass<?>> {

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
						Dataset.store(qualifiedName, new AoCInfo(AoC.CoO, lineNumber, snippet));
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
}
