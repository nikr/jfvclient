/*
 * Copyright 2013 Niklas Rehfeld.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * returns a list of {@link Flowspace} structures.
 *
 * @see Flowspace
 * @author Niklas Rehfeld
 *
 */
public class ListFlowspace
{
	@SerializedName("slice-name")
	private String sliceName;
	@SerializedName("show-disabled")
	private boolean showDisabled;

	/**
	 * Create a new ListFlowspace request.
	 *
	 * @param sliceName
	 *            the name of the slice for which to list the flowspaces.
	 * @param showDisabled whether to show disabled flowspaces
	 */
	public ListFlowspace(String sliceName, boolean showDisabled)
	{
		this.sliceName = sliceName;
		this.showDisabled = showDisabled;
	}


	/**
	 * @param showDisabled whether to show disabled flowspaces
	 */
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
	 * @param sliceName
	 *            the name of the slice
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}

	/**
	 * @param showDisabled
	 *            whether to show disabled slices or not.
	 */
	public void setShowDisabled(boolean showDisabled)
	{
		this.showDisabled = showDisabled;
	}

}
