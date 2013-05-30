/**
 *
 */
package org.flowcreator.data;

import net.minidev.json.JSONObject;

/**
 * @author Niklas Rehfeld
 *
 */
public class FlowSpace extends JSONObject
{
	public FlowSpace(String name, String dpid, int priority, MatchStruct match, SliceAction[] actions)
	{
		put("dpid", dpid);
		put("name", name);
		put("priority", new Integer(priority));
		put("match", match); //otherwise it treats it as a Map, and doesn't call toString.
		put("slice-action", actions);
	}

}
