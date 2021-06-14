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

class ArithmeticAsLogicTest {

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
		String path = "./src/test/resources/ArithmeticAsLogic/";
		String[] searchers = new String[] { "br.ufc.mdcc.jaoc.searcher.ArithmeticAsLogicSearcher" };
		Collection<AoCSuite> aocSuiteList = JAoCAPI.searchAoC(path, searchers);

		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());

		for (AoCSuite suite : aocSuiteList) {
			assertEquals("ArithmeticAsLogicSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 2, "There are more AoC than expected.");

			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				assertEquals(AoC.AaL, aocInfo.getAtomOfConfusion(), "AoC type not mached");
				assertTrue(aocInfo.getLineNumber() == 9 || aocInfo.getLineNumber() == 19,
						"AoC found out of the expected line. Line: " + aocInfo.getLineNumber() + " Snippet: "
								+ aocInfo.getSnippet());

			}
		}
	}
}
