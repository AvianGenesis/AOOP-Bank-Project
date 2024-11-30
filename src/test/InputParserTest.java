package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.InputParser;

public class InputParserTest {
    InputParser ip;

    @Before
    public void setUp(){
        ip = new InputParser();
    }

    @Test
    public void testParseInt(){
        assertEquals(100, ip.tryParseInt("100"));
        assertEquals(-50, ip.tryParseInt("-50"));
        assertEquals(Integer.MIN_VALUE, ip.tryParseInt("10.5"));
        assertEquals(Integer.MIN_VALUE, ip.tryParseInt("pizza"));
        assertEquals(Integer.MIN_VALUE, ip.tryParseInt(""));
    }

    @Test
    public void testParseDouble(){
        assertEquals(100.0, ip.tryParseAmt("100"), 0);
        assertEquals(-50.0, ip.tryParseAmt("-50"), 0);
        assertEquals(10.65, ip.tryParseAmt("10.65"), 0);
        assertEquals(10.82, ip.tryParseAmt("10.82344587"), 0);
        assertEquals(Double.MIN_VALUE, ip.tryParseAmt("pizza"), 0);
        assertEquals(Double.MIN_VALUE, ip.tryParseAmt(""), 0);
    }
}
