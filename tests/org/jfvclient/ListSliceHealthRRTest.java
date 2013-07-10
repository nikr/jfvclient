/**
 *
 */
package org.jfvclient;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.lang.reflect.Type;

import org.jfvclient.requests.ListSliceHealth;
import org.jfvclient.responses.SliceHealth;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Niklas Rehfeld
 *
 */
public class ListSliceHealthRRTest
{

	/**
	 * test.
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException
	{
		Gson gson =JFVClient.getGson();
		JFVClient c = new JFVClient();
//		JsonObject o = new JsonObject();
		FVRpcRequest<ListSliceHealth> lsr = new FVRpcRequest<ListSliceHealth>("list-slice-health"
				,new ListSliceHealth("fvadmin"));
		String response = c.send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<SliceHealth>>()
		{
		}.getType();
		FVRpcResponse<SliceHealth> resp = gson.fromJson(response, t);

		assertFalse("Response should not be an error." , resp.isError());
	}

}
