/**
 *
 */
package org.flowcreator.queries;

import java.util.Arrays;

import net.minidev.json.JSONArray;

import org.flowcreator.data.FlowSpace;

/**
 *
 * JSON Format:
 *
 * <pre>[
 {
   "name" : &lt;string&gt;,
   "dpid" : &lt;dpid&gt;,
   "priority" : &lt;number&gt;,
   "match" : &lt;match-struct&gt;,
   "queues" : [ &lt;queue_id&gt; ],
            OPTIONAL
   "force-enqueue" : &lt;queue_id&gt;,
            OPTIONAL
   "slice-action" :
   [
    {
      "slice-name" : &lt;name&gt; ",
      "permission" : &lt;perm-value&gt;
    }
   ]
 }
]</pre>
 *
 *where <pre>match-struct</pre> is
 *<table border="1">
 <tbody><tr>
 <td>KEY(s)
 </td><td>VALUE
 </td></tr>
 <tr>
 <td>"in_port","input_port"
 </td><td>integer
 </td></tr>
 <tr>
 <td>"dl_src","eth_src", "dl_dst","eth_dst"
 </td><td>hex-string
 </td></tr>
 <tr>
 <td>"dl_type", "dl_vlan", "dl_vlan_pcp"
 </td><td>integer
 </td></tr>
 <tr>
 <td>"nw_src", "nw_dst", "ip_src", "ip_dst"
 </td><td>CIDR-style netmask
 </td></tr>
 <tr>
 <td>"tp_src","tp_dst"
 </td><td>integer (max 64k)
 </td></tr>
 </tbody></table>
 *
 *returns a boolean (I assume it's true if successful).
 *
 * @author Niklas Rehfeld
 *
 */
public class AddFlowspaceRequest extends FVRpcQueryObject
{

	public AddFlowspaceRequest(FlowSpace[] fs){
		super("add-flowspace");

		JSONArray a = new JSONArray();
		a.addAll(Arrays.asList(fs));
//		JSONObject o = new JSONObject();
		setPositionalParams(a);
//		params.putAll(a);
	}

	public AddFlowspaceRequest()
	{
		super("add-flowspace");

	}

	/* (non-Javadoc)
	 * @see org.flowcreator.queries.FVRpcQueryObject#getParameterCount()
	 */
	@Override
	public int getMinParameterCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
