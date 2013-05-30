/**
 *
 */
package org.jfvclient.queries;

import org.jfvclient.data.Slice;

/**
 * @author rehfelnikl
 *
 */
public class AddSliceRequest extends FVRpcQueryObject
{

	public AddSliceRequest()
	{
		super("add-slice");
	}

	public AddSliceRequest(Slice slice)
	{
		this();
		configureSlice(slice);

	}

	public void configureSlice(Slice slice)
	{
		addNamedParameter("slice-name", slice.getName());
		addNamedParameter("controller-url", slice.getControllerURL());
		addNamedParameter("admin-contact", slice.getAdminEmail());
		addNamedParameter("password", slice.getPassword());
	}

	@Override
	public int getMinParameterCount()
	{
		return 4;
	}


}
