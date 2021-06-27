package br.ufc.mdcc.jaoc.searcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufc.mdcc.jaoc.model.AoC;
import br.ufc.mdcc.jaoc.model.AoCInfo;
import br.ufc.mdcc.jaoc.model.Dataset;
import br.ufc.mdcc.jaoc.util.Util;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class TypeConversionSearcher extends AbstractProcessor<CtClass<?>> {

	public void process(CtClass<?> element) {
		if (Util.isValid(element)) {
			String qualifiedName = element.getQualifiedName();

			for (CtLocalVariable<?> localVariable : element.getElements(new TypeFilter<CtLocalVariable<?>>(CtLocalVariable.class))) {
				if(hasTypeConversion(localVariable.getOriginalSourceFragment().getSourceCode())) {
					int lineNumber = localVariable.getPosition().getEndLine();
					String snippet = localVariable.getOriginalSourceFragment().getSourceCode();
					Dataset.store(qualifiedName, new AoCInfo(AoC.TPC, lineNumber, snippet));
				}
			}
			
			for (CtAssignment<?, ?> assignment : element.getElements(new TypeFilter<CtAssignment<?, ?>>(CtAssignment.class))) {
				if(hasTypeConversion(assignment.getOriginalSourceFragment().getSourceCode())) {
					int lineNumber = assignment.getPosition().getEndLine();
					String snippet = assignment.getOriginalSourceFragment().getSourceCode();
					Dataset.store(qualifiedName, new AoCInfo(AoC.TPC, lineNumber, snippet));
				}
			}
			
		}
		
	}
	
	private static boolean hasTypeConversion(String statement) {
		Pattern pattern = Pattern.compile("\\((\\s*int\\s*)\\)");
		Matcher matcher = pattern.matcher(statement); 
		
		String javaAPIUsageString = "Math.floor";
		if(matcher.find() && !statement.contains(javaAPIUsageString)) {
			return true;
		}
		
		return false;
	}
}
