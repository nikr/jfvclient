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

import org.jfvclient.requests.AddSlice;
import org.jfvclient.requests.ListDatapathInfo;
import org.jfvclient.requests.ListSliceInfo;

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
 *            the &lt;request paramerters &gt; type.
 * @author Niklas Rehfeld
 */
public class FVRpcRequest<V>
{

	private String method;
	private String id;
	private V params;
	private final String jsonrpc = "2.0";

	public FVRpcRequest(String method, String id, V params)
	{
		this.method = method;
		this.id = id;
		this.params = params;

	}

	public String getID()
	{
		return id;
	}

	public String getMethod()
	{
		return method;
	}

	public V getParam()
	{
		return params;
	}

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
	 * So maybe not that useful, and more likely to be a source of errors...
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
		}
		else if (params instanceof ListSliceInfo)
		{
			this.method = "list-slice-info";
		}
		else
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
	 *            a request ID.
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
