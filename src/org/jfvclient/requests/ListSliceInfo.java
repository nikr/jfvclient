/**
 *
 */
package org.jfvclient.requests;

import com.google.gson.annotations.SerializedName;

/**
 * @author Niklas Rehfeld
 *
 */
public class ListSliceInfo
{
	@SerializedName("slice-name") private String sliceName;

	public ListSliceInfo(String name)
	{
		this.sliceName = name;
	}

	/**
	 * @return the sliceName
	 */
	public String getSliceName()
	{
		return sliceName;
	}

	/**
	 * @param sliceName the sliceName to set
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}

}
