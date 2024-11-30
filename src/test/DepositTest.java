package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import account.Account;
import account.Checking;
import loggable.Deposit;
import main.Customer;

public class DepositTest {
    private Customer mockCustomer = new Customer("Esteban", "Diaz", "String", "String", "String", "String", 5, "String",
            5);
    private Account account = new Checking(mockCustomer, 1, 1000);
    private Deposit depo = new Deposit(account, 5);

    @Before
    public void setUp() {
        account = new Checking(mockCustomer, 1, 1000);
        depo = new Deposit(account, 5);
    }

    @Test
    public void testDepositPositive() {
        Deposit depo = new Deposit(account, 5);
        assertTrue(depo.action());
        assertEquals(1005.0, account.getAccountBalance(), 0);
    }

    @Test
    public void testDepositNegative() {
        Deposit depo = new Deposit(account, -5);
        assertFalse(depo.action());
    }

    @Test
    public void testDepositZero() {
        Deposit depo = new Deposit(account, 0);
        assertFalse(depo.action());
    }

    @Test
    public void testDepositReport() {
        assertEquals("Deposited $5.00 into Checking-1\n", depo.getReport());
    }

    @Test
    public void testDepositLog() {
        assertEquals(
                "Esteban Diaz deposited $5.00 into Checking-1. Esteban Diaz’s New Balance for Checking-1: $1,000.00\n",
                depo.getLog());
    }

    @Test
    public void testDepositSuccess() {
        assertEquals("Successfully deposited $5.00 into Checking-1\n", depo.getSuccess());
    }

    @Test
    public void testDepositType() {
        assertEquals("deposits", depo.getType());
    }
}
