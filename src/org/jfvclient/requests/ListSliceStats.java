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

import com.google.gson.annotations.SerializedName;

/**
 * @author Niklas Rehfeld
 *
 */
public class ListSliceStats
{
	@SerializedName("slice-name")
	private String sliceName;

	/**
	 *create new ListSliceStats request.
	 * @param sliceName the name of the slice to query
	 */
	public ListSliceStats(String sliceName)
	{
		this.sliceName = sliceName;
	}

	/**
	 * @return the sliceName
	 */
	public String getSliceName()
	{
		return sliceName;
	}

	/**
	 * @param sliceName
	 *            the sliceName to set
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}

}
