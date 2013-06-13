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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.jfvclient.data.Dpid;
import org.jfvclient.deserialisers.DpidDeserialiser;
import org.jfvclient.requests.AddSlice;
import org.jfvclient.requests.ListSliceInfo;
import org.jfvclient.responses.DataPaths;
import org.jfvclient.responses.DatapathInfo;
import org.jfvclient.responses.FVHealth;
import org.jfvclient.responses.SliceInfo;
import org.jfvclient.responses.SliceList;
import org.jfvclient.serialisers.DpidSerialiser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 *
 * @author Niklas Rehfeld
 */
public class JFVClient
{

	Properties config;
	HttpsURLConnection connection;
	Gson gson;

	public JFVClient()
	{
		config = getProps();
		try
		{
			connection = connect();
		} catch (Exception e)
		{
			System.err.println("Cannot connect to FlowVisor: "
					+ e.getLocalizedMessage());
			throw new Error("Cannot connect to Flowvisor", e);
		}
		gson = getGson();


//		Doesn't work.
//		if (config.containsKey("truststore") && config.containsKey("truststorepw"))
//		{
//			System.setProperty("javax.net.ssl.trustStore", config.getProperty("truststore"));
//			System.setProperty("javax.net.ssl.trustStorePassword", config.getProperty("truststorepw"));
//		}


	}

	private HttpsURLConnection connect() throws MalformedURLException,
			IOException
	{
		Authenticator.setDefault(new SimpleAuth());

		String hostname = config.getProperty("hostname");
		String port = config.getProperty("port");

		HttpsURLConnection con = (HttpsURLConnection) new URL("https://"
				+ hostname + ":" + port).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "jfvclient");
		con.setRequestProperty("content-type", "application/json");
		con.setDoOutput(true);
		con.setDoInput(true);
		// This is bad. it bypasses the Hostname Verifier, and makes things
		// insecure.
		// TODO remove this from the production code.
		con.setHostnameVerifier(new HostnameVerifier()
		{

			@Override
			public boolean verify(String hostname, SSLSession session)
			{
				if (!session.getPeerHost().equalsIgnoreCase(hostname))
				{
					System.err.println("WARNING: SSL session hostname ("
							+ session.getPeerHost()
							+ ") is different to actual host name (" + hostname
							+ ")");
				}
				return true;
			}
		});
		return con;
	}

	protected String send(Gson g, Object request) throws IOException

	{
		//TODO need to check that the connection is still OK, and reconnect if necessary.

		OutputStream os = connection.getOutputStream();
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(os));
		String req = g.toJson(request);

		w.write(req);
		w.flush();
		w.close();

		// TODO remove this
		System.out.println("req: " + req);

		if (connection.getResponseCode() != 200)
		{
			System.err.println("Response is not OK -- "
					+ connection.getResponseCode() + "  "
					+ connection.getResponseMessage());
		}
		BufferedReader iw = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String response = "";
		String in;
		while ((in = iw.readLine()) != null)
		{
			response += in;
		}
		iw.close();

		// TODO remove this.
		System.out.println("response: " + response);

		return response;
	}

	public static class SimpleAuth extends Authenticator
	{
		private static String uname;
		private static String pw;

		static
		{ // get the properties file and read password etc. from there.
			Properties props = null;
			try
			{
				props = getProps();

			} catch (Exception e)
			{

				e.printStackTrace();
				throw new Error(e.getLocalizedMessage());
			}

			uname = props.getProperty("username");
			pw = props.getProperty("password");
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(uname, pw.toCharArray());

		}
	}

	protected static Gson getGson()
	{
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Dpid.class, new DpidSerialiser());
		gb.registerTypeAdapter(Dpid.class, new DpidDeserialiser());
		return gb.create();
	}

	private static Properties getProps()
	{
		Properties props = new Properties();
		try
		{
			props.load(new FileInputStream("resources/visor.properties"));
		} catch (Exception e)
		{
			System.err.println("Properties file not loaded: "
					+ e.getLocalizedMessage());
			props = new Properties();
			props.put("hostname", "localhost");
			props.put("port", 8080);
			props.put("password", "");
			props.put("username", "fvadmin");
//			props.put("truststore", "~/.keystore");
//			props.put("truststorepw", "changeme");
		}

		return props;
	}

	public boolean addSlice(AddSlice s) throws IOException,
			JFVErrorResponseException
	{
		// Gson g = getGson();
		FVRpcRequest<AddSlice> asr = new FVRpcRequest<AddSlice>(s);
		String response = send(gson, asr);
		Type t = new TypeToken<FVRpcResponse<Boolean>>()
		{
		}.getType();
		FVRpcResponse<Boolean> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult().booleanValue();
	}

	public SliceList listSlices() throws IOException, JFVErrorResponseException
	{
		FVRpcRequest lsr = new FVRpcRequest(
				FVRpcRequest.NoParamType.list_slices);
		String response = send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<SliceList>>()
		{
		}.getType();
		FVRpcResponse<SliceList> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}

	public DataPaths listDatapaths() throws IOException,
			JFVErrorResponseException
	{
		FVRpcRequest lsr = new FVRpcRequest(
				FVRpcRequest.NoParamType.list_datapaths);
		String response = send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<DataPaths>>()
		{
		}.getType();
		FVRpcResponse<DataPaths> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}

	public DatapathInfo listDataPathInfo(Dpid d) throws IOException,
			JFVErrorResponseException
	{
		FVRpcRequest<Dpid> lsr = new FVRpcRequest<Dpid>(d);
		String response = send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<DatapathInfo>>()
		{
		}.getType();
		FVRpcResponse<DatapathInfo> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}

	public SliceInfo listSliceInfo(String sliceName) throws IOException,
			JFVErrorResponseException
	{
		FVRpcRequest<ListSliceInfo> lsr = new FVRpcRequest<ListSliceInfo>(
				new ListSliceInfo(sliceName));
		String response = send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<SliceInfo>>()
		{
		}.getType();
		FVRpcResponse<SliceInfo> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}


	public FVHealth listFVHealth() throws JFVErrorResponseException, IOException
	{
		FVRpcRequest lsr = new FVRpcRequest(
				FVRpcRequest.NoParamType.list_fv_health);
		String response = send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<FVHealth>>()
		{
		}.getType();
		FVRpcResponse<FVHealth> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}
}
