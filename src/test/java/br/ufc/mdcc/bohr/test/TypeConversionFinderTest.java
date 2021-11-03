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

class TypeConversionFinderTest {

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
		String path = "./src/test/resources/TypeConversion/";
		String[] finders = new String[] { "br.ufc.mdcc.bohr.finder.TypeConversionFinder" };
		Collection<AoCSuite> aocSuiteList = BohrAPI.findAoC(path, finders, false);

		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());

		for (AoCSuite suite : aocSuiteList) {
			assertEquals("TypeConversionSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 21, "There are more or less AoC than expected.");

			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				assertEquals(AoC.TPC, aocInfo.getAtomOfConfusion(), "AoC type not mached");
				assertTrue(aocInfo.getLineNumber() == 10 || aocInfo.getLineNumber() == 24 || aocInfo.getLineNumber() == 38
						|| aocInfo.getLineNumber() == 54 || aocInfo.getLineNumber() == 70 || aocInfo.getLineNumber() == 84
						|| aocInfo.getLineNumber() == 98 || aocInfo.getLineNumber() == 112 || aocInfo.getLineNumber() == 126
						|| aocInfo.getLineNumber() == 140 || aocInfo.getLineNumber() == 154 || aocInfo.getLineNumber() == 170
						|| aocInfo.getLineNumber() == 186 || aocInfo.getLineNumber() == 200 || aocInfo.getLineNumber() == 214
						|| aocInfo.getLineNumber() == 230 || aocInfo.getLineNumber() == 246 || aocInfo.getLineNumber() == 262 
						|| aocInfo.getLineNumber() == 278 || aocInfo.getLineNumber() == 292 || aocInfo.getLineNumber() == 306,
						"AoC found out of the expected line. Line: " + aocInfo.getLineNumber() + " Snippet: "
								+ aocInfo.getSnippet());

			}
		}
	}
}
