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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Type;

import org.jfvclient.requests.AddSlice;
import org.jfvclient.requests.RemoveSlice;
import org.jfvclient.requests.UpdateSlice;
import org.jfvclient.requests.UpdateSlicePassword;
import org.jfvclient.responses.Slice;
import org.jfvclient.responses.SliceList;
import org.jfvclient.testing.TestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Set of tests that do things to slices. These include <code>add-slice</code>,
 * <code>update-slice</code> and <code>remove-slice</code>.
 *
 *
 * @author Niklas Rehfeld
 *
 */
public class SliceOperationsRRTest
{
	private static final String PASSWORDTESTSLICE = "passwordtestslice";

	static Type booleanResponseType;

	private String slicename = "testslice123";
	private JFVClient c = new JFVClient();

	/**
	 * creates booleanresponsetype.
	 */
	@BeforeClass
	public static void setup()
	{
		booleanResponseType = new TypeToken<FVRpcResponse<Boolean>>()
		{
		}.getType();
	}

	/**
	 * cleans up slices that were created by the tests.
	 *
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	@AfterClass
	public static void cleanup() throws IOException, JFVErrorResponseException
	{
		JFVClient c = new JFVClient();
		SliceList l = c.listSlices();
		for (Slice s : l)
		{
			if (s.getSlice_name().startsWith("testslice123"))
			{
				c.removeSlice(s.getSlice_name());
			}
		}
		c.removeSlice(PASSWORDTESTSLICE);
	}

	/**
	 * test adding a new slice.
	 *
	 * @throws IOException
	 */
	@Test
	public void testAddNew() throws IOException
	{
		// if the slice already exists, add some rubbish to the end.
		if (checkExists(slicename))
		{
			slicename += Long.toHexString(Math.round(Math.random() * 10000));
		}
		FVRpcResponse<Boolean> reso = addSlice(slicename);

		assertFalse("Result should not be an error", reso.isError());
		Boolean b = reso.getResult();
		assertTrue("Expected 'true' result", b.booleanValue());
	}

	/**
	 * test adding a slice that already exists.
	 *
	 * @throws IOException
	 */
	@Test
	public void testAddDuplicate() throws IOException
	{
		if (!checkExists(slicename))
		{
			addSlice(slicename);
		}
		assertTrue("slice still doesn't exist after adding it. I quit.",
				checkExists(slicename));

		FVRpcResponse<Boolean> reso = addSlice(slicename);

		assertTrue("Result should be an error", reso.isError());
		assertEquals("Result code should be -32602 / invalid parameters",
				-32602, reso.getError().getCode());

	}

	/**
	 * test updating avalid slice.
	 *
	 * @throws IOException
	 */
	@Test
	public void testUpdateValid() throws IOException
	{
		if (!checkExists(slicename))
		{
			addSlice(slicename);
		}
		assertTrue("slice still doesn't exist after adding it. I quit.",
				checkExists(slicename));

		FVRpcResponse<Boolean> res = updateSlice(slicename);
		assertFalse("Should not be an error response.", res.isError());
		assertTrue("Update returned false, expected true", res.getResult());

	}

	/**
	 * test removing a valid slice.
	 *
	 * @throws IOException
	 */
	@Test
	public void testRemoveValidSlice() throws IOException
	{
		if (!checkExists(slicename))
		{
			addSlice(slicename);
		}
		assertTrue("slice still doesn't exist after adding it. I quit.",
				checkExists(slicename));

		FVRpcResponse<Boolean> res = removeSlice(slicename);
		assertFalse("Should not be an error response.", res.isError());
		assertTrue("Update returned false, expected true", res.getResult());

	}

	/**
	 * test updating a valid slice's password.
	 *
	 * @throws IOException
	 */
	@Test
	public void testUpdateSlicePassword() throws IOException
	{
		addSlice(PASSWORDTESTSLICE);
		FVRpcResponse<Boolean> response = updateSlicePassword(
				PASSWORDTESTSLICE, "newpassword");
		assertFalse("Should not be an error response.", response.isError());
		assertTrue("Password change did not return true.", response.getResult());

	}

	/**
	 * check if a slice of the given name exists in the flowvisor.
	 *
	 * @param slicename
	 *            the name of the slice.
	 * @return true if list-slices contains the slice name.
	 */
	private boolean checkExists(String slicename)
	{
		try
		{
			return c.listSlices().contains(new Slice(slicename));
		} catch (Exception e)
		{
			System.err.println(e.getLocalizedMessage());
			return false;
		}
	}

	private FVRpcResponse<Boolean> addSlice(String name) throws IOException
	{
		// add a new slice with a random port, so that the controller url is
		// different each time, as FV will not add multiple slices for a single
		// controller.
		AddSlice a = new AddSlice(name, "tcp:localhost:"
				+ Math.round(Math.random() * 10000), "test@localhost",
				"testpassword");
		Gson g = TestUtils.getGson();
		FVRpcRequest<AddSlice> asr = new FVRpcRequest<AddSlice>(a);
		String res = c.send(g, asr);

		return g.fromJson(res, booleanResponseType);

	}

	private FVRpcResponse<Boolean> updateSlice(String name) throws IOException
	{
		UpdateSlice u = new UpdateSlice(name, "test@localhost");
		u.setAdminStatus(false);
		Gson g = TestUtils.getGson();
		FVRpcRequest<UpdateSlice> asr = new FVRpcRequest<UpdateSlice>(u);
		String res = c.send(g, asr);

		return g.fromJson(res, booleanResponseType);

	}

	private FVRpcResponse<Boolean> removeSlice(String name) throws IOException
	{
		RemoveSlice rs = new RemoveSlice(name);
		Gson g = TestUtils.getGson();
		FVRpcRequest<RemoveSlice> asr = new FVRpcRequest<RemoveSlice>(rs);
		String res = c.send(g, asr);

		return g.fromJson(res, booleanResponseType);

	}

	private FVRpcResponse<Boolean> updateSlicePassword(String sliceName,
			String password) throws IOException
	{
		Gson g = TestUtils.getGson();
		FVRpcRequest<UpdateSlicePassword> usp = new FVRpcRequest<UpdateSlicePassword>(
				new UpdateSlicePassword(sliceName, password));
		String response = c.send(g, usp);
		return g.fromJson(response, booleanResponseType);

	}

}
