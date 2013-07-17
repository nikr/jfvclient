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
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;

import org.jfvclient.data.Dpid;
import org.jfvclient.data.Flowspace;
import org.jfvclient.data.MatchStruct;
import org.jfvclient.data.SliceAction;
import org.jfvclient.requests.AddFlowspace;
import org.jfvclient.requests.RemoveFlowspace;
import org.jfvclient.requests.UpdateFlowspace;
import org.jfvclient.testing.TestUtils;
import org.junit.AfterClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author Niklas Rehfeld
 *
 */
public class FlowspaceRRTests
{

	private Type booleanResponseType = new TypeToken<FVRpcResponse<Boolean>>()
	{
	}.getType();

	/**
	 * removes the flowspaces that were created by running the tests. This
	 * method may be necessary, because it's not predictable in which order the
	 * tests will run.
	 */
	@AfterClass
	public static void cleanup()
	{
		JFVClient c = new JFVClient();
		try
		{
			c.removeFlowspace("flowspace_foo");
		} catch (Exception e)
		{
			// fine. ignore.
		}
	}

	/**
	 * test addition of a single flowspace.
	 *
	 * @throws IOException
	 */
	@Test
	public void testAddFlowspaceSingle() throws IOException
	{
		MatchStruct m = new MatchStruct();
		// m.put("in_port", "all");
		SliceAction a = new SliceAction("fvadmin", 6);

		// Gson gson = TestUtils.getGson();
		// JFVClient c = new JFVClient();

		Flowspace fs = new Flowspace("flowspace_foo", new Dpid(5L),
				new Integer(10), m, Collections.singletonList(a));
		AddFlowspace afs = new AddFlowspace();
		afs.add(fs);
		FVRpcResponse<Integer> resp = addFlowspace(afs);
		assertFalse(resp.isError());

		// TODO check the response and call list-fs-status to see if the call
		// succeeded.
	}

	/**
	 * test updating a valid, existing flowspace.
	 *
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	@Test
	public void testUpdateExistingFlowspace() throws IOException,
			JFVErrorResponseException
	{
		System.out.println("Update existing Flowspace");
		// create a new one if it's not there.
		checkAndCreateTestFlowspace();
		Flowspace fs = new Flowspace("flowspace_foo", new Dpid(5L));

		MatchStruct m = new MatchStruct();
		m.put("in_port", 2);

		fs.setMatch(m);
		fs.setPriority(10); // needed because of a bug in FlowVisor.

		UpdateFlowspace u = new UpdateFlowspace();
		u.add(fs);
		FVRpcResponse<Integer> resp = updateFlowspace(u);
		if (resp.isError())
		{
			System.err.println("error message: ");
			System.err.println(resp.getError().getReason());
			System.err.println(resp.getError().getMsg());
		}
		assertFalse("should not be an error response ", resp.isError());


	}

	/**
	 * test removal of an existing, valid flowspace.
	 *
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	@Test
	public void testRemoveExistingFlowspace() throws IOException,
			JFVErrorResponseException
	{

//		Flowspace fs = new Flowspace("flowspace_foo", new Dpid(5L));
		// create a new one if it's not there.
		checkAndCreateTestFlowspace();

		FVRpcResponse<Boolean> resp = removeFlowspace(new RemoveFlowspace(
				"flowspace_foo"));
		assertFalse("should not be an error response", resp.isError());
		// assertTrue("result should be >0 ", resp.getResult().intValue() > 0);
		assertTrue("result should be true.", resp.getResult());

	}

	/**
	 * creates a new flowspace called <code>flowspace_foo</code> if it doesn't
	 * already exist.
	 *
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	private void checkAndCreateTestFlowspace() throws IOException,
			JFVErrorResponseException
	{
		MatchStruct m = new MatchStruct();
		SliceAction a = new SliceAction("fvadmin", 6);
//		Gson gson = TestUtils.getGson();
		JFVClient c = new JFVClient();

		Flowspace fs = new Flowspace("flowspace_foo", new Dpid(5L),
				new Integer(10), m, Collections.singletonList(a));

		if (!c.listAllFlowspaces(true).contains(fs))
		{
			System.out.println("creating a new flowspace...");
			AddFlowspace af = new AddFlowspace();
			af.add(fs);
			addFlowspace(af);
		}

	}

	private FVRpcResponse<Integer> updateFlowspace(UpdateFlowspace flowspaces)
			throws IOException
	{
		Gson gson = TestUtils.getGson();
		JFVClient c = new JFVClient();
		FVRpcRequest<UpdateFlowspace> ufs = new FVRpcRequest<UpdateFlowspace>(
				flowspaces);
		String response = c.send(ufs);
		return gson.fromJson(response, new TypeToken<FVRpcResponse<Integer>>()
		{
		}.getType());

	}

	private FVRpcResponse<Integer> addFlowspace(AddFlowspace flowspaces)
			throws IOException
	{
		Gson gson = TestUtils.getGson();
		JFVClient c = new JFVClient();
		FVRpcRequest<AddFlowspace> afr = new FVRpcRequest<AddFlowspace>(
				flowspaces);
		String response = c.send(afr);
		return gson.fromJson(response, new TypeToken<FVRpcResponse<Integer>>()
		{
		}.getType());
	}

	private FVRpcResponse<Boolean> removeFlowspace(RemoveFlowspace flowspaces)
			throws IOException
	{
		Gson gson = TestUtils.getGson();
		JFVClient c = new JFVClient();
		FVRpcRequest<RemoveFlowspace> afr = new FVRpcRequest<RemoveFlowspace>(
				flowspaces);
		String response = c.send(afr);
		return gson.fromJson(response, booleanResponseType);
	}

}
