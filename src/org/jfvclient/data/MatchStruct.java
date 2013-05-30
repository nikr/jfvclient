/**
 *
 */
package org.jfvclient.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This Class is simply a filtered {@see java.util.Hashmap}. It only allows adding values for a predefined set of keys.
 *
 * There are probably much nicer ways of doing this.
 *
 * TODO add the rest of the keys.
 *
 * @author Niklas Rehfeld
 *
 */
public class MatchStruct extends HashMap<String, Object>
{
	static Collection<String> MATCH_KEYS;

	private Map<String, Object> matches;

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
		MATCH_KEYS.add("tp_src"); // port
		MATCH_KEYS.add("tp_dst"); // port
	}

	public MatchStruct()
	{
		matches = new HashMap<String, Object>();

	}

	@Override
	public Object put(String key, Object value)
	{
		if (!MATCH_KEYS.contains(key))
		{
			throw new IllegalArgumentException("Key " + key + "not allowed.");
		}
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
