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

	/**
	 * test getting the flowvisor health status.
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException
	{
		JFVClient c = new JFVClient();

		Gson gson = JFVClient.getGson();
		EmptyFVRpcRequest lsr = new EmptyFVRpcRequest(
				EmptyFVRpcRequest.NoParamType.list_fv_health);
		String response = c.send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<FVHealth>>()
		{
		}.getType();
		FVRpcResponse<FVHealth> resp = gson.fromJson(response, t);

		assertFalse("should not be an error response", resp.isError());
		assertNotNull("response body should not be null", resp.getResult());

	}

}
