package br.aoc.bohr.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.aoc.bohr.BohrAPI;
import br.aoc.bohr.model.AoC;
import br.aoc.bohr.model.AoCInfo;
import br.aoc.bohr.model.AoCSuite;

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
		String[] finders = new String[] { "br.aoc.bohr.finder.PreAndPostIncrementDecrementFinder" };
		Collection<AoCSuite> aocSuiteList = BohrAPI.findAoC(path, finders, false);
		
		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());
		
		for (AoCSuite suite : aocSuiteList) {

			assertEquals("PreAndPostIncrementDecrementSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 6, "There are more or less AoC than expected.");
			
			int count = 0;
			
			for(AoCInfo aocInfo: suite.getAtomsOfConfusion()) {
				if(aocInfo.getLineNumber() == 10) {
					assertEquals(AoC.PostINC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 17) {
					assertEquals(AoC.PostINC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 27) {
					assertEquals(AoC.PostDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}	
				
				if(aocInfo.getLineNumber() == 37) {
					assertEquals(AoC.PreINC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 44) {
					assertEquals(AoC.PreDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
				
				if(aocInfo.getLineNumber() == 54) {
					assertEquals(AoC.PreDEC, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
					count++;
				}
			}
			
			assertTrue(count == 6, "Number of AoC types not mached");
		}
	}
}
