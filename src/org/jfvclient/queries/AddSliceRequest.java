/**
 *
 */
package org.jfvclient.queries;

import org.jfvclient.data.Slice;

/**
 * @author rehfelnikl
 *
 */
public class AddSliceRequest extends FVRpcObjectQueryRequest
{

	public AddSliceRequest(String hostURL)
	{
		super(hostURL, "add-slice");
	}

	public AddSliceRequest(String hostURL, Slice slice)
	{
		this(hostURL);
//		configureSlice(slice);
		put(PARAMS, slice);

	}

	/* (non-Javadoc)
	 * @see org.jfvclient.queries.FVRpcQueryRequest#returns()
	 */
	@Override
	public ParamType returns()
	{
		return ParamType.OBJECT;
	}


//	public void configureSlice(Slice slice)
//	{
//		put("slice-name", slice.getName());
//		addNamedParameter("controller-url", slice.getControllerURL());
//		addNamedParameter("admin-contact", slice.getAdminEmail());
//		addNamedParameter("password", slice.getPassword());
//	}


}
