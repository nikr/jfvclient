package org.jfvclient;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.jfvclient.data.Flowspace;
import org.jfvclient.requests.ListFlowspace;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 *
 * @author Niklas Rehfeld
 *
 */
public class ListFlowspaceRRTest
{

	/**
	 * tests llisting all of the flowspaces including disabled ones.
	 * @throws IOException
	 */
	@Test
	public void testListAllFlowspacesIncludingDisabled() throws IOException
	{
		JFVClient c = new JFVClient();
		Gson gson = JFVClient.getGson();
		ListFlowspace f = new ListFlowspace(true);
		FVRpcRequest<ListFlowspace> afr = new FVRpcRequest<ListFlowspace>(f);
		String response = c.send(gson, afr);
		Type t = new TypeToken<FVRpcResponse<List<Flowspace>>>()
		{
		}.getType();
		FVRpcResponse<List<Flowspace>> resp = gson.fromJson(response, t);
		assertFalse("should not be an error response", resp.isError());

	}

	/**
	 * tests listing all flowspaces, except disabled ones.
	 * @throws IOException
	 */
	@Test
	public void testListAllFlowspacesExcludingDisabled() throws IOException
	{
		JFVClient c = new JFVClient();
		Gson gson = JFVClient.getGson();
		ListFlowspace f = new ListFlowspace(false);
		FVRpcRequest<ListFlowspace> afr = new FVRpcRequest<ListFlowspace>(f);
		String response = c.send(gson, afr);
		Type t = new TypeToken<FVRpcResponse<List<Flowspace>>>()
		{
		}.getType();
		FVRpcResponse<List<Flowspace>> resp = gson.fromJson(response, t);
		assertFalse("should not be an error response", resp.isError());

	}

	/**
	 * test listing non-existent flowspaces.
	 * @throws IOException
	 */
	@Test
	public void testListNonexistantFlowspaces() throws IOException
	{
		JFVClient c = new JFVClient();
		Gson gson = JFVClient.getGson();
		ListFlowspace f = new ListFlowspace("NonexistantFlowspaceNameHopefully", true);
		FVRpcRequest<ListFlowspace> afr = new FVRpcRequest<ListFlowspace>(f);
		String response = c.send(gson, afr);
		Type t = new TypeToken<FVRpcResponse<List<Flowspace>>>()
		{
		}.getType();
		FVRpcResponse<List<Flowspace>> resp = gson.fromJson(response, t);
		assertFalse("should not be an error response", resp.isError());
		assertTrue(resp.getResult().isEmpty());

	}

}
