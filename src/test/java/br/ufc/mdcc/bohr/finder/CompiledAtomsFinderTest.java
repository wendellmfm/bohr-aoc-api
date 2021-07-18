package br.ufc.mdcc.bohr.finder;

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

class CompiledAtomsFinderTest {
	
	private int atomsCount;

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
		String path = "./src/test/resources/CompiledAtoms/";
		String[] finders = new String[] { "br.ufc.mdcc.bohr.finder.PreAndPostIncrementDecrementFinder",
				"br.ufc.mdcc.bohr.finder.ConditionalOperatorFinder",
				"br.ufc.mdcc.bohr.finder.OmittedCurlyBracesFinder",
				"br.ufc.mdcc.bohr.finder.LogicAsControlFlowFinder",
				"br.ufc.mdcc.bohr.finder.ArithmeticAsLogicFinder",
				"br.ufc.mdcc.bohr.finder.ChangeOfLiteralEncodingFinder",
				"br.ufc.mdcc.bohr.finder.TypeConversionFinder"};
		
		Collection<AoCSuite> aocSuiteList = BohrAPI.searchAoC(path, finders);
		
		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());
		
		for (AoCSuite suite : aocSuiteList) {

			assertEquals("CompiledAtomsSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			assertTrue(suite.getAtomsOfConfusion().size() == 44, "There are more or less AoC than expected.");
			
			atomsCount = 0;
			
			for(AoCInfo aocInfo: suite.getAtomsOfConfusion()) {
				preAndPosIncrementDecrementTests(aocInfo);
				
				conditionalOperatorTests(aocInfo);
				
				omittedCurlyBracesTests(aocInfo);
				
				logicAsControlFlowTests(aocInfo);
				
				arithmeticAsLogic(aocInfo);
				
				changeOfLiteralEncodingTests(aocInfo);
				
				typeConversionTests(aocInfo);
			}
			
			assertTrue(atomsCount == 44, "Number of AoC types not mached");
		}
	}

	private void checkAoCInfo(AoCInfo aocInfo, AoC aocType, int lineNumber) {
		if(aocInfo.getLineNumber() == lineNumber) {
			assertEquals(aocType, aocInfo.getAtomOfConfusion(), "AoC type not mached. Line: " + aocInfo.getLineNumber() + " Snippet: " + aocInfo.getSnippet());
			atomsCount++;
		}
	}

	private void preAndPosIncrementDecrementTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.PostINC, 6);			
		checkAoCInfo(aocInfo, AoC.PostINC, 22);
		checkAoCInfo(aocInfo, AoC.PostDEC, 43);
		checkAoCInfo(aocInfo, AoC.PostDEC, 66);
		checkAoCInfo(aocInfo, AoC.PostDEC, 67);
		checkAoCInfo(aocInfo, AoC.PreINC, 73);
		checkAoCInfo(aocInfo, AoC.PreDEC, 89);
		checkAoCInfo(aocInfo, AoC.PreDEC, 110);
		
		if(aocInfo.getLineNumber() == 259 && aocInfo.getAtomOfConfusion() != AoC.LaCTRF) {
			checkAoCInfo(aocInfo, AoC.PreINC, 259);
		}

		if(aocInfo.getLineNumber() == 286 && aocInfo.getAtomOfConfusion() != AoC.LaCTRF) {
			checkAoCInfo(aocInfo, AoC.PreINC, 286);
		}
		
		if(aocInfo.getLineNumber() == 309 && aocInfo.getAtomOfConfusion() != AoC.LaCTRF) {
			checkAoCInfo(aocInfo, AoC.PreINC, 309);
		}
		
		checkAoCInfo(aocInfo, AoC.PreINC, 271);
		checkAoCInfo(aocInfo, AoC.PreINC, 274);
	}
	
	private void conditionalOperatorTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.CoO, 126);
		checkAoCInfo(aocInfo, AoC.CoO, 150);
		checkAoCInfo(aocInfo, AoC.CoO, 175);
	}
	
	private void omittedCurlyBracesTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.OCB, 390);
		checkAoCInfo(aocInfo, AoC.OCB, 410);
		checkAoCInfo(aocInfo, AoC.OCB, 430);
		checkAoCInfo(aocInfo, AoC.OCB, 450);
		checkAoCInfo(aocInfo, AoC.OCB, 456);
		checkAoCInfo(aocInfo, AoC.OCB, 478);
		checkAoCInfo(aocInfo, AoC.OCB, 489);
	}
	
	private void logicAsControlFlowTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.LaCTRF, 208);
		checkAoCInfo(aocInfo, AoC.LaCTRF, 228);
		
		if(aocInfo.getLineNumber() == 259 && aocInfo.getAtomOfConfusion() != AoC.PreINC) {
			checkAoCInfo(aocInfo, AoC.LaCTRF, 259);
		}
		
		if(aocInfo.getLineNumber() == 286 && aocInfo.getAtomOfConfusion() != AoC.PreINC) {
			checkAoCInfo(aocInfo, AoC.LaCTRF, 286);
		}
		
		if(aocInfo.getLineNumber() == 309 && aocInfo.getAtomOfConfusion() != AoC.PreINC) {
			checkAoCInfo(aocInfo, AoC.LaCTRF, 309);
		}
	}

	private void arithmeticAsLogic(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.AaL, 198);
		checkAoCInfo(aocInfo, AoC.AaL, 218);
		checkAoCInfo(aocInfo, AoC.AaL, 238);
	}
	
	private void changeOfLiteralEncodingTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.CoLE, 333);
		checkAoCInfo(aocInfo, AoC.CoLE, 346);
		checkAoCInfo(aocInfo, AoC.CoLE, 359);
		checkAoCInfo(aocInfo, AoC.CoLE, 372);
		checkAoCInfo(aocInfo, AoC.CoLE, 374);
	}
	
	private void typeConversionTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.TPC, 520);
		checkAoCInfo(aocInfo, AoC.TPC, 534);
		checkAoCInfo(aocInfo, AoC.TPC, 548);
		checkAoCInfo(aocInfo, AoC.TPC, 562);
		checkAoCInfo(aocInfo, AoC.TPC, 564);
	}
	
}
