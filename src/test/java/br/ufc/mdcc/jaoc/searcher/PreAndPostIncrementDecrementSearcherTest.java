package br.ufc.mdcc.jaoc.searcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufc.mdcc.jaoc.JAoCAPI;
import br.ufc.mdcc.jaoc.model.AoC;
import br.ufc.mdcc.jaoc.model.AoCInfo;
import br.ufc.mdcc.jaoc.model.AoCSuite;

class PreAndPostIncrementDecrementSearcherTest {

	@BeforeEach
	void init() {
		JAoCAPI.clean();
	}

	@AfterEach
	void tearDown() {
		JAoCAPI.clean();
	}
	
	@Test
	void testProcess() {
		String path = "./src/test/resources/PreAndPostIncrementDecrement/";
		String[] searchers = new String[] { "br.ufc.mdcc.jaoc.searcher.PreAndPostIncrementDecrementSearcher" };
		Collection<AoCSuite> aocSuiteList = JAoCAPI.searchAoC(path, searchers);
		
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
