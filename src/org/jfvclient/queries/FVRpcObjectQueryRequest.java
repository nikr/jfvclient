/**
 *
 */
package org.jfvclient.queries;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @author Niklas Rehfeld
 *
 */
public abstract class FVRpcObjectQueryRequest extends FVRpcQueryRequest
{

	protected JSONObject params;

	/**
	 * @param hostURL
	 * @param method
	 * @param id
	 */
	protected FVRpcObjectQueryRequest(String hostURL, String method, String id)
	{
		super(hostURL, method, id);
		params = new JSONObject();

		put(PARAMS, params);
	}

	protected FVRpcObjectQueryRequest(String hostURL, String method)
	{
		super(hostURL, method);
	}

	public void setParams(JSONObject params)
	{
		put(PARAMS, params);
	}

	public JSONObject getParams()
	{
		Object o = get(PARAMS);
		if (o instanceof JSONObject)
		{
			return (JSONObject) o;
		} else
		{
			remove(PARAMS);//something's broken, so get rid of it.
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jfvclient.queries.FVRpcQueryRequest#getParameterType()
	 */
	@Override
	public ParamType getParameterType()
	{
		return ParamType.OBJECT;
	}

}
