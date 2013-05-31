/**
 *
 */
package org.jfvclient.queries;

import net.minidev.json.JSONObject;

/**
 *
 *
 * @author Niklas Rehfeld
 *
 */
public abstract class FVRpcQueryRequest extends JSONObject
{
	public enum ParamType {
		ARRAY, OBJECT, NONE
	};

	private String hostUrl;

	protected static final String JSONRPC = "jsonrpc";
	protected static final String METHOD = "method";
	protected static final String ID = "id";
	protected static final String PARAMS = "params";

	protected FVRpcQueryRequest(String hostURL, String method, String id)
	{
		super();
		this.hostUrl = hostURL;
		put(JSONRPC, "2.0");
		put(METHOD, method);
		put(ID, id);
		put(PARAMS, "");
	}

	protected FVRpcQueryRequest(String hostURL, String method)
	{
		this(hostURL, method, method + "-" + Math.round(Math.random() * 1000));
	}




	public String getHostURL()
	{
		return hostUrl;
	}

	public void setHostURL(String hostURL)
	{
		this.hostUrl = hostURL;
	}

	/**
	 *
	 * @return The type of 'result' object that the response is expected to
	 *         return.
	 */
	public abstract ParamType returns();

	/**
	 * Subclasses should override this. For queries that use parameters, use
	 * {@see FVRpcArrayQueryRequest} or {@see FVRpcObjectQueryRequest} as the
	 * superclass instead.
	 *
	 * @return The type of parameter that this request contains.
	 */
	public ParamType getParameterType()
	{
		return ParamType.NONE;
	}

}
