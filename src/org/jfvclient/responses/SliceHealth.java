/**
 *
 */
package org.jfvclient.responses;

import java.util.List;

import org.jfvclient.data.Dpid;

import com.google.gson.annotations.SerializedName;

/**
 *
 * response to a <code>list-slice-health</code> query. corresponds to the JSON:
 *
 * <pre>
 * {
 *   "is-connected" : <boolean>,
 *   "connect-drop-count" : <number>,
 *   "fs-entries" : <number>,
 *   "connected-dpids" : [ <dpid> ]
 * }
 * </pre>
 *
 * @author Niklas Rehfeld
 *
 */
public class SliceHealth
{
	@SerializedName("is-connected")
	boolean isConnected;
	@SerializedName("connect-drop-count")
	int connectDropCount;
	@SerializedName("fs-entries")
	int fsEntries;
	@SerializedName("connected-dpids")
	List<Dpid> connectedDpids;

	/**
	 * @return the isConnected
	 */
	public boolean isConnected()
	{
		return isConnected;
	}

	/**
	 * @return the connectDropCount
	 */
	public int getConnectDropCount()
	{
		return connectDropCount;
	}

	/**
	 * @return the fsEntries
	 */
	public int getFsEntries()
	{
		return fsEntries;
	}

	/**
	 * @return the connectedDpids
	 */
	public List<Dpid> getConnectedDpids()
	{
		return connectedDpids;
	}
}
