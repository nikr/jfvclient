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
package org.jfvclient.data;

import java.awt.font.NumericShaper;

/**
 *
 * @author Niklas Rehfeld
 */
public class Dpid
{

	private String dpid;
	private final String matchRegex = "([a-fA-F0-9]{2}:){7}[a-fA-F0-9]{2}";

	/**
	 * Creates a new DPID given a DPID string. The string supplied must be either
	 * <ul>
	 * <li/> of the form hh:hh:hh:hh:hh:hh:hh:hh
	 * <li/> "any", "all" or "all_dpids"
	 * </ul>
	 * @param dpid
	 */
	public Dpid(String dpid)
	{
		this.dpid = dpid;
	}

	/**
	 * creates a new DPID from a value.
	 *
	 * note that as a long is signed (2's complement), the dpid
	 * ff:...:ff actually corresponds to -1L.
	 *
	 * @param dpid the long value of a DPID.
	 */
	public Dpid(long dpid)
	{
		String raw = Long.toHexString(dpid);
		int padding = 16 - raw.length();
		for (int i = 0; i < padding; i++)
		{
			raw = "0" + raw;
		}
		this.dpid = "";
		int j = 0;
		for (; j < 14; j+= 2)
		{
			this.dpid += raw.substring(j,  j+2) + ":";
		}
		this.dpid += raw.substring(j,  j+2);
	}

	public String getDpid()
	{
		return dpid;
	}


	/**
	 * This is a convenience method for simple dpids in test setups, such as
	 * using Mininet. In these cases the DPIDs will be something like
	 * 00:00:...:00:01 for the first switch etc. This converts the device number
	 * into a simple DPID like that. It only works if the device number is
	 * &lteq; FF.
	 *
	 * <emph>Should not be used in production code.</emph>
	 *
	 * @param dnum
	 * @return
	 * @deprecated(use constructor Dpid(long) instead.)
	 */
	@Deprecated
	public static String toDpid(int dnum)
	{
		String finalbits = Integer.toHexString(dnum);
		if (finalbits.length() < 2)
		{
			finalbits = "0" + finalbits;
		}

		return "00:00:00:00:00:00:00:" + finalbits;
	}

	public boolean isValid()
	{
		if (dpid.matches(matchRegex))
		{
			return true;
		}
		if (dpid.equalsIgnoreCase("any") || dpid.equalsIgnoreCase("all")
				|| dpid.equalsIgnoreCase("ALL_DPIDS"))
		{
			return true;
		}
		return false;

	}

	/**
	 * returns true iff o is a Dpid AND the dpid strings are the same,
	 * OR both of them are one of "any", "all" or "all_dpids".
	 */
	@Override
	public boolean equals(Object o)
	{
		Dpid other;
		if (!(o instanceof Dpid))
			return false;
		else
		{
			other = (Dpid) o;
		}
		String otherString = other.getDpid();
		if(otherString.equalsIgnoreCase(dpid))
		{
			return true;
		}
		if (otherString.equalsIgnoreCase("all") || otherString.equalsIgnoreCase("any") || otherString.equalsIgnoreCase("all_dpids"))
		{
			return (dpid.equalsIgnoreCase("all") || dpid.equalsIgnoreCase("any") || dpid.equalsIgnoreCase("all_dpids"));
		}
		return false;


	}

	@Override
	public int hashCode()
	{
		return dpid.hashCode();
	}

	@Override
	public String toString()
	{
		return dpid;
	}
}
