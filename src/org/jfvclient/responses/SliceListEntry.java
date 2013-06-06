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
package org.jfvclient.responses;

import com.google.gson.annotations.SerializedName;

/**
 * An entry in the list of responses returned by the
 * <code>list-slices</code> command.
 *
 * @author Niklas Rehfeld
 */
public class SliceListEntry
{
    @SerializedName("slice-name")
    private String slice_name;
    @SerializedName("admin-status")
    private boolean admin_status;
 
     public String getSlice_name()
    {
        return slice_name;
    }

    public boolean isAdmin_status()
    {
        return admin_status;
    }
    
    @Override
    public String toString()
    {
        return "slice-name: " + slice_name + ", admin-status: " + admin_status;
    }
}
