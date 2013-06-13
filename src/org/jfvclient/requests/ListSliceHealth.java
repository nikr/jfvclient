/**
 *
 */
package org.jfvclient.requests;

import com.google.gson.annotations.SerializedName;

/**
 * @author rehfelnikl
 *
 */
public class ListSliceHealth
{
	@SerializedName("slice-name")
	private String SliceName;

	public ListSliceHealth(String sliceName)
	{
		this.SliceName = sliceName;
	}

	/**
	 * @return the sliceName
	 */
	public String getSliceName()
	{
		return SliceName;
	}

	/**
	 * @param sliceName the sliceName to set
	 */
	public void setSliceName(String sliceName)
	{
		SliceName = sliceName;
	}
}
