/**
 *
 */
package org.jfvclient.queries;

import java.util.Map;


/**
 *
 * @author Niklas Rehfeld
 *
 */
public class ListVersionQuery extends FVRpcQueryObject
{
	private String flowvisorVersion;
	private String dbVersion;


	public ListVersionQuery()
	{
		super("list-version", "version-" + Math.round(Math.random() * 1000));
//		request.
	}

	@Override
	public String toString()
	{
		String s = "FV ListVersionQuery: " + flowvisorVersion + ", ";
		s += "DB ListVersionQuery: " + dbVersion;
		return s;
	}

	@Override
	public String getMethod()
	{
		return "list-version";
	}

	@Override
	public Map<String, Object> getParameters()
	{
		return null;
	}

	@Override
	public int getMinParameterCount()
	{
		return 0;
	}




}
