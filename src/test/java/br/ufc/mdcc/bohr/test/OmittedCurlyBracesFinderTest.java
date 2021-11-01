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

class OmittedCurlyBracesFinderTest {

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
		String path = "./src/test/resources/OmittedCurlyBraces/";
		String[] finders = new String[] { "br.ufc.mdcc.bohr.finder.OmittedCurlyBracesFinder" };
		Collection<AoCSuite> aocSuiteList = BohrAPI.findAoC(path, finders, false);

		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());

		for (AoCSuite suite : aocSuiteList) {
			assertEquals("OmittedCurlyBracesSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 17, "There are more or less AoC than expected.");

			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				assertEquals(AoC.OCB, aocInfo.getAtomOfConfusion(), "AoC type not mached");
				assertTrue(aocInfo.getLineNumber() == 11 || aocInfo.getLineNumber() == 13
						|| aocInfo.getLineNumber() == 44 || aocInfo.getLineNumber() == 46
						|| aocInfo.getLineNumber() == 73 || aocInfo.getLineNumber() == 75
						|| aocInfo.getLineNumber() == 102 || aocInfo.getLineNumber() == 106
						|| aocInfo.getLineNumber() == 136 || aocInfo.getLineNumber() == 161
						|| aocInfo.getLineNumber() == 165 || aocInfo.getLineNumber() == 190
						|| aocInfo.getLineNumber() == 194 || aocInfo.getLineNumber() == 223
						|| aocInfo.getLineNumber() == 243 || aocInfo.getLineNumber() == 262
						|| aocInfo.getLineNumber() == 274,
						"AoC found out of the expected line. Line: " + aocInfo.getLineNumber() + " Snippet: "
								+ aocInfo.getSnippet());

			}
		}
	}
}
