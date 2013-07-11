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

import org.jfvclient.responses.LinksList;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Niklas Rehfeld
 *
 */
public class ListLinksRRTest
{

	JFVClient c = new JFVClient();

	/**
	 *
	 * Result should be
	 * <pre>
	 * [
	 * {"srcPort":"1","dstPort":"3","dstDPID":"00:00:00:00:00:00:00:06","srcDPID":"00:00:00:00:00:00:00:05"},
	 * {"srcPort":"3","dstPort":"1","dstDPID":"00:00:00:00:00:00:00:05","srcDPID":"00:00:00:00:00:00:00:06"},
	 * {"srcPort":"2","dstPort":"3","dstDPID":"00:00:00:00:00:00:00:07","srcDPID":"00:00:00:00:00:00:00:05"},
	 * {"srcPort":"3","dstPort":"2","dstDPID":"00:00:00:00:00:00:00:05","srcDPID":"00:00:00:00:00:00:00:07"}
	 * ]
	 * </pre>
	 *
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@Test
	public void test() throws MalformedURLException, IOException
	{

		Gson g = TestUtils.getGson();
		EmptyFVRpcRequest llRequest = new EmptyFVRpcRequest(
				EmptyFVRpcRequest.NoParamType.list_links, "ll1");
		String resp = c.send(g, llRequest);

		FVRpcResponse<LinksList> res = null;
		Type respType = new TypeToken<FVRpcResponse<LinksList>>()
		{
		}.getType();
		res = g.fromJson(resp, respType);

		assertTrue("Result should not be an error ", !res.isError());
		LinksList d = res.getResult();

		assertEquals(4, d.size());

		// need to think of some way of testing the results properly. need some
		// way of making new Link objects to compare to.

	}

}
