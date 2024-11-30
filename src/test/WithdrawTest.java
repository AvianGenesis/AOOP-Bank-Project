package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import account.Account;
import account.Checking;
import loggable.Withdraw;
import main.Customer;

public class WithdrawTest {
    private Customer mockCustomer = new Customer("Esteban", "Diaz", "String", "String", "String", "String", 5, "String",
            5);
    private Account account = new Checking(mockCustomer, 1, 1000);
    private Withdraw with = new Withdraw(account, 5);

    @Before
    public void setUp() {
        account = new Checking(mockCustomer, 1, 1000);
        with = new Withdraw(account, 5);
    }

    @Test
    public void testWithdrawPositive() {
        Withdraw with = new Withdraw(account, 5);
        assertTrue(with.action());
        assertEquals(995.0, account.getAccountBalance(), 0);
    }

    @Test
    public void testWithdrawTooMuch() {
        Withdraw with = new Withdraw(account, 2000);
        assertFalse(with.action());
    }

    @Test
    public void testWithdrawNegative() {
        Withdraw with = new Withdraw(account, -5);
        assertFalse(with.action());
    }

    @Test
    public void testWithdrawZero() {
        Withdraw with = new Withdraw(account, 0);
        assertFalse(with.action());
    }

    @Test
    public void testWithdrawReport() {
        assertEquals("Withdrew $5.00 from Checking-1\n", with.getReport());
    }

    @Test
    public void testWithdrawLog() {
        assertEquals(
                "Esteban Diaz withdrew $5.00 from Checking-1. Esteban Diaz’s New Balance for Checking-1: $1,000.00\n",
                with.getLog());
    }

    @Test
    public void testWithdrawSuccess() {
        assertEquals("Successfully withdrew $5.00 from Checking-1\n", with.getSuccess());
    }

    @Test
    public void testWithdrawType() {
        assertEquals("withdraws", with.getType());
    }
}
