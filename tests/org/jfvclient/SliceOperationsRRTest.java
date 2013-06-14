/**
 *
 */
package org.jfvclient;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;

import org.jfvclient.requests.AddSlice;
import org.jfvclient.responses.Slice;
import org.jfvclient.testing.TestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Set of tests that do things to slices. These include <code>add-slice</code>,
 * <code>update-slice</code> and <code>remove-slice</code>.
 *
 *
 * @author Niklas Rehfeld
 *
 */
public class SliceOperationsRRTest
{
	static Type t;

	private static String slicename = "testslice123";
	private JFVClient c = new JFVClient();

	@BeforeClass
	public static void setup()
	{
		t = new TypeToken<FVRpcResponse<Boolean>>()
		{
		}.getType();
	}

	@Test
	public void testAddNew() throws IOException
	{
		// if the slice already exists, add some rubbish to the end.
		if (checkExists(slicename))
		{
			slicename += Long.toHexString(Math.round(Math.random() * 10000));
		}
		FVRpcResponse<Boolean> reso = addSlice(slicename);

		assertFalse("Result should not be an error", reso.isError());
		Boolean b = reso.getResult();
		assertTrue("Expected 'true' result", b.booleanValue());
	}

	@Test
	public void testAddDuplicate() throws IOException
	{
		if (!checkExists(slicename))
		{
			addSlice(slicename);
		}
		assertTrue("slice still doesn't exist after adding it. I quit.",
				checkExists(slicename));

		FVRpcResponse<Boolean> reso = addSlice(slicename);

		assertTrue("Result should be an error", reso.isError());
		assertEquals("Result code should be -32602 / invalid parameters",
				-32602, reso.getError().getCode());

	}


	private boolean checkExists(String slicename)
	{
		try
		{
			return c.listSlices().contains(new Slice(slicename));
		} catch (Exception e)
		{
			System.err.println(e.getLocalizedMessage());
			return false;
		}
	}

	private FVRpcResponse<Boolean> addSlice(String name) throws IOException
	{
		AddSlice a = new AddSlice(name, "tcp:localhost:6633", "nik@localhost",
				"testpassword");
		Gson g = TestUtils.getGson();
		FVRpcRequest<AddSlice> asr = new FVRpcRequest<AddSlice>("add-slice", a);
		String res = c.send(g, asr);

		return g.fromJson(res, t);

	}

}
