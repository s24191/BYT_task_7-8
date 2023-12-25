package test.b_Money;

import static org.junit.Assert.*;

import b_Money.*;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("TestAccount");
		assertThrows(AccountExistsException.class, () -> SweBank.openAccount("Ulrika"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(500, SEK));
		assertEquals(500, SweBank.getBalance("Ulrika"), 0);
	}


	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(500, SEK));
		SweBank.withdraw("Ulrika", new Money(200, SEK));
		assertEquals(300, SweBank.getBalance("Ulrika"), 0);
	}


	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0, SweBank.getBalance("Ulrika"), 0);
	}


	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(500, SEK));
		assertEquals(500, SweBank.getBalance("Ulrika"), 0);
		assertEquals(500, Nordea.getBalance("Bob"), 0);
	}


	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));

		SweBank.addTimedPayment("Ulrika", "payment1", 5, 0, new Money(100, SEK), Nordea, "Bob");

		// Check initial balance
		assertEquals(1000, SweBank.getBalance("Ulrika"), 0);
		assertEquals(0, Nordea.getBalance("Bob"), 0);

		// Trigger the timed payment
		SweBank.tick();

		// Check the balance after the timed payment
		assertEquals(900, SweBank.getBalance("Ulrika"), 0);
		assertEquals(100, Nordea.getBalance("Bob"), 0);

		// Remove the timed payment
		SweBank.removeTimedPayment("Ulrika", "payment1");

		// trigger another tick and check if the balance remains the same
		SweBank.tick();
		assertEquals(900, SweBank.getBalance("Ulrika"), 0);
		assertEquals(100, Nordea.getBalance("Bob"), 0);
	}


}
