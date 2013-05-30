/**
 *
 */
package org.jfvclient.queries;

/**
 * @author Niklas Rehfeld
 *
 */
public class FVRpcQueryFactory
{

	public static FVRpcQueryObject getObject(String method)
	{
		if (method.equalsIgnoreCase("list-version")
				|| method.equalsIgnoreCase("version"))
		{
			return new Version();
		}
		if (method.equalsIgnoreCase("add-slice"))
			return new AddSliceRequest();
		else
			return null;
	}

	// public FVRpcQueryObject fromJsonString(String jsonString)
	// {
	// JSONRPC2Parser p = new JSONRPC2Parser();
	// JSONRPC2Request r = p.parseJSONRPC2Request(jsonString);
	//
	// if (r.getMethod().equals("list-version"))
	// {
	//
	// }
	// }

}
