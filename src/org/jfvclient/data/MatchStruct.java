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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.jfvclient.requests.ListFlowspace;

/**
 * The following documentation is stolen from the
 * org.flowvisor.openflow.protocol.FVMatch class from <a
 * href="https://github.com/OPENNETWORKINGLAB/flowvisor">flowvisor</a>
 *
 *
 * Supported keys/values include <br>
 * <p>
 * <TABLE border=1>
 * <TR>
 * <TD>KEY(s)
 * <TD>VALUE
 * </TR>
 * <TR>
 * <TD>"in_port","input_port"
 * <TD>integer
 * </TR>
 * <TR>
 * <TD>"dl_src","eth_src", "dl_dst","eth_dst"
 * <TD>hex-string
 * </TR>
 * <TR>
 * <TD>"dl_type", "dl_vlan", "dl_vlan_pcp"
 * <TD>integer
 * </TR>
 * <TR>
 * <TD>"nw_src", "nw_dst", "ip_src", "ip_dst"
 * <TD>CIDR-style netmask
 * </TR>
 * <TR>
 * <TD>"tp_src","tp_dst"
 * <TD>integer (max 64k)
 * </TR>
 * </TABLE>
 * <p>
 * The CIDR-style netmasks assume 32 netmask if none given, so:
 * "128.8.128.118/32" is the same as "128.8.128.118"
 * <p>
 * The following extra information is from the fvctl man page.
 * <p>
 *
 * The following field assignments describe how a flow matches a packet. If any
 * of these assignments is omitted from the flow syntax, the field is treated as
 * a wildcard; thus, if all of them are omitted, the resulting flow matches all
 * packets. The string all or any is used to specify a flow that matches all
 * packets.
 * <ul>
 * <li>in_port=port_no Matches physical port port_no. Switch ports are numbered
 * as displayed by fvctl getDeviceInfo DPID.</li>
 *
 * <li>dl_vlan=vlan Matches IEEE 802.1q virtual LAN tag vlan. Specify 0xffff as
 * vlan to match packets that are not tagged with a virtual LAN; otherwise,
 * specify a number between 0 and 4095, inclusive, as the 12-bit VLAN ID to
 * match.
 *
 * <li>dl_src=mac Matches Ethernet source address mac, which should be specified
 * as 6 pairs of hexadecimal digits delimited by colons, e.g. 00:0A:E4:25:6B:B0.
 *
 * <li>dl_dst=mac Matches Ethernet destination address mac.
 *
 * <li>dl_type=ethertype Matches Ethernet protocol type ethertype, which should
 * be specified as a integer between 0 and 65535, inclusive, either in decimal
 * or as a hexadecimal number prefixed by 0x, e.g. 0x0806 to match ARP packets.
 *
 * <li>nw_src=ip[/netmask] Matches IPv4 source address ip, which should be
 * specified as an IP address, e.g. 192.168.1.1. The optional netmask allows
 * matching only on an IPv4 address prefix. The netmask is specified
 * "CIDR-style", i.e., 192.168.1.0/24.
 *
 * <li>nw_dst=ip[/netmask] Matches IPv4 destination address ip.
 *
 * <li>nw_proto=proto Matches IP protocol type proto, which should be specified
 * as a decimal number between 0 and 255, inclusive, e.g. 6 to match TCP
 * packets.
 *
 * <li>nw_tos=tos/dscp Matches ToS/DSCP (only 6-bits, not modify reserved 2-bits
 * for future use) field of IPv4 header tos/dscp, which should be specified as a
 * decimal number between 0 and 255, inclusive.
 *
 * <li>tp_src=port Matches transport-layer (e.g., TCP, UDP, ICMP) source port
 * port, which should be specified as a decimal number between 0 and 65535 (in
 * the case of TCP or UDP) or between 0 and 255 (in the case of ICMP),
 * inclusive, e.g. 80 to match packets originating from a HTTP server.
 *
 * <li>tp_dst=port Matches transport-layer destination port port.
 * </ul>
 *
 * <p/>
 * in addition to these keys, it will also accept a <code>wildcards</code> key,
 * which takes an integer as an argument. This should however not be used when
 * manually creating a match structure, but is returned by {@link ListFlowspace}
 * .
 *
 * TODO add the rest of the keys.
 *
 * @author Niklas Rehfeld
 *
 */
public class MatchStruct extends HashMap<String, Object>
{

	protected final static Collection<String> MATCH_KEYS;

	static
	{
		MATCH_KEYS = new HashSet<String>();
		MATCH_KEYS.add("in_port");
		MATCH_KEYS.add("dl_dst");
		MATCH_KEYS.add("dl_src"); //
		MATCH_KEYS.add("eth_dst"); // MAC
		MATCH_KEYS.add("eth_src"); // MAC
		MATCH_KEYS.add("nw_src"); // ip
		MATCH_KEYS.add("nw_dst"); // ip
		MATCH_KEYS.add("ip_src"); // ip
		MATCH_KEYS.add("ip_dst"); // ip
		MATCH_KEYS.add("tp_src"); // port
		MATCH_KEYS.add("tp_dst"); // port
		MATCH_KEYS.add("wildcards"); // wildcards.
	}

	/**
	 * default constructor
	 */
	public MatchStruct()
	{

	}

	/**
	 * Only puts the key-value pair if the key is a valid match key.
	 *
	 */
	@Override
	public Object put(String key, Object value)
	{
		if (!MATCH_KEYS.contains(key))
		{
			throw new IllegalArgumentException("Key " + key + "not allowed.");
		}
		if ((key.equalsIgnoreCase("in_port") || key
				.equalsIgnoreCase("wildcards")) && !(value instanceof Number))
			throw new IllegalArgumentException(
					"Value must be a number, this is a "
							+ value.getClass().getCanonicalName());
		return super.put(key, value);

	}

	@Override
	public String toString()
	{
		int i = 0; // for the commas.
		String out = "";
		for (String k : keySet())
		{
			out += k + "=" + get(k);
			if (++i < size())
				out += ",";

		}
		return out;
	}

}
