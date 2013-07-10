/**
 *
 */
package org.jfvclient;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jfvclient.responses.SliceInfo;
import org.junit.Test;

/**
 * @author rehfelnikl
 *
 */
public class ListSliceInfoRRTest
{

	/**
	 * test if the 'fvadmin' slice exists.
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	@Test
	public void test() throws IOException, JFVErrorResponseException
	{
		JFVClient c = new JFVClient();
		SliceInfo i = c.listSliceInfo("fvadmin");
		assertEquals("fvadmin", i.getSliceName());
	}

}
