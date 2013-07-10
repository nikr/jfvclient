/**
 *
 */
package org.jfvclient.data;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Niklas Rehfeld
 *
 */
public class DpidTest
{
	/**
	 * Test method for {@link org.jfvclient.data.Dpid#Dpid(long)}.
	 */
	@Test
	public void testDpidLong()
	{
		Dpid d = new Dpid(1L);
		assertTrue(d.isValid());

		d = new Dpid(12L);
		assertTrue(d.isValid());

		d = new Dpid(123L);
		assertTrue(d.isValid());

		d = new Dpid(1234L);
		assertTrue(d.isValid());

		d = new Dpid(3234567890123456789L);
		assertTrue(d.isValid());

		d= new Dpid(Long.MAX_VALUE);
		assertTrue(d.isValid());

		d = new Dpid(Long.MIN_VALUE);
		assertTrue(d.isValid());

		d = new Dpid(-1L);
		assertTrue(d.isValid());
	}


	/**
	 * Test method for {@link org.jfvclient.data.Dpid#isValid()}.
	 */
	@Test
	public void testIsValid()
	{
		Dpid d = new Dpid("bla");
		assertFalse(d.isValid());

		d = new Dpid("alll");
		assertFalse(d.isValid());

		d= new Dpid("all");
		assertTrue(d.isValid());

		d= new Dpid("00:00:00:00:00:00:11:11");
		assertTrue(d.isValid());

	}

	/**
	 * Test method for {@link org.jfvclient.data.Dpid#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject()
	{
		Dpid d = new Dpid(1);
		Dpid e = new Dpid("00:00:00:00:00:00:00:01");
		assertTrue(d.equals(e));

		d = new Dpid("all");
		assertFalse(d.equals(e));

		d = new Dpid("all");
		e = new Dpid("any");
		assertTrue(d.equals(e));


	}

}
