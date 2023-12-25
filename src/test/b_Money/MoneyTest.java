package test.b_Money;

import static org.junit.Assert.*;

import b_Money.Currency;
import b_Money.Money;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals((Double) 10000.0, SEK100.getAmount());
		assertEquals((Double) 0.0, EUR0.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("10000 SEK", SEK100.toString());
		assertEquals("0 EUR", EUR0.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals((Integer)(int)(10000 * 0.15), SEK100.universalValue());
		assertEquals((Integer)(int)(1000 * 1.5), EUR10.universalValue());
	}

	@Test
	public void testEqualsMoney() {
//		assertTrue(EUR10.equals(new Money(1000, EUR)));
//		assertFalse(SEK100.equals(EUR10));\

		SEK100 = new Money(10000, SEK); // 10000 SEK
		EUR10 = new Money(1000, EUR); // 1000 EUR

		assertTrue(EUR10.equals(new Money(1000, EUR)));
		assertTrue(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		Money sum = SEK100.add(EUR10);
		assertEquals((Double) (10000 + (1000 * 0.15 / 1.5)), sum.getAmount());
	}

	@Test
	public void testSub() {
		Money difference = SEK200.sub(EUR10);
		assertEquals((Double) (20000 - (1000 * 0.15 / 1.5)), difference.getAmount());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(SEK100.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals((Double) (-10000.0), SEK100.negate().getAmount());
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(new Money(10000, SEK)));
		assertTrue(SEK200.compareTo(EUR10) > 0);
	}
}
