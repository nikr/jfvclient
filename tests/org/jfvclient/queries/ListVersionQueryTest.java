/**
 *
 */
package org.jfvclient.queries;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Niklas Rehfeld
 *
 */
public class ListVersionQueryTest
{
	ListVersionQuery query;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		 query = new ListVersionQuery();
	}

	/**
	 * Test method for {@link org.jfvclient.queries.ListVersionQuery#getMethod()}.
	 */
	@Test
	public void testGetMethod()
	{
		assertEquals("list-version", query.getMethod());
	}

	/**
	 * Test method for {@link org.jfvclient.queries.ListVersionQuery#getParameters()}.
	 */
	@Test
	@Ignore
	public void testGetParameters()
	{
		fail("Not yet implemented");
	}


	/**
	 * Test method for {@link org.jfvclient.queries.ListVersionQuery#toString()}.
	 */
	@Test
	@Ignore
	public void testToString()
	{
		fail("Not yet implemented");
	}

}
