package br.ufc.mdcc.bohr.model;

import java.util.Collection;
import java.util.Vector;

public class AoCSuite {

	private String classQualifiedName;

	private Collection<AoCInfo> atomsOfConfusion;

	public AoCSuite(String classQualifiedName) {
		this.classQualifiedName = classQualifiedName;
		this.atomsOfConfusion = new Vector<>();
	}

	public String getClassQualifiedName() {
		return classQualifiedName;
	}

	public void setClassQualifiedName(String classQualifiedName) {
		this.classQualifiedName = classQualifiedName;
	}

	public Collection<AoCInfo> getAtomsOfConfusion() {
		return atomsOfConfusion;
	}

	public void addAoCInfo(AoCInfo atomOfConfusion) {
		this.atomsOfConfusion.add(atomOfConfusion);
	}

}
