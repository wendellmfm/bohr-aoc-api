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

			assertTrue(suite.getAtomsOfConfusion().size() == 19, "There are more or less AoC than expected.");

			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				assertEquals(AoC.TPC, aocInfo.getAtomOfConfusion(), "AoC type not mached");
				assertTrue(aocInfo.getLineNumber() == 10 || aocInfo.getLineNumber() == 24 || aocInfo.getLineNumber() == 38
						|| aocInfo.getLineNumber() == 52 || aocInfo.getLineNumber() == 66 || aocInfo.getLineNumber() == 80
						|| aocInfo.getLineNumber() == 94 || aocInfo.getLineNumber() == 108 || aocInfo.getLineNumber() == 122
						|| aocInfo.getLineNumber() == 136 || aocInfo.getLineNumber() == 150 || aocInfo.getLineNumber() == 164
						|| aocInfo.getLineNumber() == 178 || aocInfo.getLineNumber() == 192 || aocInfo.getLineNumber() == 206
						|| aocInfo.getLineNumber() == 220 || aocInfo.getLineNumber() == 234 || aocInfo.getLineNumber() == 248
						|| aocInfo.getLineNumber() == 262,
						"AoC found out of the expected line. Line: " + aocInfo.getLineNumber() + " Snippet: "
								+ aocInfo.getSnippet());

			}
		}
	}
}
