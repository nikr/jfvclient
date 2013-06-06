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
import org.jfvclient.data.Dpid;

/**
 * One link in a response to "list-links"
 *
 * @author Niklas Rehfeld
 */
public class Link
{

    @SerializedName("src-dpid")
    private Dpid src_dpid;
    @SerializedName("src-port")
    private int src_port;
    @SerializedName("dst-dpid")
    private Dpid dst_dpid;
    @SerializedName("dst-port")
    private int dst_port;

    public String getSrc_dpid()
    {
        return src_dpid.getDpid();
    }

    public int getSrc_port()
    {
        return src_port;
    }

    public String getDst_dpid()
    {
        return dst_dpid.getDpid();
    }

    public int getDst_port()
    {
        return dst_port;
    }
    
   @Override
   public String toString()
   {
       return dst_dpid.getDpid() + dst_port + src_dpid.getDpid() + src_port;
   }
}
