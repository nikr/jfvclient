/**
 *
 */
package org.flowcreator.queries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.minidev.json.JSONObject;

/**
 * @author rehfelnikl
 *
 */
public class ListSlicesRequest extends FVRpcQueryObject
{

	public static final String[] RESULT_KEYS = {"slice-name", "admin-status"};

	/**
	 * @param method
	 */
	public ListSlicesRequest()
	{
		super("list-slices");
	}

	/* (non-Javadoc)
	 * @see org.flowcreator.queries.FVRpcQueryObject#getParameterCount()
	 */
	@Override
	public int getMinParameterCount()
	{
		return 0;
	}




}
