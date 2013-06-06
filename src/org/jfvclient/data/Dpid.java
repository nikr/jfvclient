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
package org.jfvclient.data;

/**
 *
 * @author Niklas Rehfeld
 */
public class Dpid
{

    private String dpid;
    private final String matchRegex = "([a-fA-F0-9]{2}:){7}[a-fA-F0-9]{2}";

    public Dpid(String dpid)
    {
        this.dpid = dpid;
    }
    
    public String getDpid()
    {
        return dpid;
    }

    public boolean isValid()
    {
        if (dpid.matches(matchRegex))
        {
            return true;
        }
        if (dpid.equals("any") || dpid.equals("all") || dpid.equals("ALL_DPIDS"))
        {
            return true;
        }
        return false;

    }
}
