package org.jfvclient;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;

import org.jfvclient.data.Dpid;
import org.jfvclient.data.Flowspace;
import org.jfvclient.data.MatchStruct;
import org.jfvclient.data.SliceAction;
import org.jfvclient.requests.AddFlowspace;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AddFlowspaceRRTest
{

	@Test
	public void testAddFlowspaceSingle() throws IOException
	{
		MatchStruct m = new MatchStruct();
//		m.put("in_port", "all");
		SliceAction a = new SliceAction("fvadmin", 6);

		Gson gson = TestUtils.getGson();
JFVClient c = new JFVClient();

		Flowspace fs = new Flowspace("flowspace_foo", new Dpid(Dpid.toDpid(5)), new Integer(10), m, Collections.singletonList(a));
		AddFlowspace afs = new AddFlowspace();
		afs.add(fs);
		FVRpcRequest<AddFlowspace> afr = new FVRpcRequest<AddFlowspace>(afs);
		String response = c.send(gson, afr);
		Type t = new TypeToken<FVRpcResponse<Number>>()
		{
		}.getType();
		FVRpcResponse<Number> resp = gson.fromJson(response, t);
		assertFalse(resp.isError());

		//TODO check the response and call list-fs-status to see if the call succeeded.
	}

}
