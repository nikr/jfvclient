/**
 *
 */
package org.flowcreator.queries;

import java.util.Collection;
import java.util.Map;


/**
 *
 * @author Niklas Rehfeld
 *
 */
public class Version extends FVRpcQueryObject
{
	private String flowvisorVersion;
	private String dbVersion;


	public Version()
	{
		super("list-version", "version-" + Math.round(Math.random() * 1000));
//		request.
	}

	@Override
	public String toString()
	{
		String s = "FV Version: " + flowvisorVersion + ", ";
		s += "DB Version: " + dbVersion;
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
