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

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;

import org.jfvclient.data.Dpid;
import org.jfvclient.requests.ListDatapathInfo;
import org.jfvclient.responses.DatapathInfo;
import org.jfvclient.responses.ErrorResponse;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author rehfelnikl
 *
 */
public class ListDatapathInfoRRTest
{

	private JFVClient c = new JFVClient();

	/**
	 * tests getting info on a valid, existant device.
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@Test
	public void testExisting() throws MalformedURLException, IOException
	{
		Gson g = TestUtils.getGson();
		//list datapath info for device 0::0:06
		ListDatapathInfo ldi = new ListDatapathInfo(new Dpid(6L));
		FVRpcRequest<ListDatapathInfo> ldiRequest = new FVRpcRequest<ListDatapathInfo>("list-datapath-info", "ldpi1", ldi);
		String resp = c.send(g,ldiRequest);

		FVRpcResponse<DatapathInfo> res = null;
        Type respType = new TypeToken<FVRpcResponse<DatapathInfo>>()
                {
                }.getType();
        res = g.fromJson(resp, respType);
        if (res.isError())
        {
        	fail("result should not be an error.");
        }
        DatapathInfo d = res.getResult();
        assertEquals("Expecting DPID = " + d.getDpid(), new Dpid(6L).getDpid(), d.getDpid());
        assertTrue("Expected numPorts > 0", d.getNumPorts() > 0);


	}

	/**
	 * tests getting info on a nonexistant device.
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@Test
	public void testNonExisting() throws MalformedURLException, IOException
	{
		Gson g = TestUtils.getGson();
		ListDatapathInfo ldi = new ListDatapathInfo(new Dpid(23L));
		FVRpcRequest<ListDatapathInfo> ldiRequest = new FVRpcRequest<ListDatapathInfo>("list-datapath-info", "ldpi23", ldi);
		String resp = c.send(g,ldiRequest);

		FVRpcResponse<DatapathInfo> res = null;
        Type respType = new TypeToken<FVRpcResponse<DatapathInfo>>()
                {
                }.getType();
        res = g.fromJson(resp, respType);
        assertTrue("Expected an error response, got " + res, res.isError());
        //expect a "invalid parameters" error (-32602) .
        assertEquals("Expected ErrorResponse code -32602, got " + res.getError().getCode(), res.getError().getCode(), ErrorResponse.INVALID_PARAMS);
//        if (res.isError())
//        {
//        	fail("result should not be an error.");
//        }
//        DatapathInfo d = res.getResult();
//        assertEquals("Expecting DPID = " + d.getDpid(), d.getDpid(), Dpid.toDpid(1));
//        assertTrue("Expected numPorts > 0", d.getNumPorts() > 0);


	}


}
