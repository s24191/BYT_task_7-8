package test.b_Money;

import b_Money.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}


	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("payment1", 10, 5, new Money(500, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("payment1"));

		testAccount.removeTimedPayment("payment1");
		assertFalse(testAccount.timedPaymentExists("payment1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("payment1", 10, 0, new Money(500, SEK), SweBank, "Alice");
		testAccount.tick();
		assertEquals(new Money(9999500, SEK), testAccount.getBalance());
		SweBank.tick();
		assertEquals(new Money(1000500, SEK).getAmount(), SweBank.getBalance("Alice"));
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(1000, SEK)); // Deposit money
		assertEquals(new Money(10001000, SEK), testAccount.getBalance()); // Check balance after deposit

		testAccount.withdraw(new Money(1000, SEK)); // Withdraw money
		assertEquals(new Money(10000000, SEK), testAccount.getBalance()); // Check balance after withdrawal
	}


	@Test
	public void testGetBalance() {
		assertEquals(new Money(10000000, SEK), testAccount.getBalance());
	}
}
