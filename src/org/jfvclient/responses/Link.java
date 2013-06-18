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

    @SerializedName("srcDPID")
    private Dpid src_dpid;
    @SerializedName("srcPort")
    private int src_port;
    @SerializedName("dstDPID")
    private Dpid dst_dpid;
    @SerializedName("dstPort")
    private int dst_port;

    public Dpid getSrc_dpid()
    {
        return src_dpid;
    }

    public int getSrc_port()
    {
        return src_port;
    }

    public Dpid getDst_dpid()
    {
        return dst_dpid;
    }

    public int getDst_port()
    {
        return dst_port;
    }

    @Override
    public String toString()
    {

        String out = src_dpid.getDpid() + ":";
        out += src_port + "-->";
        out += dst_dpid.getDpid() + ":";
        out += dst_port;
        return out;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Link))
        {
            return false;
        }
        Link other = (Link) o;
        return (other.dst_dpid == dst_dpid
                && other.dst_port == dst_port
                && other.src_dpid == src_dpid
                && other.src_port == src_port);
    }

    @Override
    public int hashCode()
    {
        //not sure about this one...
        return dst_dpid.hashCode() + src_dpid.hashCode() + dst_port + (src_port << 4);

    }
}
