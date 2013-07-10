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

import org.jfvclient.requests.AddFlowspace;
import org.jfvclient.requests.AddSlice;
import org.jfvclient.requests.ListDatapathInfo;
import org.jfvclient.requests.ListFlowspace;
import org.jfvclient.requests.ListSliceHealth;
import org.jfvclient.requests.ListSliceInfo;
import org.jfvclient.requests.ListSliceStats;
import org.jfvclient.requests.RemoveFlowspace;
import org.jfvclient.requests.RemoveSlice;
import org.jfvclient.requests.UpdateFlowspace;
import org.jfvclient.requests.UpdateSlice;
import org.jfvclient.requests.UpdateSlicePassword;

import com.google.gson.JsonNull;

/**
 * This class represents a JSON-RPC request. it takes the form
 *
 * <pre>
 * {
 * "method" : &lt;method&gt;
 * "id" : &lt;id&gt;
 * "params" : &lt request parameters &gt;
 * "jsonrpc" : "2.0"
 * }
 * </pre>
 *
 * @param <V>
 *            the &lt;request parameters &gt; type.
 * @author Niklas Rehfeld
 */
public class FVRpcRequest<V>
{

	private String method;
	private String id;
	private V params;
	@SuppressWarnings("unused")
	private final String jsonrpc = "2.0";

	/**
	 *
	 * @param method
	 *            The JSON-RPC method
	 * @param id
	 *            the request ID
	 * @param params
	 *            the parameters.
	 */
	public FVRpcRequest(String method, String id, V params)
	{
		this.method = method;
		this.id = id;
		this.params = params;

	}

	/**
	 *
	 * @return The request ID
	 */
	public String getID()
	{
		return id;
	}

	/**
	 *
	 * @return the JSON_RPC method name
	 */
	public String getMethod()
	{
		return method;
	}

	/**
	 *
	 * @return the request parameters
	 */
	public V getParam()
	{
		return params;
	}

	/**
	 * Creates a request with a generated ID. The ID is "method-xxx", where xxx
	 * is a random number.
	 *
	 * @param method
	 *            the JSON-RPC method
	 * @param params
	 *            the parameters
	 */
	public FVRpcRequest(String method, V params)
	{
		this(method, method + Math.round(Math.random() * 1000), params);
	}

	/**
	 * This constructor will attempt to work out the method name from the
	 * parameter type. It will only work for known parameter types, and not for
	 * ones where multiple types can have the same type of parameters. Also not
	 * for ones with no parameters.
	 *
	 * It will also generate a random request ID, the same as {@link #FVRpcRequest(String, Object)}
	 *
	 * @param params
	 *            The parameters object that is the body of the request.
	 */
	public FVRpcRequest(V params)
	{
		setMethod(params);
		this.id = method + Math.round(Math.random() * 1000);
		this.params = params;
	}

	private void setMethod(V params)
	{
		if (params instanceof AddSlice)
		{
			this.method = "add-slice";
		} else if (params instanceof ListDatapathInfo)
		{
			this.method = "list-datapath-info";
		} else if (params instanceof ListSliceInfo)
		{
			this.method = "list-slice-info";
		} else if (params instanceof ListSliceHealth)
		{
			this.method = "list-slice-health";
		} else if (params instanceof ListSliceStats)
		{
			this.method = "list-slice-stats";
		} else if (params instanceof UpdateSlice)
		{
			this.method = "update-slice";
		} else if (params instanceof RemoveSlice)
		{
			this.method = "remove-slice";
		} else if (params instanceof AddFlowspace)
		{
			this.method = "add-flowspace";
		} else if (params instanceof ListFlowspace)
		{
			this.method = "list-flowspace";
		} else if (params instanceof UpdateSlicePassword)
		{
			this.method = "update-slice-password";
		} else if (params instanceof UpdateFlowspace)
		{
			this.method = "update-flowspace";
		} else if (params instanceof RemoveFlowspace)
		{
			this.method = "remove-flowspace";
		} else
		{
			throw new IllegalArgumentException("Unknown type: "
					+ params.getClass().getCanonicalName());
		}
	}

	/**
	 * Constructor used for 'empty' requests. These are
	 * <ul>
	 * <li />list-version
	 * <li />list-links
	 * <li />list-slices
	 * <li />list-datapaths
	 * <li />list-fv-health
	 * <li />save-config
	 * </ul>
	 *
	 * @see NoParamType
	 * @param t
	 *            the method
	 * @param id
	 *            a request ID. Must not be null.
	 */
	public FVRpcRequest(NoParamType t, String id)
	{

		switch (t)
		{
		case list_slices:
			method = "list-slices";
			break;
		case list_version:
			method = "list-version";
			break;
		case list_datapaths:
			method = "list-datapaths";
			break;
		case list_links:
			method = "list-links";
			break;
		case list_fv_health:
			method = "list-fv-health";
			break;
		case save_config:
			method = "save-config";
			break;
		default:
			throw new AssertionError(t.name());
		}
		this.id = id;
		params = (V) JsonNull.INSTANCE;

	}

	/**
	 * Creates a new request with no parameters and a generated request ID.
	 * @param t
	 */
	public FVRpcRequest(NoParamType t)
	{
		this(t, t.name() + Math.round(Math.random() * 1000));
	}

	/**
	 * Requests that require no parameters. Rather than implementing all of
	 * these as empty classes, it is easier to just enumerate them and have them
	 * as a special class of requests.
	 */
	public static enum NoParamType {

		/**
		 * list-slices request
		 */
		list_slices,
		/**
		 * list-version request
		 */
		list_version,
		/**
		 * list-datapaths request
		 */
		list_datapaths,
		/**
		 * list-links request
		 */
		list_links,
		/**
		 * list-fv-health request
		 */
		list_fv_health,
		/**
		 * save-config request
		 */
		save_config,
	}
}
