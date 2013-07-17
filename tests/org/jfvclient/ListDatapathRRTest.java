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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;

import org.jfvclient.data.Dpid;
import org.jfvclient.responses.DataPaths;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author Niklas Rehfeld
 *
 */
public class ListDatapathRRTest
{

	JFVClient c = new JFVClient();


	/**
	 * test listing the devices on the test network.
	 *
	 * @see org.jfvclient.RequestResponseTests for details on the test network.
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@Test
	public void testValidRequest() throws MalformedURLException, IOException
	{
		Gson g = TestUtils.getGson();
		EmptyFVRpcRequest ldiRequest = new EmptyFVRpcRequest(
				EmptyFVRpcRequest.NoParamType.list_datapaths);
		String resp = c.send(ldiRequest);

		FVRpcResponse<DataPaths> res = null;
		Type respType = new TypeToken<FVRpcResponse<DataPaths>>()
		{
		}.getType();
		res = g.fromJson(resp, respType);

		assertTrue("Result should not be an error ", !res.isError());
		DataPaths d = res.getResult();
		// first dpid shoud be 00::06.
		// assertEquals(d.get(0), new Dpid(Dpid.toDpid(6)));
		assertTrue("Should contain DPID 00::05",
				d.contains(new Dpid(5L)));
		assertTrue("Should contain DPID 00::06",
				d.contains(new Dpid(6L)));
		assertTrue("Should contain DPID 00::07",
				d.contains(new Dpid(7L)));
		assertEquals("Unexpected number of DPIDs in results", d.size(), 3);

	}

}
