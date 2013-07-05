package org.jfvclient;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;

import org.jfvclient.responses.Version;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ListVersionRRTest
{

	@Test
	public void testVersion() throws IOException
	{
		JFVClient c = new JFVClient();
		Gson gson = JFVClient.getGson();

		FVRpcRequest<Object> lsr = new FVRpcRequest<Object>(
				FVRpcRequest.NoParamType.list_version);
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