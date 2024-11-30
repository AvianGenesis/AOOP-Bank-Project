package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import account.Account;
import account.Checking;
import loggable.Pay;
import main.Customer;

public class PayTest {
    private Customer mockCustomer = new Customer("Esteban", "Diaz", "String", "String", "String", "String", 5, "String",
            5);
    private Customer mockToCustomer = new Customer("Zachary", "Flaherty", "String", "String", "String", "String", 5,
            "String", 10);
    private Account account = new Checking(mockCustomer, 1, 1000);
    private Account toAccount = new Checking(mockToCustomer, 2, 500);
    private Pay pay;

    @Before
    public void setUp() {
        account = new Checking(mockCustomer, 1, 1000);
        pay = new Pay(account, 5, toAccount);
    }

    @Test
    public void testPayPositive() {
        Pay pay = new Pay(account, 5, toAccount);
        assertTrue(pay.action());
        assertEquals(995.0, account.getAccountBalance(), 0);
        assertEquals(505.0, toAccount.getAccountBalance(), 0);
    }

    @Test
    public void testPayTooMuch() {
        Pay pay = new Pay(account, 2000, toAccount);
        assertFalse(pay.action());
    }

    @Test
    public void testPayNegative() {
        Pay pay = new Pay(account, -5, toAccount);
        assertFalse(pay.action());
    }

    @Test
    public void testPayZero() {
        Pay pay = new Pay(account, 0, toAccount);
        assertFalse(pay.action());
    }

    @Test
    public void testPayReport() {
        assertEquals("Paid $5.00 from Checking-1 to Checking-2\n", pay.getReport());
    }

    @Test
    public void testPayLog() {
        assertEquals(
                "Esteban Diaz paid $5.00 to Zachary Flaherty from Checking-1. New balance for Checking-1: $1,000.00. New balance for Checking-2: $500.00.\n",
                pay.getLog());
    }

    @Test
    public void testPaySuccess() {
        assertEquals("Successfully paid $5.00 from Checking-1 to Checking-2\n", pay.getSuccess());
    }

    @Test
    public void testPayType() {
        assertEquals("pays", pay.getType());
    }
}
