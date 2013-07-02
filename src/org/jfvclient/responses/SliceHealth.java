/*
 * Copyright 2013 Niklas Rehfeld.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
