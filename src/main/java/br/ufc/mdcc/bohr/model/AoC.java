package br.ufc.mdcc.bohr.model;

public enum AoC {

	IOP("IOP", "Infix Operator Precedence"),
	PostINC("PostINC", "Post-Increment"), 
	PostDEC("PostDEC", "Post-Decrement"),
	PreINC("PreINC", "Pre-Increment"),
	PreDEC("PreDEC", "Pre-Decrement"),
	CVar("CVar", "Constant Variables"),
	RInd("RInd", "Remove Identation Atom"),
	CoO("CoO", "Conditional Operator"),
	AaL("AaL", "Arithmetic as Logic"),
	LaCTRF("LaCTRF", "Logic as Control Flow"),
	RVar("RVar", "Repurpused Variables"),
	DUR("DUR", "Dead, Unreacheble, and Repeated"),
	CoLE("CoLE", "Change of Literal Encoding"),
	OCB("OCB", "Omitted Curly Braces"),
	TPC("TPC", "Type Conversion"),
	Ind("Ind", "Identation");

	private final String shortName;

	private final String fullName;

	AoC(String shortName, String fullName) {
		this.shortName = shortName;
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public String getFullName() {
		return fullName;
	}
}