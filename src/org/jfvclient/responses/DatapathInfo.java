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
import java.util.List;
import java.util.Map;
import org.jfvclient.data.Dpid;

/**
 *
 * @author Niklas Rehfeld
 */
public class DatapathInfo
{

    private Dpid dpid;
    @SerializedName("num-ports")
    private int numPorts;
    @SerializedName("port-list")
    private List<Integer> portList;
    @SerializedName("port-names")
    private List<String> portNames;
    private String connection;
    @SerializedName("current-flowmod-usage")
    private Map<String, Integer> currentFlowmodUsage;

    public String getDpid()
    {
        return dpid.getDpid();
    }

    public int getNumPorts()
    {
        return numPorts;
    }

    public List<Integer> getPortList()
    {
        return portList;
    }

    public List<String> getPortNames()
    {
        return portNames;
    }

    public String getConnection()
    {
        return connection;
    }

    public Map<String, Integer> getCurrentFlowmodUsage()
    {
        return currentFlowmodUsage;
    }
    
    
}
