package br.ufc.mdcc.bohr.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Dataset {

	private static Map<String, AoCSuite> dataset = new HashMap<>();

	public static void save(String classQualifiedName, AoCInfo atomOfConfusionInfo) {
		AoCSuite suite = null;
		if (dataset.containsKey(classQualifiedName)) {
			suite = dataset.get(classQualifiedName);
		} else {
			suite = new AoCSuite(classQualifiedName);
			dataset.put(classQualifiedName, suite);
		}
		suite.addAoCInfo(atomOfConfusionInfo);
	}

	public static Collection<AoCSuite> list() {
		return dataset.values();
	}

	public static void clear() {
		dataset.clear();
	}
}