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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.jfvclient.data.Dpid;
import org.jfvclient.deserialisers.DpidDeserialiser;
import org.jfvclient.responses.DatapathInfo;
import org.jfvclient.responses.Link;
import org.jfvclient.responses.SliceListEntry;
import org.jfvclient.serialisers.DpidSerialiser;

/**
 *
 * @author Niklas Rehfeld
 */
public class JFVClient {

    public static void main(String[] args)
    {
//        Gson g = new Gson();
        Gson g = getGson();
        FVRpcRequest r = new FVRpcRequest("list-version", "list-version-1", null);
        System.out.println(g.toJson(r));
        
        r = new FVRpcRequest(FVRpcRequest.NoParamType.list_links, "listlinks07662");
        System.out.println(g.toJson(r));
        
//        HttpClient c = new DefaultHttpClient();
        
        String result = "{\"id\":\"fdsa\", \"result\":\"chickens\",\"jsonrpc\":\"2.0\"}";
        FVRpcResponse resp = g.fromJson(result, FVRpcResponse.class);
        System.out.println(resp);
        result = "{\"id\":\"fdsa\", \"error\":{\"code\" : -32146, \"msg\" : \"chickens\"},\"jsonrpc\":\"2.0\"}";
        resp = g.fromJson(result, FVRpcResponse.class);
        System.out.println(resp);
        result = "{\"id\":\"fdsa\", \"result\":{\"src-dpid\" : \"fdsa\"},\"jsonrpc\":\"2.0\"}";
        FVRpcResponse<Link> rl ;
        Type respLink = new TypeToken<FVRpcResponse<Link>>(){}.getType();
        rl = g.fromJson(result, respLink);
        System.out.println(rl);
        
        
        result = "{\"id\":\"fdsa\", \"result\":[{\"src-dpid\" : \"fdsa\"},{\"src-dpid\" : \"fdsaffddd\"}],\"jsonrpc\":\"2.0\"}";
        FVRpcResponse<List<Link>> rll;
        respLink = new TypeToken<FVRpcResponse<List<Link>>>(){}.getType();
        rll = g.fromJson(result, respLink);
        System.out.println(rll);
        
        result = "{\"id\":\"fdsa\", \"result\":[{\"slice-name\" : \"fdsa\", \"admin-status\" : true}],\"jsonrpc\":\"2.0\"}";
        FVRpcResponse<List<SliceListEntry>> sll;
        respLink = new TypeToken<FVRpcResponse<List<SliceListEntry>>>(){}.getType();
        sll = g.fromJson(result, respLink);
        System.out.println(sll);
        
        result = "{\"id\":\"fdsa\", \"result\":{\"current-flowmod-usage\" : {\"fdsa\" : 1234 ,\"slice2\" : 3456}},\"jsonrpc\":\"2.0\"}";
        FVRpcResponse<DatapathInfo> dpr;
        respLink = new TypeToken<FVRpcResponse<DatapathInfo>>(){}.getType();
        dpr = g.fromJson(result, respLink);
        System.out.println(dpr.getResult().getCurrentFlowmodUsage());
        
        
    }
    
    private static Gson getGson()
    {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Dpid.class, DpidSerialiser.class);
        gb.registerTypeAdapter(Dpid.class, DpidDeserialiser.class);
        return gb.create();
    }
    
    
}
