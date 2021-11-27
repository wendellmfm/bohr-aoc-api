package br.ufc.mdcc.bohr.model;

public enum AoC {

	IOP("IOP", "Infix Operator Precedence"),
	POSTINCDEC("PostIncDec", "Post Increment Decrement"), 
	PREINCDEC("PreIncDec", "Pre Increment Decrement"),
	CO("CO", "Conditional Operator"),
	AL("AaL", "Arithmetic as Logic"),
	LCF("LaCF", "Logic as Control Flow"),
	RV("RV", "Repurposed Variables"),
	CLE("CoLE", "Change of Literal Encoding"),
	OCB("OCB", "Omitted Curly Braces"),
	TC("TC", "Type Conversion");

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