/**
 *
 */
package org.jfvclient;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;

import org.jfvclient.requests.AddSlice;
import org.jfvclient.testing.TestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Niklas Rehfeld
 *
 */
public class AddSliceRRTest
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


		//TODO check if a slice of this name exists and change name if necessary.
	}

	@AfterClass
	public static void cleanup()
	{
		//TODO delete the slice we just created.
	}


	@Test
	public void testAddNew() throws MalformedURLException, IOException
	{
		AddSlice a = new AddSlice(slicename, "tcp:localhost:6633",
				"nik@localhost", "testpassword");
		Gson g = TestUtils.getGson();
		FVRpcRequest<AddSlice> asr = new FVRpcRequest<AddSlice>("add-slice", a);
		String res = c.send(g, asr);

		FVRpcResponse<Boolean> reso = g.fromJson(res, t);

		assertFalse("Result should not be an error", reso.isError());
		Boolean b = reso.getResult();
		assertTrue("Expected 'true' result", b.booleanValue());
	}

	@Test
	public void testAddDuplicate() throws MalformedURLException, IOException
	{
		AddSlice a = new AddSlice(slicename, "tcp:localhost:6633",
				"nik@localhost", "testpassword");
		Gson g = TestUtils.getGson();
		FVRpcRequest<AddSlice> asr = new FVRpcRequest<AddSlice>("add-slice", a);
		String res = c.send(g, asr);

		FVRpcResponse<Boolean> reso = g.fromJson(res, t);

		assertTrue("Result should be an error", reso.isError());
		assertEquals(-32602, reso.getError().getCode());

	}

}
