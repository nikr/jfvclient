package org.jfvclient;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;

import org.jfvclient.data.Dpid;
import org.jfvclient.responses.DataPaths;
import org.jfvclient.responses.DatapathInfo;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ListDatapathRRTest
{

	JFVClient c = new JFVClient();
	@Test
	public void testValidRequest() throws MalformedURLException, IOException
	{
		Gson g = TestUtils.getGson();
		FVRpcRequest ldiRequest = new FVRpcRequest(FVRpcRequest.NoParamType.list_datapaths, "ldp1");
		String resp = c.send(g,ldiRequest);

		FVRpcResponse<DataPaths> res = null;
        Type respType = new TypeToken<FVRpcResponse<DataPaths>>()
                {
                }.getType();
        res = g.fromJson(resp, respType);

        assertTrue("Result should not be an error ", !res.isError());
        DataPaths d = res.getResult();
//        first dpid shoud be 00::06.
//        assertEquals(d.get(0), new Dpid(Dpid.toDpid(6)));
        assertTrue("Should contain DPID 00::05", d.contains(new Dpid(Dpid.toDpid(5))));
        assertTrue("Should contain DPID 00::06", d.contains(new Dpid(Dpid.toDpid(6))));
        assertTrue("Should contain DPID 00::07", d.contains(new Dpid(Dpid.toDpid(7))));
        assertEquals("Unexpected number of DPIDs in results", d.size(), 3);

	}



}
