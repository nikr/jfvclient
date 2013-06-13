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

import org.jfvclient.responses.ErrorResponse;

/**
 * Represents a JSON-RPC response from the FlowVisor. Takes the form
 *
 * <pre>
 * {
 * "id" : &lt;id&gt;,
 * "result" : &lt result object &gt;,
 * "error" : {
 *      "code" : &lt;errorcode&gt;,
 *      "msg" : &lt;message&gt;,
 *      "data" : &lt; data &gt;
 *      },
 * "jsonrpc" : "2.0"
 * }
 * </pre>
 *
 * The
 * <code>error</code> part will be there if and only if the response is an error
 * response and the
 * <code>result</code> part will be there if and only if the response is not an
 * error response.
 *
 * @param <V> the result object type.
 *
 * @author Niklas Rehfeld
 */
public class FVRpcResponse<V>
{

    String id;
    V result;
    ErrorResponse error;
    final String jsonrpc = "2.0";

    /**
     * Returns the result object if this response is a successful response. To
     * test if this is an error response, use {@link #isError()}.
     *
     * @return The result object, or <code>null</code> if the response is an
     * error response.
     */
    public V getResult()
    {
        return result;
    }

    /**
     * Returns the error object associated with this response.
     * 
     * @return The error object, or null if this is not an error response. 
     * @see org.jfvclient.responses.ErrorResponse
     */
    public ErrorResponse getError()
    {
        return error;
    }

    /**
     * Returns the request/response ID.
     * 
     * @return the Request/Response ID. 
     */
    public String getID()
    {
        return id;
    }

    /**
     *
     * @return true iff this is an error response.
     */
    public boolean isError()
    {
        return error != null;
    }

    @Override
    public String toString()
    {
        if (result != null)
        {
            return "id = " + id + ", res = " + result.toString() + ", jsonrpc = " + jsonrpc;
        }
        if (error != null)
        {
            return "id = " + id + ", err = " + error.toString() + ", jsonrpc = " + jsonrpc;
        }
        return "";
    }
}
