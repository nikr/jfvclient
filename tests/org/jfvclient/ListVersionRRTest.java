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
		String response = c.send(lsr);
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
