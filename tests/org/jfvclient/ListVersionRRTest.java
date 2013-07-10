package org.jfvclient;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Type;

import org.jfvclient.responses.Version;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author Niklas Rehfeld
 *
 */
public class ListVersionRRTest
{

	/**
	 * tests that the result from list-version is valid.
	 *
	 * @throws IOException
	 */
	@Test
	public void testVersion() throws IOException
	{
		JFVClient c = new JFVClient();
		Gson gson = JFVClient.getGson();

		EmptyFVRpcRequest lsr = new EmptyFVRpcRequest(
				EmptyFVRpcRequest.NoParamType.list_version);
		String response = c.send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<Version>>()
		{
		}.getType();
		FVRpcResponse<Version> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			fail("Should not be an error response.");
		}
		Version v = resp.getResult();
		assertNotNull(v.getDbVersion());
		assertNotNull(v.getFvVersion());
	}

}
