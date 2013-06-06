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
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

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
public class JFVClient
{

    public static void main(String[] args) throws MalformedURLException, IOException
    {
    	Gson g = getGson();
        FVRpcRequest r = new FVRpcRequest(FVRpcRequest.NoParamType.list_version, "lv1");
        send(g, r);

    }


    private static void test()
    {
//      Gson g = new Gson();
      Gson g = getGson();
      FVRpcRequest r = new FVRpcRequest("list-version", "list-version-1", null);
      System.out.println(g.toJson(r));

      r = new FVRpcRequest(FVRpcRequest.NoParamType.list_links,
              "listlinks07662");
      System.out.println(g.toJson(r));

//      HttpClient c = new DefaultHttpClient();

      String result = "{\"id\":\"fdsa\", \"result\":\"chickens\",\"jsonrpc\":\"2.0\"}";
      FVRpcResponse resp = g.fromJson(result, FVRpcResponse.class);
      System.out.println(resp);
      result = "{\"id\":\"fdsa\", \"error\":{\"code\" : -32146, \"msg\" : \"chickens\"},\"jsonrpc\":\"2.0\"}";
      resp = g.fromJson(result, FVRpcResponse.class);
      System.out.println(resp);
      result = "{\"id\":\"fdsa\", \"result\":{\"src-dpid\" : \"fdsa\"},\"jsonrpc\":\"2.0\"}";
      FVRpcResponse<Link> rl;
      Type respLink = new TypeToken<FVRpcResponse<Link>>()
      {
      }.getType();
      rl = g.fromJson(result, respLink);
      System.out.println(rl);


      result = "{\"id\":\"fdsa\", \"result\":[{\"src-dpid\" : \"fdsa\"},{\"src-dpid\" : \"fdsaffddd\"}],\"jsonrpc\":\"2.0\"}";
      FVRpcResponse<List<Link>> rll;
      respLink = new TypeToken<FVRpcResponse<List<Link>>>()
      {
      }.getType();
      rll = g.fromJson(result, respLink);
      System.out.println(rll);

      result = "{\"id\":\"fdsa\", \"result\":[{\"slice-name\" : \"fdsa\", \"admin-status\" : true}],\"jsonrpc\":\"2.0\"}";
      FVRpcResponse<List<SliceListEntry>> sll;
      respLink = new TypeToken<FVRpcResponse<List<SliceListEntry>>>()
      {
      }.getType();
      sll = g.fromJson(result, respLink);
      System.out.println(sll);

      result = "{\"id\":\"fdsa\", \"result\":{\"current-flowmod-usage\" : {\"fdsa\" : 1234 ,\"slice2\" : 3456}},\"jsonrpc\":\"2.0\"}";
      FVRpcResponse<DatapathInfo> dpr;
      respLink = new TypeToken<FVRpcResponse<DatapathInfo>>()
      {
      }.getType();
      dpr = g.fromJson(result, respLink);
      System.out.println(dpr.getResult().getCurrentFlowmodUsage());

    }



    private static Object send(Gson g, Object request) throws MalformedURLException, IOException
    {
    	Properties props = getProps();

    	Authenticator.setDefault(new SimpleAuth());

    	String uname = props.getProperty("username");
    	String pw = props.getProperty("password");

        HttpsURLConnection con = (HttpsURLConnection) new URL(
                "https://bordeaux:8001").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "jfvclient");
        con.setDoOutput(true);
        con.setHostnameVerifier(new HostnameVerifier()
		{

			@Override
			public boolean verify(String arg0, SSLSession arg1)
			{
				return true;
			}
		});

        OutputStream os = con.getOutputStream();
        OutputStreamWriter w = new OutputStreamWriter(new BufferedOutputStream(
                os));
        String req = g.toJson(request);
        System.out.println(req);
        w.write(req);
        w.flush();
        w.close();

        if (con.getResponseCode() != 200)
        {
            System.out.println("Response is not OK -- " + con.getResponseCode() + "  " + con.getResponseMessage());
        }
        BufferedReader iw = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = "";
        String in;
        while ((in = iw.readLine()) != null)
        {
            response += in;
        }
        iw.close();
        System.out.println("response: " + response);


        return null;
    }

    public static class SimpleAuth extends Authenticator
    {
    	@Override
    	 public PasswordAuthentication getPasswordAuthentication () {
            //get the properties file and read password etc. from there. NOT LIKE THIS...
             return new PasswordAuthentication ("fvadmin", "inbound traffic".toCharArray());

         }
    }

    private static Gson getGson()
    {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Dpid.class, new DpidSerialiser());
        gb.registerTypeAdapter(Dpid.class, new DpidDeserialiser());
        return gb.create();
    }

    private static Properties getProps() throws FileNotFoundException, IOException
    {
    	Properties p = new Properties();
    	p.load(new FileInputStream("resources/visor.properties"));
    	return p;
    }
}
