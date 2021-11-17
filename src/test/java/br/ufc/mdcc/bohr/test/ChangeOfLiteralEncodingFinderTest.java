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

class ChangeOfLiteralEncodingFinderTest {

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
		String path = "./src/test/resources/ChangeOfLiteralEncoding/";
		String[] finders = new String[] { "br.ufc.mdcc.bohr.finder.ChangeOfLiteralEncodingFinder" };
		Collection<AoCSuite> aocSuiteList = BohrAPI.findAoC(path, finders, false);

		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());

		for (AoCSuite suite : aocSuiteList) {
			assertEquals("ChangeOfLiteralEncodingSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 7, "There are more or less AoC than expected.");

			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				assertEquals(AoC.CLE, aocInfo.getAtomOfConfusion(), "AoC type not mached");
				assertTrue(aocInfo.getLineNumber() == 9 || aocInfo.getLineNumber() == 22
						|| aocInfo.getLineNumber() == 35 || aocInfo.getLineNumber() == 47 
						|| aocInfo.getLineNumber() == 59 || aocInfo.getLineNumber() == 72
						|| aocInfo.getLineNumber() == 74,
						"AoC found out of the expected line. Line: " + aocInfo.getLineNumber() + " Snippet: "
								+ aocInfo.getSnippet());

			}
		}
	}
}
