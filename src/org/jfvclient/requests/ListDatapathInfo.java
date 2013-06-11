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

package org.jfvclient.requests;

import org.jfvclient.data.Dpid;

/**
 *
 * @author Niklas Rehfeld
 */
public class ListDatapathInfo
{
	private Dpid dpid;

	public ListDatapathInfo()
	{
	}

	public ListDatapathInfo(Dpid d)
	{
		dpid = d;
	}

	public String getDpid()
	{
		return dpid.getDpid();
	}

	/**
	 *
	 * @param d The DPID of the datapath to query. Note, this must identify a
	 *            unique device, so cannot be "any", "all" or "ALL_DPIDS"
	 */
	public void setDpid(Dpid d)
	{
		dpid = d;
	}
}
