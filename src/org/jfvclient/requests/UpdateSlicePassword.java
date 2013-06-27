/**
 *
 */
package org.jfvclient.requests;

import com.google.gson.annotations.SerializedName;


/**
 *
 *
 * <pre>{
 "slice-name" : &lt;name&gt;,
 "password" : &lt;password&gt;
}</pre>
 *
 *returns a boolean.
 * @author Niklas Rehfeld
 *
 */
public class UpdateSlicePassword
{
	@SerializedName("slice-name") private String sliceName;
	private String password;

	public UpdateSlicePassword(String sliceName, String password)
	{
		this.sliceName = sliceName;
		this.password = password;
	}

	/**
	 * @return the sliceName
	 */
	public String getSliceName()
	{
		return sliceName;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param sliceName the sliceName to set
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}



}
