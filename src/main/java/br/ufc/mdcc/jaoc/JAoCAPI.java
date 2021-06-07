package br.ufc.mdcc.jaoc;

import java.util.Collection;

import br.ufc.mdcc.jaoc.model.AoCSuite;
import br.ufc.mdcc.jaoc.model.Dataset;
import spoon.Launcher;
import spoon.SpoonAPI;

public class JAoCAPI {

	private static SpoonAPI spoon;

	public static Collection<AoCSuite> searchAoC(String sourceCodePath) {
		build(sourceCodePath);
		configure();
		process();
		return Dataset.list();
	}

	
	public static Collection<AoCSuite> searchAoC(String sourceCodePath, String[] searchers) {
		build(sourceCodePath);
		configure(searchers);
		process();
		return Dataset.list();
	}

	private static void build(String sourceCodePath) {
		spoon = new Launcher();
		spoon.addInputResource(sourceCodePath);
		spoon.buildModel();
	}

	private static void configure() {
		spoon.addProcessor("br.ufc.mdcc.jaoc.searcher.IncDecSearcher");
		spoon.addProcessor("br.ufc.mdcc.jaoc.searcher.ConditionalOperatorSearcher");
		spoon.addProcessor("br.ufc.mdcc.jaoc.searcher.InfixOperatorPrecedenceSearcher");
		spoon.addProcessor("br.ufc.mdcc.jaoc.searcher.OmittedCurlyBracesSearcher");
	}

	private static void configure(String[] searchers) {
		for (String searcher : searchers) {
			spoon.addProcessor(searcher);
		}
	}

	private static void process() {
		spoon.process();
	}
	
	public static void clean() {
		Dataset.clear();
	}
	
}