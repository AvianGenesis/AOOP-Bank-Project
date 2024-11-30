package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import account.Account;
import account.Checking;
import main.Customer;

public class AccountTest {

    private Customer mockCustomer = new Customer("Esteban", "Diaz", "String", "String", "String", "String", 5, "String",
            5);
    private Account account = new Checking(mockCustomer, 1, 1000);

    @Before
    public void setUp() {
        account = new Checking(mockCustomer, 1, 1000);
    }

    @Test
    public void testDepositPositiveAmount() {
        double initialBalance = account.getAccountBalance(); // Get initial balance
        double amountToDeposit = 10.0;
        boolean result = account.deposit(amountToDeposit); // Deposit positive amount

        assertTrue("Deposit should return true for positive amounts.", result);
        assertEquals("Balance should increase by the deposited amount.",
                initialBalance + amountToDeposit, account.getAccountBalance(), 0.001);
    }

    @Test
    public void testDepositZeroAmount() {
        double initialBalance = account.getAccountBalance();
        boolean result = account.deposit(0.0);

        assertFalse("Deposit should return false for zero amount.", result);
        assertEquals("Balance should remain the same when depositing zero.",
                initialBalance, account.getAccountBalance(), 0.001);
    }

    @Test
    public void testWithdrawValidAmount() {
        double initialBalance = account.getAccountBalance();
        double amountToWithdraw = 100.0;
        boolean result = account.withdraw(amountToWithdraw);

        assertTrue("Withdraw should return true for valid amount.", result);
        assertEquals("Balance should decrease by the withdrawn amount.",
                initialBalance - amountToWithdraw, account.getAccountBalance(), 0.001);
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        double initialBalance = account.getAccountBalance();
        boolean result = account.withdraw(2000.0); // Exceeds balance

        assertFalse("Withdraw should return false if amount exceeds balance.", result);
        assertEquals("Balance should remain the same when withdrawal exceeds balance.",
                initialBalance, account.getAccountBalance(), 0.001);
    }

    @Test
    public void testDepositNegativeAmount() {
        double initialBalance = account.getAccountBalance();
        boolean result = account.deposit(-50.0);

        assertFalse("Deposit should return false for negative amounts.", result);
        assertEquals("Balance should remain the same when attempting to deposit a negative amount.",
                initialBalance, account.getAccountBalance(), 0.001);
    }

    @Test
    public void testWithdrawNegativeAmount() {
        double initialBalance = account.getAccountBalance();
        boolean result = account.withdraw(-50.0);

        assertFalse("Withdraw should return false for negative amounts.", result);
        assertEquals("Balance should remain the same when attempting to withdraw a negative amount.",
                initialBalance, account.getAccountBalance(), 0.001);
    }
}
