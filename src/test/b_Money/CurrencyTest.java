package test.b_Money;

import static org.junit.Assert.*;

import b_Money.Currency;
import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.001);
		assertEquals(0.20, DKK.getRate(), 0.001);
		assertEquals(1.5, EUR.getRate(), 0.001);
	}

	@Test
	public void testSetRate() {
		SEK.setRate(0.16);
		assertEquals(0.16, SEK.getRate(), 0.001);

		DKK.setRate(0.21);
		assertEquals(0.21, DKK.getRate(), 0.001);
	}

	@Test
	public void testGlobalValue() {
		assertEquals((Integer)150, SEK.universalValue(1000));
		assertEquals((Integer)200, DKK.universalValue(1000));
		assertEquals((Integer)1500, EUR.universalValue(1000));
	}

	@Test
	public void testValueInThisCurrency() {
		assertEquals((Integer)(int)(1000 * 1.5 / 0.15), SEK.valueInThisCurrency(1000, EUR));
		assertEquals((Integer)(int)(1000 * 0.15 / 1.5), EUR.valueInThisCurrency(1000, SEK));
	}

}
