/**
 *
 */
package org.jfvclient;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jfvclient.responses.Slice;
import org.jfvclient.responses.SliceList;
import org.junit.Test;

/**
 * Checks if the default fvadmin slice is present.
 *
 * @author Niklas Rehfeld
 *
 */
public class ListSlicesRRTest
{

	/**
	 * test if the 'fvadmin' slice is in the list returned by list-slices.
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	@Test
	public void test() throws IOException, JFVErrorResponseException
	{
		JFVClient c = new JFVClient();
		SliceList sl = c.listSlices();
		assertTrue(sl.contains(new Slice("fvadmin")));
	}

}
