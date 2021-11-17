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

class ArithmeticAsLogicFinderTest {

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
		String path = "./src/test/resources/ArithmeticAsLogic/";
		String[] finders = new String[] { "br.ufc.mdcc.bohr.finder.ArithmeticAsLogicFinder" };
		Collection<AoCSuite> aocSuiteList = BohrAPI.findAoC(path, finders, false);

		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());

		for (AoCSuite suite : aocSuiteList) {
			assertEquals("ArithmeticAsLogicSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 5, "There are more or less AoC than expected.");

			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				assertEquals(AoC.AL, aocInfo.getAtomOfConfusion(), "AoC type not mached");
				assertTrue(aocInfo.getLineNumber() == 13 || aocInfo.getLineNumber() == 33
						|| aocInfo.getLineNumber() == 53 || aocInfo.getLineNumber() == 73
						|| aocInfo.getLineNumber() == 83,
						"AoC found out of the expected line. Line: " + aocInfo.getLineNumber() + " Snippet: "
								+ aocInfo.getSnippet());

			}
		}
	}
}
