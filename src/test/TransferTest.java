package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import account.Account;
import account.Checking;
import loggable.Transfer;
import main.Customer;

public class TransferTest {
    private Customer mockCustomer = new Customer("Esteban", "Diaz", "String", "String", "String", "String", 5, "String",
            5);
    private Customer mockToCustomer = new Customer("Zachary", "Flaherty", "String", "String", "String", "String", 5,
            "String", 10);
    private Account account = new Checking(mockCustomer, 1, 1000);
    private Account toAccount = new Checking(mockToCustomer, 2, 500);
    private Transfer tran;

    @Before
    public void setUp() {
        account = new Checking(mockCustomer, 1, 1000);
        tran = new Transfer(account, 5, toAccount);
    }

    @Test
    public void testTransferPositive() {
        Transfer tran = new Transfer(account, 5, toAccount);
        assertTrue(tran.action());
        assertEquals(995.0, account.getAccountBalance(), 0);
        assertEquals(505.0, toAccount.getAccountBalance(), 0);
    }

    @Test
    public void testTransferTooMuch() {
        Transfer tran = new Transfer(account, 2000, toAccount);
        assertFalse(tran.action());
    }

    @Test
    public void testTransferNegative() {
        Transfer tran = new Transfer(account, -5, toAccount);
        assertFalse(tran.action());
    }

    @Test
    public void testTransferZero() {
        Transfer tran = new Transfer(account, 0, toAccount);
        assertFalse(tran.action());
    }

    @Test
    public void testTransferReport() {
        assertEquals("Transferred $5.00 from Checking-1 to Checking-2\n", tran.getReport());
    }

    @Test
    public void testTransferLog() {
        assertEquals(
                "Esteban Diaz transferred $5.00 from Checking-1 to Checking-2. New balance for Checking-1: $1,000.00. New balance for Checking-2: $500.00.\n",
                tran.getLog());
    }

    @Test
    public void testTransferSuccess() {
        assertEquals("Successfully transferred $5.00 from Checking-1 to Checking-2\n", tran.getSuccess());
    }

    @Test
    public void testTransferType() {
        assertEquals("transfers", tran.getType());
    }
}
