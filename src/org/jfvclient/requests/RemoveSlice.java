/**
 *
 */
package org.jfvclient.requests;

import com.google.gson.annotations.SerializedName;

/**
 * A <code>remove-slice</code> request.
 * <pre>
{
 "slice-name" : <name>
} </pre>
 *
 * returns a boolean.
 *
 * @author Niklas Rehfeld
 *
 */
public class RemoveSlice
{
	@SerializedName("slice-name") private String sliceName;

	public RemoveSlice(String name)
	{
		this.setSliceName(name);
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
