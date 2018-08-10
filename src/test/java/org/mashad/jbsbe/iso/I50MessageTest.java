package org.mashad.jbsbe.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class I50MessageTest {

	private Date testDate;

	@Before
	public void setUp() throws Exception {
		I50Factory.addField(4, "Amount", I50Type.AMOUNT);
		I50Factory.addField(10, "Date", I50Type.DATE10);
		I50Factory.addField(35, "cardNumber", I50Type.NUMERIC, 16, "xxxx-xxxx-xxxx-####");
		I50Factory.addField(11, "stan", I50Type.NUMERIC, 6);

		testDate = new SimpleDateFormat("dd.MM.yyyy").parse("10.08.2018");
	}

	@Test
	public void test() throws IllegalAccessException {
		I50Factory<SimpleTransformer> factory = createFactory(SimpleTransformer.class);

		PurchaseRequest purchaseRequest = PurchaseRequest.builder()
				.amount(100L)
				.date(testDate)
				.stan(123456)
				.cardNumber("1234567891234567").build();
		I50Message message = factory.newMessage(purchaseRequest);
		System.out.println(message);
		assertEquals("0200106000002000000000000001000008100000001234561234567891234567", new String(message.writeData()));

	}

	@Test
	public void testCustomTransformer() throws IllegalAccessException {
		I50Factory<KeyhanTransformer> factory = createFactory(KeyhanTransformer.class);

		PurchaseRequest purchaseRequest = PurchaseRequest.builder()
				.amount(100L)
				.date(testDate)
				.stan(123456)
				.cardNumber("1234567891234567").build();

		I50Message message = factory.newMessage(purchaseRequest);
		System.out.println(message);

	}
	
	
	@Test
	public void testAutoStan() throws IllegalAccessException {
		I50Factory<SimpleTransformer> factory = createFactory(SimpleTransformer.class);

		PurchaseRequest2 purchaseRequest = PurchaseRequest2.builder()
				.amount(100L)
				.date(testDate)
				.cardNumber("1234567891234567").build();

		I50Message message = factory.newMessage(purchaseRequest);
		assertNotNull("Stan has to be set automatically", message.getObjectValue(11));
	}

	@Test
	public void testGetter() throws IllegalAccessException {
		I50Factory<SimpleTransformer> factory = createFactory(SimpleTransformer.class);

		PurchaseRequestPrivate purchaseRequest = PurchaseRequestPrivate.builder()
				.amount(100L)
				.date(testDate)
				.stan(123456)
				.cardNumber("1234567891234567")
				.build();

		I50Message message = factory.newMessage(purchaseRequest);
		assertEquals("0200106000002000000000000001000008100000001234561234567891234567", new String(message.writeData()));

	}

	private <T> I50Factory createFactory(final Class<T> transformerClass) {

		try {
			return new I50Factory(transformerClass);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new AssertionError("Failed to load transformer '" + transformerClass.getName() + "' into factory class", e);
		}
	}

}
