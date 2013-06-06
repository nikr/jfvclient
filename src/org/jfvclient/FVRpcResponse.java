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

package org.jfvclient;

import org.jfvclient.responses.Error;

/**
 *
 * @author Niklas Rehfeld
 */
public class FVRpcResponse<V> {
    String id;
    V result;
    Error error;
    String jsonrpc = "2.0";
    
    public V getResult()
    {
        return result;
    }
    
    public Error getError()
    {
        return error;
    }
    
    public String getID()
    {
        return id;
    }
    
    @Override
    public String toString()
    {
        if (result != null)
            return "id = " + id + ", res = " + result.toString() + ", jsonrpc = " +jsonrpc;
        if (error != null)
            return "id = " + id + ", err = " + error.toString() + ", jsonrpc = " +jsonrpc;
        return "";
    }
    
}
