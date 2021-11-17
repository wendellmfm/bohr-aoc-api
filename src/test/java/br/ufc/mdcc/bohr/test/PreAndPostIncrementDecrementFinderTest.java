package br.ufc.mdcc.bohr.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufc.mdcc.bohr.BohrAPI;
import br.ufc.mdcc.bohr.model.AoC;
import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.AoCSuite;

class PreAndPostIncrementDecrementFinderTest {

	@BeforeEach
	void init() {
		BohrAPI.clean();
	}

	@AfterEach
	void tearDown() {
		BohrAPI.clean();
	}
	
	@Test
	void testProcess() {
		String path = "./src/test/resources/PreAndPostIncrementDecrement/";
		String[] finders = new String[] { "br.ufc.mdcc.bohr.finder.PreAndPostIncrementDecrementFinder" };
		Collection<AoCSuite> aocSuiteList = BohrAPI.findAoC(path, finders, false);
		
		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());
		
		for (AoCSuite suite : aocSuiteList) {

			assertEquals("PreAndPostIncrementDecrementSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 12, "There are more or less AoC than expected.");
			
			int count = 0;
			
			for(AoCInfo aocInfo: suite.getAtomsOfConfusion()) {
				if(aocInfo.getLineNumber() == 10) {
					assertEquals(AoC.POSTINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 26) {
					assertEquals(AoC.POSTINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 47) {
					assertEquals(AoC.POSTINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 68) {
					assertEquals(AoC.POSTINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 84) {
					assertEquals(AoC.POSTINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 102) {
					assertEquals(AoC.POSTINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 103) {
					assertEquals(AoC.POSTINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 109) {
					assertEquals(AoC.PREINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 125) {
					assertEquals(AoC.PREINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 146) {
					assertEquals(AoC.PREINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 161) {
					assertEquals(AoC.PREINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 177) {
					assertEquals(AoC.PREINCDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
			}
			
			assertTrue(count == 12, "Number of AoC types not mached");
		}
	}
}
