/*
 * Copyright (c) 2018-2020, Draque Thompson, draquemail@gmail.com
 * All rights reserved.
 *
 * Licensed under: MIT Licence
 * See LICENSE.TXT included with this code to read the full license agreement.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.darisadesigns.polyglotlina.ManagersCollections;

import TestResources.DummyCore;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.darisadesigns.polyglotlina.DictCore;
import org.darisadesigns.polyglotlina.IOHandler;
import org.darisadesigns.polyglotlina.Nodes.ConWord;
import org.darisadesigns.polyglotlina.Nodes.DeclensionGenRule;
import org.darisadesigns.polyglotlina.Nodes.TypeNode;
import org.darisadesigns.polyglotlina.PGTUtil;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author DThompson
 */
public class DeclensionManagerTest {
    
    public DeclensionManagerTest() {
    }

    /**
     * In the test Lodenkur file, there is a second dimensional value added to 
     * adverbials specifically to test this feature. It is disabled.
     */
    @Test
    public void testIsCombinedDeclSurpressed() {
        System.out.println("DeclensionManagerTest.testIsCombinedDeclSurpressed");
        
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        DeclensionManager decMan = core.getDeclensionManager();
        
        assertTrue(decMan.isCombinedDeclSurpressed(",3,", 3));
    }

    @Test
    public void testSetCombinedDeclSurpressed() {
        System.out.println("DeclensionManagerTest.testSetCombinedDeclSurpressed");
        
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        DeclensionManager decMan = core.getDeclensionManager();
        
        decMan.setCombinedDeclSuppressed(",3,", 4, false);
        assertFalse(decMan.isCombinedDeclSurpressed(",3,", 4));
    }

    @Test
    public void testSetCombinedDeclSurpressedRaw() {
        System.out.println("DeclensionManagerTest.testSetCombinedDeclSurpressedRaw");
        
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        DeclensionManager decMan = core.getDeclensionManager();
        
        decMan.setCombinedDeclSuppressedRaw("4,TESTVAL", false);
        assertFalse(decMan.isCombinedDeclSurpressed("TESTVAL", 4));
    }

    @Test
    public void testAddDeclensionGenRule() {
        System.out.println("DeclensionManagerTest.testAddDeclensionGenRule");
        
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        DeclensionManager decMan = core.getDeclensionManager();
        
        DeclensionGenRule rule = new DeclensionGenRule();
        rule.setCombinationId("COMBID");
        rule.setName("TESTNAME");
        rule.setRegex("TESTREGEX");
        rule.setTypeId(99);
        rule.setIndex(1);
        decMan.addDeclensionGenRule(rule);
        List<DeclensionGenRule> rules = Arrays.asList(decMan.getDeclensionRulesForType(99));
        assertTrue(rules.contains(rule));
    }

    @Test
    public void testWipeDeclensionGenRules() {
        System.out.println("DeclensionManagerTest.testWipeDeclensionGenRules");
        
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        DeclensionManager decMan = core.getDeclensionManager();
        
        DeclensionGenRule rule = new DeclensionGenRule();
        rule.setCombinationId("COMBID");
        rule.setName("TESTNAME");
        rule.setRegex("TESTREGEX");
        rule.setTypeId(98);
        rule.setIndex(1);
        decMan.addDeclensionGenRule(rule);
        decMan.wipeDeclensionGenRules(98);
        assertEquals(0, decMan.getDeclensionRulesForType(98).length);
    }

    @Test
    public void testDeleteDeclensionGenRule() {
        System.out.println("DeclensionManagerTest.testDeleteDeclensionGenRule");
        
        DictCore core = DummyCore.newCore();
        DictCore debugCore = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        DeclensionManager decMan = core.getDeclensionManager();
        
        DeclensionGenRule rule = new DeclensionGenRule();
        rule.setCombinationId("COMBID");
        rule.setName("TESTNAME");
        rule.setRegex("TESTREGEX");
        rule.setTypeId(97);
        rule.setIndex(1);
        decMan.addDeclensionGenRule(rule);
        decMan.deleteDeclensionGenRule(rule);
        assertFalse(Arrays.asList(decMan.getDeclensionRulesForType(97)).contains(rule));
    }

    @Test
    public void testGetDeclensionRulesForType() {
        System.out.println("DeclensionManagerTest.testGetDeclensionRulesForType");
        
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        DeclensionManager decMan = core.getDeclensionManager();
        
        DeclensionGenRule rule1 = new DeclensionGenRule();
        rule1.setCombinationId("COMBID");
        rule1.setName("TESTNAME");
        rule1.setRegex("TESTREGEX");
        rule1.setTypeId(96);
        rule1.setIndex(1);
        decMan.addDeclensionGenRule(rule1);
        DeclensionGenRule rule2 = new DeclensionGenRule();
        rule2.setCombinationId("COMBID");
        rule2.setName("TESTNAME");
        rule2.setRegex("TESTREGEX");
        rule2.setTypeId(96);
        rule2.setIndex(1);
        decMan.addDeclensionGenRule(rule2);
        
        List<DeclensionGenRule> rules = Arrays.asList(decMan.getDeclensionRulesForType(96));

        assertTrue(rules.contains(rule1) && rules.contains(rule2));
    }
    
    /**
     * Test of deleteRulesFromDeclensionTemplates method, of class DeclensionManager.
     */
    @Test
    public void testDeleteRulesFromDeclensionTemplatesInitial() {
        System.out.println("DeclensionManagerTest.testDeleteRulesFromDeclensionTemplatesInitial");
        DictCore subCore = DummyCore.newCore();
        
        try {
            subCore.readFile(PGTUtil.TESTRESOURCES + "testBulkDelete.pgd");
            int expectedInitialSize = 8;

            DeclensionManager decManSub = subCore.getDeclensionManager();
            assertEquals(expectedInitialSize, decManSub.getDeclensionRulesForType(2).length);
        } catch (IOException | IllegalStateException e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testDeleteRulesFromDeclensionTemplatesDelPast() {
        System.out.println("DeclensionManagerTest.testDeleteRulesFromDeclensionTemplatesDelPast");
        DictCore subCore = DummyCore.newCore();
        
        try {
            subCore.readFile(PGTUtil.TESTRESOURCES + "testBulkDelete.pgd");
            int expectedFinalSize = 6;

            DeclensionManager decManSub = subCore.getDeclensionManager();
            DeclensionGenRule toDelete = decManSub.getDeclensionRulesForType(2)[2];
            List<DeclensionGenRule> rulesToDelete = new ArrayList<>();
            rulesToDelete.add(toDelete);
            decManSub.deleteRulesFromDeclensionTemplates(2, 1, 2, rulesToDelete);
            assertEquals(expectedFinalSize, decManSub.getDeclensionRulesForType(2).length);
        } catch (IOException | IllegalStateException e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testDeleteRulesFromDeclensionTemplatesDelFemale() {
        System.out.println("DeclensionManagerTest.testDeleteRulesFromDeclensionTemplatesDelFemale");
        DictCore subCore = DummyCore.newCore();
        
        try {
            subCore.readFile(PGTUtil.TESTRESOURCES + "testBulkDelete.pgd");
            int expectedFinalSize = 6;

            DeclensionManager decManSub = subCore.getDeclensionManager();
            DeclensionGenRule toDelete = decManSub.getDeclensionRulesForType(2)[2];
            List<DeclensionGenRule> rulesToDelete = new ArrayList<>();
            rulesToDelete.add(toDelete);
            decManSub.deleteRulesFromDeclensionTemplates(2, 0, 3, rulesToDelete);
            assertEquals(expectedFinalSize, decManSub.getDeclensionRulesForType(2).length);
        } catch (IOException | IllegalStateException e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }

    /**
     * Test of bulkDeleteRuleFromDeclensionTemplates method, of class DeclensionManager.
     */
    @Test
    public void testBulkDeleteRuleFromDeclensionTemplates() {
        System.out.println("DeclensionManagerTest.testBulkDeleteRuleFromDeclensionTemplates");
        DictCore subCore = DummyCore.newCore();
        
        try {
            subCore.readFile(PGTUtil.TESTRESOURCES + "testBulkDelete.pgd");
            int expectedFinalSize = 5;

            DeclensionManager decManSub = subCore.getDeclensionManager();
            DeclensionGenRule toDelete = decManSub.getDeclensionRulesForType(2)[2];
            List<DeclensionGenRule> rulesToDelete = new ArrayList<>();
            rulesToDelete.add(toDelete);
            decManSub.bulkDeleteRuleFromDeclensionTemplates(2, rulesToDelete);
            assertEquals(expectedFinalSize, decManSub.getDeclensionRulesForType(2).length);
        } catch (IOException | IllegalStateException e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testBulkDeleteRuleFromDeclensionTemplatesMultiSelect() {
        System.out.println("DeclensionManagerTest.testBulkDeleteRuleFromDeclensionTemplatesMultiSelect");
        DictCore subCore = DummyCore.newCore();
        
        try {
            subCore.readFile(PGTUtil.TESTRESOURCES + "testBulkDelete.pgd");
            List<DeclensionGenRule> rulesToDelete = new ArrayList<>();
            int expectedFinalSize = 4;

            DeclensionManager decManSub = subCore.getDeclensionManager();
            rulesToDelete.add(decManSub.getDeclensionRulesForType(2)[2]);
            rulesToDelete.add(decManSub.getDeclensionRulesForType(2)[1]);
            decManSub.bulkDeleteRuleFromDeclensionTemplates(2, rulesToDelete);
            assertEquals(expectedFinalSize, decManSub.getDeclensionRulesForType(2).length);
        } catch (IOException | IllegalStateException e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testDeleteRuleByTypeIdAndCombinedId() {
        System.out.println("DeclensionManagerTest.testDeleteRuleByTypeIdAndCombinedId");
        DictCore subCore = DummyCore.newCore();
        
        try {
            subCore.readFile(PGTUtil.TESTRESOURCES + "testBulkDelete.pgd");
            int typeId = 2;
            String combinedDeclensionId = ",2,2,";
            int expectedResultSize = 5;

            DeclensionManager decManSub = subCore.getDeclensionManager();

            decManSub.deleteDeclensionGenRules(typeId, combinedDeclensionId);
            List<DeclensionGenRule> result = Arrays.asList(decManSub.getDeclensionRulesForType(typeId));
            assertEquals(result.size(), expectedResultSize);
        } catch (IOException | IllegalStateException e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testNoCoreWordDeclensionException() {
        System.out.println("DeclensionManagerTest.testNoCoreWordDeclensionException");
        DictCore subCore = DummyCore.newCore();
        ConWord noCoreWord = new ConWord();
        String expectedMessage = "Words without populated dictionary cores cannot be tested.";
        
        try {
            subCore.readFile(PGTUtil.TESTRESOURCES + "test_conj_debug.pgd");
            noCoreWord.setValue("hi");
            noCoreWord.setWordTypeId(2);

            Throwable threw = assertThrows(NullPointerException.class, () -> {
                subCore.getDeclensionManager().declineWord(noCoreWord, "");
            });

            String resultMessage = threw.getLocalizedMessage();
            assertEquals(expectedMessage, resultMessage);
        } catch (IOException | IllegalStateException e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testConjDebugBasic() {
        System.out.println("DeclensionManagerTest.testConjDebugBasic");
        
        DictCore debugCore = DummyCore.newCore();
        
        try {
            debugCore.readFile(PGTUtil.TESTRESOURCES + "test_conj_debug.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        String expectdResult = 
                "APPLIED RULES BREAKDOWN:\n" +
                "--------------------------------------\n" +
                "Rule: past-all\n" +
                "    value: hi matches regex: \".*\". Rule will be applied.\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"^\"\n" +
                "        Text: \"ALL\"\n" +
                "        Effect: hi -> ALLhi\n" +
                "--------------------------------------\n" +
                "Rule: past-true\n" +
                "    Word's class does not match filter values for rule. Rule will not be applied.\n" +
                "--------------------------------------\n" +
                "Rule: past-false\n" +
                "    Word's class does not match filter values for rule. Rule will not be applied.\n" +
                "";
        ConWord word = new ConWord();
        
        word.setValue("hi");
        word.setWordTypeId(2);
        word.setCore(debugCore);
        
        try {
            debugCore.getDeclensionManager().declineWord(word, ",2,");

            String result = "";

            for (String debugString : debugCore.getDeclensionManager().getDecGenDebug()) {
                result += debugString;
            }

            assertEquals(result, expectdResult);
        } catch (Exception e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }

    @Test
    public void testConjDebugClassValOne() {
        System.out.println("DeclensionManagerTest.testConjDebugClassValOne");
        
        DictCore debugCore = DummyCore.newCore();
        
        try {
            debugCore.readFile(PGTUtil.TESTRESOURCES + "test_conj_debug.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        String expectdResult = 
                "APPLIED RULES BREAKDOWN:\n" +
                "--------------------------------------\n" +
                "Rule: past-all\n" +
                "    value: hi matches regex: \".*\". Rule will be applied.\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"^\"\n" +
                "        Text: \"ALL\"\n" +
                "        Effect: hi -> ALLhi\n" +
                "--------------------------------------\n" +
                "Rule: past-true\n" +
                "    value: hi matches regex: \".*\". Rule will be applied.\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"$\"\n" +
                "        Text: \"TRUE\"\n" +
                "        Effect: ALLhi -> ALLhiTRUE\n" +
                "--------------------------------------\n" +
                "Rule: past-false\n" +
                "    Word's class does not match filter values for rule. Rule will not be applied.\n" +
                "";
        ConWord word = new ConWord();
        
        word.setValue("hi");
        word.setWordTypeId(2);
        word.setCore(debugCore);
        word.setClassValue(2, 0); // class val #1
        
        try {
            debugCore.getDeclensionManager().declineWord(word, ",2,");

            String result = "";

            for (String debugString : debugCore.getDeclensionManager().getDecGenDebug()) {
                result += debugString;
            }

            assertEquals(result, expectdResult);
        } catch (Exception e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testConjDebugClassValTwo() {
        System.out.println("DeclensionManagerTest.testConjDebugClassValTwo");
        
        DictCore debugCore = DummyCore.newCore();
        
        try {
            debugCore.readFile(PGTUtil.TESTRESOURCES + "test_conj_debug.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        String expectdResult = 
                "APPLIED RULES BREAKDOWN:\n" +
                "--------------------------------------\n" +
                "Rule: past-all\n" +
                "    value: hi matches regex: \".*\". Rule will be applied.\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"^\"\n" +
                "        Text: \"ALL\"\n" +
                "        Effect: hi -> ALLhi\n" +
                "--------------------------------------\n" +
                "Rule: past-true\n" +
                "    Word's class does not match filter values for rule. Rule will not be applied.\n" +
                "--------------------------------------\n" +
                "Rule: past-false\n" +
                "    value: hi matches regex: \".*\". Rule will be applied.\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"$\"\n" +
                "        Text: \"FALSE\"\n" +
                "        Effect: ALLhi -> ALLhiFALSE\n" +
                "";
        ConWord word = new ConWord();
        
        word.setValue("hi");
        word.setWordTypeId(2);
        word.setCore(debugCore);
        word.setClassValue(2, 2); // class val #2
        
        try {
            debugCore.getDeclensionManager().declineWord(word, ",2,");

            String result = "";

            for (String debugString : debugCore.getDeclensionManager().getDecGenDebug()) {
                result += debugString;
            }

            assertEquals(result, expectdResult);
        } catch (Exception e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testConjFilterNegagiveMultiTransformDebug() {
        System.out.println("DeclensionManagerTest.testConjFilterNegagiveMultiTransformDebug");
        
        DictCore debugCore = DummyCore.newCore();
        
        try {
            debugCore.readFile(PGTUtil.TESTRESOURCES + "test_conj_debug.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        String expectdResult = 
                "APPLIED RULES BREAKDOWN:\n" +
                "--------------------------------------\n" +
                "Rule: FILTER_START_A\n" +
                "    value: hi does not match regex: \"A.*\". Rule will not be applied.\n" +
                "--------------------------------------\n" +
                "Rule: MULTI-TRANS\n" +
                "    value: hi matches regex: \".*\". Rule will be applied.\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"$\"\n" +
                "        Text: \"-1\"\n" +
                "        Effect: hi -> hi-1\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"$\"\n" +
                "        Text: \"-2\"\n" +
                "        Effect: hi-1 -> hi-1-2\n" +
                "";
        ConWord word = new ConWord();
        
        word.setValue("hi");
        word.setWordTypeId(2);
        word.setCore(debugCore);
        
        try {
            debugCore.getDeclensionManager().declineWord(word, ",3,");

            String result = "";

            for (String debugString : debugCore.getDeclensionManager().getDecGenDebug()) {
                result += debugString;
            }

            assertEquals(result, expectdResult);
        } catch (Exception e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testConjFilterPositiveMultiTransformDebug() {
        System.out.println("DeclensionManagerTest.testConjFilterPositiveMultiTransformDebug");
        
        DictCore debugCore = DummyCore.newCore();
        
        try {
            debugCore.readFile(PGTUtil.TESTRESOURCES + "test_conj_debug.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        String expectdResult = 
                "APPLIED RULES BREAKDOWN:\n" +
                "--------------------------------------\n" +
                "Rule: FILTER_START_A\n" +
                "    value: Ahi matches regex: \"A.*\". Rule will be applied.\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"^\"\n" +
                "        Text: \"START_A\"\n" +
                "        Effect: Ahi -> START_AAhi\n" +
                "--------------------------------------\n" +
                "Rule: MULTI-TRANS\n" +
                "    value: Ahi matches regex: \".*\". Rule will be applied.\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"$\"\n" +
                "        Text: \"-1\"\n" +
                "        Effect: START_AAhi -> START_AAhi-1\n" +
                "    -------------------------\n" +
                "    Transformation:\n" +
                "        Regex: \"$\"\n" +
                "        Text: \"-2\"\n" +
                "        Effect: START_AAhi-1 -> START_AAhi-1-2\n" +
                "";
        ConWord word = new ConWord();
        
        word.setValue("Ahi");
        word.setWordTypeId(2);
        word.setCore(debugCore);
        
        try {
            debugCore.getDeclensionManager().declineWord(word, ",3,");

            String result = "";

            for (String debugString : debugCore.getDeclensionManager().getDecGenDebug()) {
                result += debugString;
            }

            assertEquals(result, expectdResult);
        } catch (Exception e) {
            IOHandler.writeErrorLog(e, e.getLocalizedMessage());
            fail(e);
        }
    }
    
    @Test
    public void testGetAllDepGenerationRules() {
        System.out.println("DeclensionManagerTest.testGetAllDepGenerationRules");
        
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        int typeId = 4; // verbs
        int expectedRules = 13;
        DeclensionManager decMan = core.getDeclensionManager();
        decMan.addDeclensionToTemplate(typeId, "ZIM-ZAM!");
        DeclensionGenRule[] rules = core.getDeclensionManager().getAllDepGenerationRules(typeId);
        
        int resultRules = rules.length;
        
        assertEquals(expectedRules, resultRules);
    }
    
    @Test
    public void testGetDeclensionRules() {
        System.out.println("DeclensionManagerTest.testGetDeclensionRules");
        
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "Lodenkur_TEST.pgd");
        } catch (IOException | IllegalStateException e) {
            fail(e);
        }
        
        ConWord word = new ConWord();
        int expectedRuleCount = 13;
        String expectedFirstRuleName = "Past, Cert, Pos rule";
        word.setCore(core);
        word.setWordTypeId(4); // verb
        word.setValue("er5");
        
        DeclensionGenRule[] rules = core.getDeclensionManager().getDeclensionRules(word);
        int resultRuleCount = rules.length;
        String resultFirstRuleName = rules[0].getName();
        
        assertEquals(expectedRuleCount, resultRuleCount);
        assertEquals(expectedFirstRuleName, resultFirstRuleName);
    }
    
    @Test
    public void testSmoothDeclensionRuleIndex() {
        System.out.println("DeclensionManagerTest.testSmoothDeclensionRuleIndex");
        
        DictCore subCore = DummyCore.newCore();
        DeclensionManager decMan = subCore.getDeclensionManager();
        TypeNode noun = new TypeNode();
        
        noun.setValue("noun");
        
        try {
            int nounId = subCore.getTypes().addNode(noun);

            DeclensionGenRule rule = new DeclensionGenRule();
            rule.setIndex(1);
            rule.setTypeId(nounId);
            decMan.addDeclensionGenRule(rule);
            rule = new DeclensionGenRule();
            rule.setIndex(3);
            rule.setTypeId(nounId);
            decMan.addDeclensionGenRule(rule);
            rule = new DeclensionGenRule();
            rule.setIndex(4);
            rule.setTypeId(nounId);
            decMan.addDeclensionGenRule(rule);

            Method smoothRules = DeclensionManager.class.getDeclaredMethod("smoothRules");
            smoothRules.setAccessible(true);
            smoothRules.invoke(decMan);

            DeclensionGenRule[] rules = decMan.getDeclensionRulesForType(nounId);

            // assert that rules have been changed from 1, 3, 4 -> 1, 2, 3
            assertEquals(3, rules.length);
            assertEquals(1, rules[0].getIndex());
            assertEquals(2, rules[1].getIndex());
            assertEquals(3, rules[2].getIndex());
        } catch (Exception e) {
            fail(e);
            IOHandler.writeErrorLog(e);
        }
    }
    
    @Test
    public void testGetDeclensionRulesForType_Two() {
        System.out.println("DeclensionManagerTest.testGetDeclensionRulesForType_Two");
        
        DictCore core = DummyCore.newCore();
        int expectedAllRulesCount = 8;
        String[] expectedNameOrder = {"R1", "R2", "R3", "R4", "A1", "A2", "A3", "A4"};
        int[] expectedIndexOrder = {1, 2, 3, 4, 5, 6, 7, 8};
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "testReorderRules.pgd");
            DeclensionManager decMan = core.getDeclensionManager();
            
            DeclensionGenRule[] allRules = decMan.getDeclensionRulesForType(2);
            
            assertEquals(expectedAllRulesCount, allRules.length);
            
            for (int i = 0; i < expectedNameOrder.length; i++) {
                assertEquals(expectedNameOrder[i], allRules[i].getName());
                assertEquals(expectedIndexOrder[i], allRules[i].getIndex());
            }
        } catch (IOException e) {
            fail(e);
        }
    }
    
    @Test
    public void testGetDeclensionRulesForTypeAndCombId() {
        System.out.println("DeclensionManagerTest.testGetDeclensionRulesForTypeAndCombId");

        DictCore core = DummyCore.newCore();
        int expectedAllRulesCount = 4;
        String[] expectedNameOrder = {"R1", "R2", "R3", "R4"};
        int[] expectedIndexOrder = {1, 2, 3, 4};
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "testReorderRules.pgd");
            DeclensionManager decMan = core.getDeclensionManager();
            
            DeclensionGenRule[] allRules = decMan.getDeclensionRulesForTypeAndCombId(2, ",2,2,");
            
            assertEquals(expectedAllRulesCount, allRules.length);
            
            for (int i = 0; i < expectedNameOrder.length; i++) {
                assertEquals(expectedNameOrder[i], allRules[i].getName());
                assertEquals(expectedIndexOrder[i], allRules[i].getIndex());
            }
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    public void testMoveRulesUp_OneRule() {
        System.out.println("DeclensionManagerTest.testMoveRulesUp_OneRule");
        
        String[] expectedNameOrder = {"R1", "R2", "R4", "R3", "A1", "A2", "A3", "A4"};
        int[] expectedIndexOrder = {1, 2, 3, 4, 5, 6, 7, 8};
        String combId = ",2,2,";
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "testReorderRules.pgd");
            DeclensionManager decMan = core.getDeclensionManager();
            
            DeclensionGenRule[] rules = decMan.getDeclensionRulesForTypeAndCombId(2, combId);
            decMan.moveRulesUp(2, combId, Arrays.asList(rules[3]));
            
            DeclensionGenRule[] resultRules = decMan.getDeclensionRulesForType(2);
            
            for (int i = 0; i < expectedNameOrder.length; i++) {
                assertEquals(expectedNameOrder[i], resultRules[i].getName());
                assertEquals(expectedIndexOrder[i], resultRules[i].getIndex());
            }
            
        } catch (IOException e) {
            fail(e);
        }
    }
    
    @Test
    public void testMoveRulesUp_TwoRules() {
        System.out.println("DeclensionManagerTest.testMoveRulesUp_TwoRules");
        
        String[] expectedNameOrder = {"R1", "R3", "R4", "R2", "A1", "A2", "A3", "A4"};
        int[] expectedIndexOrder = {1, 2, 3, 4, 5, 6, 7, 8};
        String combId = ",2,2,";
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "testReorderRules.pgd");
            DeclensionManager decMan = core.getDeclensionManager();
            
            DeclensionGenRule[] rules = decMan.getDeclensionRulesForTypeAndCombId(2, combId);
            decMan.moveRulesUp(2, combId, Arrays.asList(rules[2], rules[3]));
            
            DeclensionGenRule[] resultRules = decMan.getDeclensionRulesForType(2);
            
            for (int i = 0; i < expectedNameOrder.length; i++) {
                assertEquals(expectedNameOrder[i], resultRules[i].getName());
                assertEquals(expectedIndexOrder[i], resultRules[i].getIndex());
            }
            
        } catch (IOException e) {
            fail(e);
        }
    }
    
    @Test
    public void testMoveRulesUp_TwoRules_AlredayTop() {
        System.out.println("DeclensionManagerTest.testMoveRulesUp_TwoRules_AlredayTop");
        
        String[] expectedNameOrder = {"R1", "R2", "R3", "R4", "A1", "A2", "A3", "A4"};
        int[] expectedIndexOrder = {1, 2, 3, 4, 5, 6, 7, 8};
        String combId = ",2,2,";
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "testReorderRules.pgd");
            DeclensionManager decMan = core.getDeclensionManager();
            
            DeclensionGenRule[] rules = decMan.getDeclensionRulesForTypeAndCombId(2, combId);
            decMan.moveRulesUp(2, combId, Arrays.asList(rules[0], rules[1]));
            
            DeclensionGenRule[] resultRules = decMan.getDeclensionRulesForType(2);
            
            for (int i = 0; i < expectedNameOrder.length; i++) {
                assertEquals(expectedNameOrder[i], resultRules[i].getName());
                assertEquals(expectedIndexOrder[i], resultRules[i].getIndex());
            }
            
        } catch (IOException e) {
            fail(e);
        }
    }
    
    @Test
    public void testMoveRulesDown_OneRule() {
        System.out.println("DeclensionManagerTest.testMoveRulesDown_OneRule");
        
        String[] expectedNameOrder = {"R2", "R1", "R3", "R4", "A1", "A2", "A3", "A4"};
        int[] expectedIndexOrder = {1, 2, 3, 4, 5, 6, 7, 8};
        String combId = ",2,2,";
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "testReorderRules.pgd");
            DeclensionManager decMan = core.getDeclensionManager();
            
            DeclensionGenRule[] rules = decMan.getDeclensionRulesForTypeAndCombId(2, combId);
            decMan.moveRulesDown(2, combId, Arrays.asList(rules[0]));
            
            DeclensionGenRule[] resultRules = decMan.getDeclensionRulesForType(2);
            
            for (int i = 0; i < expectedNameOrder.length; i++) {
                assertEquals(expectedNameOrder[i], resultRules[i].getName());
                assertEquals(expectedIndexOrder[i], resultRules[i].getIndex());
            }
            
        } catch (IOException e) {
            fail(e);
        }
    }
    
    @Test
    public void testMoveRulesDown_TwoRules() {
        System.out.println("DeclensionManagerTest.testMoveRulesDown_TwoRules");
        
        String[] expectedNameOrder = {"R1", "R4", "R2", "R3", "A1", "A2", "A3", "A4"};
        int[] expectedIndexOrder = {1, 2, 3, 4, 5, 6, 7, 8};
        String combId = ",2,2,";
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "testReorderRules.pgd");
            DeclensionManager decMan = core.getDeclensionManager();
            
            DeclensionGenRule[] rules = decMan.getDeclensionRulesForTypeAndCombId(2, combId);
            decMan.moveRulesDown(2, combId, Arrays.asList(rules[1], rules[2]));
            
            DeclensionGenRule[] resultRules = decMan.getDeclensionRulesForType(2);
            
            for (int i = 0; i < expectedNameOrder.length; i++) {
                assertEquals(expectedNameOrder[i], resultRules[i].getName());
                assertEquals(expectedIndexOrder[i], resultRules[i].getIndex());
            }
            
        } catch (IOException e) {
            fail(e);
        }
    }
    
    @Test
    public void testMoveRulesDown_TwoRules_AlredayBottom() {
        System.out.println("DeclensionManagerTest.testMoveRulesDown_TwoRules_AlredayBottom");
        
        String[] expectedNameOrder = {"R1", "R2", "R3", "R4", "A1", "A2", "A3", "A4"};
        int[] expectedIndexOrder = {1, 2, 3, 4, 5, 6, 7, 8};
        String combId = ",2,2,";
        DictCore core = DummyCore.newCore();
        
        try {
            core.readFile(PGTUtil.TESTRESOURCES + "testReorderRules.pgd");
            DeclensionManager decMan = core.getDeclensionManager();
            
            DeclensionGenRule[] rules = decMan.getDeclensionRulesForTypeAndCombId(2, combId);
            decMan.moveRulesDown(2, combId, Arrays.asList(rules[2], rules[3]));
            
            DeclensionGenRule[] resultRules = decMan.getDeclensionRulesForType(2);
            
            for (int i = 0; i < expectedNameOrder.length; i++) {
                assertEquals(expectedNameOrder[i], resultRules[i].getName());
                assertEquals(expectedIndexOrder[i], resultRules[i].getIndex());
            }
            
        } catch (IOException e) {
            fail(e);
        }
    }
}
