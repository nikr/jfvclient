/**
 *
 */
package org.jfvclient.data;

import net.minidev.json.JSONObject;

/**
 * @author Niklas Rehfeld
 *
 */
public class SliceAction extends JSONObject
{
//	private String name;
//	private int permission;
	public SliceAction(String sliceName, Integer permission)
	{
		put("slice-name", sliceName);
		put("permission", permission);
	}

}
