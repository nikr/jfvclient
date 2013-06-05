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

import com.google.gson.Gson;
import com.google.gson.JsonNull;

/**
 *
 * @author Niklas Rehfeld
 */
public class FVRpcRequest<V>
{

    private String method;
    private String id;
    private V params;
    private String jsonrpc = "2.0";

    public FVRpcRequest(String method, String id, V params)
    {
        this.method = method;
        this.id = id;
        this.params = params;

    }

    public String getMethod()
    {
        return method;
    }

    public V getParam()
    {
        return params;
    }

    public FVRpcRequest(String method, V params)
    {
        this(method, method + Math.round(Math.random() * 1000), params);
    }

    /**
     * Constructor used for 'empty' requests such as list-version, list-links
     * etc.
     *
     *
     * @param t the method
     * @param id a request ID.
     */
    public FVRpcRequest(NoParamType t, String id)
    {

        switch (t)
        {
            case list_slices:
                method = "list-slices";
                break;
            case list_version:
                method = "list-version";
                break;
            case list_datapaths:
                method = "list-datapaths";
                break;
            case list_links:
                method = "list-links";
                break;
            case list_fv_health:
                method = "list-fv-health";
                break;
            case save_config:
                method = "save-config";
                break;
            default:
                throw new AssertionError(t.name());
        }
        this.id = id;
        params = (V) JsonNull.INSTANCE;

    }

    /**
     * Requests that require no parameters. Rather than implementing all of
     * these as empty classes, it is easier to just enumerate them and have them
     * as a special class of requests.
     */
    public static enum NoParamType
    {

        /**
         * list-slices request
         */
        list_slices,
        /**
         * list-version request
         */
        list_version,
        /**
         * list-datapaths request
         */
        list_datapaths,
        /**
         * list-links request
         */
        list_links,
        /**
         * list-fv-health request
         */
        list_fv_health,
        /**
         * save-config request
         */
        save_config,
    }
}
