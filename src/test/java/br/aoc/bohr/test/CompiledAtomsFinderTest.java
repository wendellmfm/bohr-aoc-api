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
		String[] finders = new String[] { "br.aoc.bohr.finder.PreAndPostIncrementDecrementFinder",
				"br.aoc.bohr.finder.ConditionalOperatorFinder",
				"br.aoc.bohr.finder.OmittedCurlyBracesFinder",
				"br.aoc.bohr.finder.LogicAsControlFlowFinder",
				"br.aoc.bohr.finder.ArithmeticAsLogicFinder",
				"br.aoc.bohr.finder.ChangeOfLiteralEncodingFinder",
				"br.aoc.bohr.finder.TypeConversionFinder",
				"br.aoc.bohr.finder.InfixOperatorPrecedenceFinder",
				"br.aoc.bohr.finder.RepurposedVariablesFinder"};
		
		Collection<AoCSuite> aocSuiteList = BohrAPI.findAoC(path, finders, false);
		
		assertTrue(aocSuiteList.size() == 1, "There are more sample classes than expected. Actual number: " + aocSuiteList.size());
		
		for (AoCSuite suite : aocSuiteList) {

			assertEquals("CompiledAtomsSample", suite.getClassQualifiedName(), "Qualified name not matched.");

			System.out.println(suite.getAtomsOfConfusion().size());
			assertTrue(suite.getAtomsOfConfusion().size() == 50, "There are more or less AoC than expected.");
			
			atomsCount = 0;
			
			for(AoCInfo aocInfo: suite.getAtomsOfConfusion()) {
				preAndPosIncrementDecrementTests(aocInfo);
				
				conditionalOperatorTests(aocInfo);
				
				omittedCurlyBracesTests(aocInfo);
				
				logicAsControlFlowTests(aocInfo);
				
				arithmeticAsLogic(aocInfo);
				
				changeOfLiteralEncodingTests(aocInfo);
				
				typeConversionTests(aocInfo);
				
				infixOperatorPrecedenceTests(aocInfo);
				
				repurposedVariablesTests(aocInfo);
				
			}
			
			assertTrue(atomsCount == 50, "Number of AoC types not mached");
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
		
		if(aocInfo.getLineNumber() == 279 && aocInfo.getAtomOfConfusion() != AoC.LaCTRF) {
			checkAoCInfo(aocInfo, AoC.PreINC, 279);
		}

		if(aocInfo.getLineNumber() == 306 && aocInfo.getAtomOfConfusion() != AoC.LaCTRF
				&& aocInfo.getAtomOfConfusion() != AoC.IOP) {
			checkAoCInfo(aocInfo, AoC.PreINC, 306);
		}
		
		if(aocInfo.getLineNumber() == 329 && aocInfo.getAtomOfConfusion() != AoC.LaCTRF) {
			checkAoCInfo(aocInfo, AoC.PreINC, 329);
		}
		
		checkAoCInfo(aocInfo, AoC.PreINC, 291);
		checkAoCInfo(aocInfo, AoC.PreINC, 294);
	}
	
	private void conditionalOperatorTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.CoO, 126);
		checkAoCInfo(aocInfo, AoC.CoO, 150);
		checkAoCInfo(aocInfo, AoC.CoO, 175);
	}
	
	private void omittedCurlyBracesTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.OCB, 410);
		checkAoCInfo(aocInfo, AoC.OCB, 430);
		checkAoCInfo(aocInfo, AoC.OCB, 450);
		checkAoCInfo(aocInfo, AoC.OCB, 470);
		checkAoCInfo(aocInfo, AoC.OCB, 476);
		checkAoCInfo(aocInfo, AoC.OCB, 498);
		checkAoCInfo(aocInfo, AoC.OCB, 509);
	}
	
	private void logicAsControlFlowTests(AoCInfo aocInfo) {
		if(aocInfo.getLineNumber() == 279 && aocInfo.getAtomOfConfusion() != AoC.PreINC) {
			checkAoCInfo(aocInfo, AoC.LaCTRF, 279);
		}
		
		if(aocInfo.getLineNumber() == 306 && aocInfo.getAtomOfConfusion() != AoC.PreINC
				&& aocInfo.getAtomOfConfusion() != AoC.IOP) {
			checkAoCInfo(aocInfo, AoC.LaCTRF, 306);
		}
		
		if(aocInfo.getLineNumber() == 329 && aocInfo.getAtomOfConfusion() != AoC.PreINC) {
			checkAoCInfo(aocInfo, AoC.LaCTRF, 329);
		}
	}

	private void arithmeticAsLogic(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.AaL, 198);
		checkAoCInfo(aocInfo, AoC.AaL, 218);
		checkAoCInfo(aocInfo, AoC.AaL, 238);
		checkAoCInfo(aocInfo, AoC.AaL, 258);
		checkAoCInfo(aocInfo, AoC.AaL, 268);
	}
	
	private void changeOfLiteralEncodingTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.CoLE, 353);
		checkAoCInfo(aocInfo, AoC.CoLE, 366);
		checkAoCInfo(aocInfo, AoC.CoLE, 379);
		checkAoCInfo(aocInfo, AoC.CoLE, 392);
		checkAoCInfo(aocInfo, AoC.CoLE, 394);
	}
	
	private void typeConversionTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.TPC, 540);
		checkAoCInfo(aocInfo, AoC.TPC, 554);
		checkAoCInfo(aocInfo, AoC.TPC, 568);
		checkAoCInfo(aocInfo, AoC.TPC, 582);
		checkAoCInfo(aocInfo, AoC.TPC, 584);
	}
	
	private void infixOperatorPrecedenceTests(AoCInfo aocInfo) {
		if(aocInfo.getLineNumber() == 306 && aocInfo.getAtomOfConfusion() != AoC.PreINC
				&& aocInfo.getAtomOfConfusion() != AoC.LaCTRF) {
			checkAoCInfo(aocInfo, AoC.IOP, 306);
		}
		
		checkAoCInfo(aocInfo, AoC.IOP, 592);
		checkAoCInfo(aocInfo, AoC.IOP, 604);
		checkAoCInfo(aocInfo, AoC.IOP, 638);
		checkAoCInfo(aocInfo, AoC.IOP, 650);
	}
	
	private void repurposedVariablesTests(AoCInfo aocInfo) {
		checkAoCInfo(aocInfo, AoC.RVar, 636);
	}
	
}
