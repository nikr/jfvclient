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
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.jfvclient.data.Dpid;
import org.jfvclient.data.Flowspace;
import org.jfvclient.deserialisers.DpidDeserialiser;
import org.jfvclient.requests.AddFlowspace;
import org.jfvclient.requests.AddSlice;
import org.jfvclient.requests.ListFlowspace;
import org.jfvclient.requests.ListSliceHealth;
import org.jfvclient.requests.ListSliceInfo;
import org.jfvclient.requests.ListSliceStats;
import org.jfvclient.requests.RemoveSlice;
import org.jfvclient.requests.UpdateFlowspace;
import org.jfvclient.requests.UpdateSlice;
import org.jfvclient.requests.UpdateSlicePassword;
import org.jfvclient.responses.DataPaths;
import org.jfvclient.responses.DatapathInfo;
import org.jfvclient.responses.FVHealth;
import org.jfvclient.responses.SliceHealth;
import org.jfvclient.responses.SliceInfo;
import org.jfvclient.responses.SliceList;
import org.jfvclient.responses.SliceStats;
import org.jfvclient.responses.Version;
import org.jfvclient.serialisers.DpidSerialiser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jfvclient.requests.RemoveFlowspace;

/**
 *
 *
 * @author Niklas Rehfeld
 */
public class JFVClient
{

	Properties config;
	// HttpsURLConnection connection;
	Gson gson;

	private String hostName;
	private String hostPort;

	private static Type booleanResponseType = new TypeToken<FVRpcResponse<Boolean>>()
	{
	}.getType();

	public JFVClient()
	{
		config = getProps();
		hostName = config.getProperty("hostname");
		hostPort = config.getProperty("port");
		gson = getGson();

	}

	/**
	 * Creates a {@link HttpsURLConnection} that can be used to do a request. A
	 * new {@link HttpsURLConnection} needs to be created for each request, as
	 * once the connection has been read from, it cannot be written to again.
	 *
	 * So this really needs to be called at the beginning of
	 * {@link #send(Gson, Object)} each time.
	 *
	 * @return A properly initialised HttpsURLConnection.
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private HttpsURLConnection connect() throws MalformedURLException,
			IOException
	{
		Authenticator.setDefault(new PropertiesFileAuth());

		// String hostname = config.getProperty("hostname");
		// String port = config.getProperty("port");

		HttpsURLConnection con = (HttpsURLConnection) new URL("https://"
				+ hostName + ":" + hostPort).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "jfvclient");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		con.setDoInput(true);
		// This is bad. it bypasses the Hostname Verifier, and makes things
		// insecure.
		// FIXME remove this from the production code.
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
		HttpsURLConnection connection = connect();
		OutputStream os = connection.getOutputStream();
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(os));
		String req = g.toJson(request);

		w.write(req);
		w.flush();
		w.close();

		// TODO remove this
		//		System.out.println("request: " + req);

		if (connection.getResponseCode() != 200)
		{
			System.err.println("Response is not OK -- "
					+ connection.getResponseCode() + "  "
					+ connection.getResponseMessage());
			BufferedReader iw = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String in = "";
			while ((in = iw.readLine()) != null)
				System.err.println(in);
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
		//System.out.println("response: " + response);

		return response;
	}

	protected class PropertiesFileAuth extends Authenticator
	{
		private String uname;
		private String pw;

		public PropertiesFileAuth()
		{
			uname = config.getProperty("username");
			pw = config.getProperty("password");
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
			// props.put("truststore", "~/.keystore");
			// props.put("truststorepw", "changeme");
		}

		return props;
	}

	/**
	 * Add a slice to the flowvisor.
	 *
	 * @param s
	 * @return
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	public boolean addSlice(AddSlice s) throws IOException,
			JFVErrorResponseException
	{
		// Gson g = getGson();
		FVRpcRequest<AddSlice> asr = new FVRpcRequest<AddSlice>(s);
		String response = send(gson, asr);
		FVRpcResponse<Boolean> resp = gson.fromJson(response,
				booleanResponseType);
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

	public FVHealth listFVHealth() throws JFVErrorResponseException,
			IOException
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

	public SliceHealth listSliceHealth(String sliceName)
			throws JFVErrorResponseException, IOException
	{
		FVRpcRequest<ListSliceHealth> lsr = new FVRpcRequest<ListSliceHealth>(
				new ListSliceHealth(sliceName));
		String response = send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<SliceHealth>>()
		{
		}.getType();
		FVRpcResponse<SliceHealth> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}

	public SliceStats listSliceStats(String sliceName)
			throws JFVErrorResponseException, IOException
	{
		FVRpcRequest<ListSliceStats> lsr = new FVRpcRequest<ListSliceStats>(
				new ListSliceStats(sliceName));
		String response = send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<SliceStats>>()
		{
		}.getType();
		FVRpcResponse<SliceStats> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}

	public boolean updateSlice(UpdateSlice u) throws JFVErrorResponseException,
			IOException
	{
		FVRpcRequest<UpdateSlice> lsr = new FVRpcRequest<UpdateSlice>(u);
		String response = send(gson, lsr);
		FVRpcResponse<Boolean> resp = gson.fromJson(response,
				booleanResponseType);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult().booleanValue();
	}

	public boolean removeSlice(String sliceName) throws IOException,
			JFVErrorResponseException
	{
		FVRpcRequest<RemoveSlice> rsr = new FVRpcRequest<RemoveSlice>(
				new RemoveSlice(sliceName));
		String response = send(gson, rsr);
		FVRpcResponse<Boolean> resp = gson.fromJson(response,
				booleanResponseType);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult().booleanValue();
	}

	public int addFlowspace(AddFlowspace fs) throws IOException,
			JFVErrorResponseException
	{

		FVRpcRequest<AddFlowspace> afr = new FVRpcRequest<AddFlowspace>(fs);
		String response = send(gson, afr);
		FVRpcResponse<Integer> resp = gson.fromJson(response,
				new TypeToken<FVRpcResponse<Integer>>(){}.getType());
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult().intValue();
	}

	/**
	 * convenience method for adding a single flowspace.
	 *
	 * @param f
	 * @return
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	public int addFlowspace(Flowspace f) throws IOException,
			JFVErrorResponseException
	{
		AddFlowspace a = new AddFlowspace();
		a.add(f);
		return addFlowspace(a);
	}

	/**
	 * Remove a single flowspace.
	 *
	 * @param flowspaceName the name of the flowspace
	 * @return
	 * @throws JFVErrorResponseException
	 * @throws IOException
	 */
	public int removeFlowspace(String flowspaceName)
			throws JFVErrorResponseException, IOException
	{
		RemoveFlowspace fs = new RemoveFlowspace(flowspaceName);
		FVRpcRequest<RemoveFlowspace> afr = new FVRpcRequest<RemoveFlowspace>(
				fs);
		String response = send(gson, afr);
		FVRpcResponse<Integer> resp = gson.fromJson(response,
				new TypeToken<FVRpcResponse<Integer>>(){}.getType());
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult().intValue();
	}

	public Version getVersion() throws JFVErrorResponseException, IOException
	{
		FVRpcRequest lsr = new FVRpcRequest(
				FVRpcRequest.NoParamType.list_version);
		String response = send(gson, lsr);
		Type t = new TypeToken<FVRpcResponse<Version>>()
		{
		}.getType();
		FVRpcResponse<Version> resp = gson.fromJson(response, t);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}

	/**
	 * Lists the flowspaces associated with a slice.
	 *
	 * @param sliceName
	 *            The name of the slice.
	 * @param includeDisabled
	 *            defaults to true if sliceName is not null.
	 * @return A list of the flowspaces associated with the slice.
	 * @throws IOException
	 *             If something bad happens on the network.
	 * @throws JFVErrorResponseException
	 *             If the response is an error.
	 */
	public List<Flowspace> listFlowspaces(String sliceName,
			boolean includeDisabled) throws IOException,
			JFVErrorResponseException
	{
		FVRpcRequest<ListFlowspace> lfs = new FVRpcRequest<ListFlowspace>(
				new ListFlowspace(sliceName, includeDisabled));
		String response = send(gson, lfs);
		Type responseType = new TypeToken<FVRpcResponse<List<Flowspace>>>()
		{
		}.getType();
		FVRpcResponse<List<Flowspace>> resp = gson.fromJson(response,
				responseType);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}

	/**
	 * Lists the flowspaces for all slices.
	 *
	 * @param includeDisabled
	 *            whether or not to include disabled slices. Defaults to
	 *            <b>false</b>
	 * @return A list of all flowspaces.
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	public List<Flowspace> listAllFlowspaces(boolean includeDisabled)
			throws IOException, JFVErrorResponseException
	{
		FVRpcRequest<ListFlowspace> lfs = new FVRpcRequest<ListFlowspace>(
				new ListFlowspace(includeDisabled));
		String response = send(gson, lfs);
		Type responseType = new TypeToken<FVRpcResponse<List<Flowspace>>>()
		{
		}.getType();
		FVRpcResponse<List<Flowspace>> resp = gson.fromJson(response,
				responseType);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();
	}

	public boolean updateSlicePassword(String sliceName, String password)
			throws IOException, JFVErrorResponseException
	{
		FVRpcRequest<UpdateSlicePassword> usp = new FVRpcRequest<UpdateSlicePassword>(
				new UpdateSlicePassword(sliceName, password));
		String response = send(gson, usp);
		FVRpcResponse<Boolean> resp = gson.fromJson(response,
				booleanResponseType);
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult();

	}

	public int updateFlowspace(UpdateFlowspace flowspaces)
			throws IOException, JFVErrorResponseException
	{

		FVRpcRequest<UpdateFlowspace> ufs = new FVRpcRequest<UpdateFlowspace>(
				flowspaces);
		String response = send(gson, ufs);
		FVRpcResponse<Integer> resp = gson.fromJson(response,
				new TypeToken<FVRpcResponse<Integer>>(){}.getType());
		if (resp.isError())
		{
			throw new JFVErrorResponseException(resp.getError());
		}
		return resp.getResult().intValue();
	}

	public int updateFlowspace(Flowspace flowspace) throws IOException,
			JFVErrorResponseException
	{
		UpdateFlowspace l = new UpdateFlowspace();
		l.add(flowspace);
		return updateFlowspace(l);
	}
}
