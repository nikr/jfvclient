/**
 *
 */
package org.jfvclient.requests;

import org.jfvclient.data.Flowspace;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * {
 *  "slice-name" : <name>
 *     OPTIONAL
 *  "show-disabled" : <boolean>
 *     OPTIONAL
 * }
 * </pre>
 *
 *returns a {@link Flowspace} structure.
 *@see Flowspace
 * @author Niklas Rehfeld
 *
 */
public class ListFlowspace
{
	@SerializedName("slice-name") private String sliceName;
	@SerializedName("show-disabled") private boolean showDisabled;

	public ListFlowspace(String sliceName, boolean showDisabled)
	{
		this.sliceName = sliceName;
		this.showDisabled = showDisabled;
	}

	public ListFlowspace()
	{

	}

	public ListFlowspace(boolean showDisabled)
	{
		this.showDisabled = showDisabled;
	}

	/**
	 * @return name of the slice whose flowspaces to list.
	 */
	public String getSliceName()
	{
		return sliceName;
	}
	/**
	 * @return whether to show disabled slices
	 */
	public boolean showDisabled()
	{
		return showDisabled;
	}
	/**
	 * @param sliceName the name of the slice
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}
	/**
	 * @param showDisabled whether to show disabled slices or not.
	 */
	public void setShowDisabled(boolean showDisabled)
	{
		this.showDisabled = showDisabled;
	}


}
