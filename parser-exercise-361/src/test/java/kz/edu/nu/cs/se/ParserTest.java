package kz.edu.nu.cs.se;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;


public class ParserTest {
    
    /*
     * True Examples
     */
    
    @Test
    public void test_1() {
        String s = "a;";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("single value statement", parser.program());
    }
    
    @Test
    public void test_2() {
        String s = "(a,b);";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("two element list", parser.program());
    }
    
    @Test
    public void test_3() {
        String s = "(alice, bob);";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("two element list longer identifiers", parser.program());
    }
    
    @Test
    public void test_4() {
        String s = "(a,1);";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("two element list, letters and numbers", parser.program());
    }
    
    @Test
    public void test_5() {
        String s = "(a,b,(u, v));";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("nested list", parser.program());
    }
    
    @Test
    public void test_6() {
        String s = "();";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("empty list", parser.program());
    }
    
    @Test
    public void test_7() {
        String s = "(alice,(bob,(claire,dennis)));";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("deeper nested list right", parser.program());
    }
    
    @Test
    public void test_8() {
        String s = "(((alice,bob),claire),dennis); ";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("deeper nested list left", parser.program());
    }
    
    @Test
    public void test_9() {
        String s = "((alice,2.0),(),(),(()));";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("nested list with empty", parser.program());
    }
    
    @Test
    public void test_10() {
        String s = "a;" + "(a,b);" + "\n(alice, bob);" + "((alice,2.0),(),(),(()));";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertTrue("multiline program", parser.program());
    }
    
    
    /*
     * False Examples
     */
    
    @Test
    public void parse_error_1() {
        String s = "a";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertFalse("missing semicolon", parser.program());
    }
    
    @Test
    public void parse_error_2() {
        String s = "(a,b,";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertFalse("list not finished", parser.program());
    }
    
    @Test
    public void parse_error_3() {
        String s = "(a,b,c);(a,";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertFalse("list on second line not finished", parser.program());
    }
    
    @Test
    public void parse_error_4() {
        String s = "()";
        MyLanguageParser parser = new MyLanguageParser(s);
        assertFalse("empty list no semicolon", parser.program());
    }
}
