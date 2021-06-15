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

class LogicAsControlFlowTest {

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
		String path = "./src/test/resources/LogicAsControlFlow/";
		String[] searchers = new String[] { "br.ufc.mdcc.jaoc.searcher.LogicAsControlFlowSearcher" };
		Collection<AoCSuite> aocSuiteList = JAoCAPI.searchAoC(path, searchers);

		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());

		for (AoCSuite suite : aocSuiteList) {
			assertEquals("LogicAsControlFlowSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 4, "There are more or less AoC than expected.");

			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				assertEquals(AoC.LaCTRF, aocInfo.getAtomOfConfusion(), "AoC type not mached");
				assertTrue(aocInfo.getLineNumber() == 10 || aocInfo.getLineNumber() == 22
						|| aocInfo.getLineNumber() == 32,
						"AoC found out of the expected line. Line: " + aocInfo.getLineNumber() + " Snippet: "
								+ aocInfo.getSnippet());

			}
		}
	}
}
