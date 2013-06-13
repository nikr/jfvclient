/**
 *
 */
package org.jfvclient;

import java.io.IOException;
import java.lang.reflect.Type;

import org.jfvclient.responses.FVHealth;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author rehfelnikl
 * 
 */
public class ListFVHealthRRTest
{

	@Test
	public void test() throws IOException
	{
		JFVClient c = new JFVClient();

		Gson gson = JFVClient.getGson();
		FVRpcRequest lsr = new FVRpcRequest(
				FVRpcRequest.NoParamType.list_fv_health);
		String response = c.send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<FVHealth>>()
		{
		}.getType();
		FVRpcResponse<FVHealth> resp = gson.fromJson(response, t);

		assertFalse("should not be an error response", resp.isError());
		assertNotNull("response body should not be null", resp.getResult());

	}

}
