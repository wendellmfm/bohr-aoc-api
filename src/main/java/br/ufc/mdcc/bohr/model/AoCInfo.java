package br.ufc.mdcc.bohr.model;

public class AoCInfo {

	private AoC atomOfConfusion;

	private int lineNumber;

	private String snippet;

	public AoCInfo(AoC atomOfConfusion, int lineNumber, String snippet) {
		super();
		this.atomOfConfusion = atomOfConfusion;
		this.lineNumber = lineNumber;
		this.snippet = snippet;
	}

	public AoC getAtomOfConfusion() {
		return atomOfConfusion;
	}

	public void setAtomOfConfusion(AoC atomOfConfusion) {
		this.atomOfConfusion = atomOfConfusion;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

}
