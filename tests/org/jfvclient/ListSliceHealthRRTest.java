/*
 * Copyright 2013 Niklas Rehfeld.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
		String response = c.send(lsr);
		Type t = new TypeToken<FVRpcResponse<SliceHealth>>()
		{
		}.getType();
		FVRpcResponse<SliceHealth> resp = gson.fromJson(response, t);

		assertFalse("Response should not be an error." , resp.isError());
	}

}
