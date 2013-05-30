/**
 *
 */
package org.jfvclient.queries;


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
	 * @see org.jfvclient.queries.FVRpcQueryObject#getParameterCount()
	 */
	@Override
	public int getMinParameterCount()
	{
		return 0;
	}




}
