/**
 *
 */
package org.jfvclient.queries;

import net.minidev.json.JSONArray;

/**
 * A FVRpcQueryRequest whose parameters are an array.
 *
 * @author Niklas Rehfeld
 *
 */
public abstract class FVRpcArrayQueryRequest extends FVRpcQueryRequest
{

	protected JSONArray params;
	
	/**
	 * @param hostURL
	 * @param method
	 * @param id
	 */
	public FVRpcArrayQueryRequest(String hostURL, String method, String id)
	{
		super(hostURL, method, id);
		params = new JSONArray();
		put(PARAMS, params);
		// TODO Auto-generated constructor stub
	}

	protected FVRpcArrayQueryRequest(String hostURL, String method)
	{
		super(hostURL, method);
	}

	public void setParams(JSONArray params)
	{
//		this.params = params;
		put(PARAMS, params);
	}

	public JSONArray getParams()
	{
		Object p = get(PARAMS);
		if (p != null && p instanceof JSONArray)
			return (JSONArray ) p;
		remove(PARAMS);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jfvclient.queries.FVRpcQueryRequest#getParameterType()
	 */
	@Override
	public ParamType getParameterType()
	{
		return ParamType.ARRAY;
	}

}
