/**
 *
 */
package org.flowcreator.data;

import java.io.ObjectOutputStream.PutField;

import net.minidev.json.JSONArray;
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
