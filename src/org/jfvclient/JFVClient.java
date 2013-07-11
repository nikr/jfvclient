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
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfvclient.requests.RemoveFlowspace;

/**
 * This class can be used to for most operations. It contains methods for sending
 * FlowVisor monitoring and control messages.
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

        private boolean ignoreHostAuth;
        
	private static Type booleanResponseType = new TypeToken<FVRpcResponse<Boolean>>()
	{
	}.getType();
        
        private static final Logger logger = Logger.getLogger(JFVClient.class.getCanonicalName());

	/**
	 * Creates a new JFVClient.
	 */
	public JFVClient()
	{
		config = getProps();
		hostName = config.getProperty("hostname");
		hostPort = config.getProperty("port");
                ignoreHostAuth = Boolean.getBoolean(config.getProperty("ignore-hostname-auth"));
		gson = getGson();
                logger.setLevel(Level.parse(config.getProperty("verbosity",
                        "WARNING")));      
                

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
	private HttpsURLConnection connect() throws 
			IOException
	{
		Authenticator.setDefault(new PropertiesFileAuthenticator(new File("resources/visor.properties")));
                
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
                if (ignoreHostAuth)
                {
                    logger.log(Level.WARNING, "Ignoring hostname verification.");
		con.setHostnameVerifier(new HostnameVerifier()
		{

			@Override
			public boolean verify(String hostname, SSLSession session)
			{
				if (!session.getPeerHost().equalsIgnoreCase(hostname))
				{
					logger.log(Level.WARNING, "SSL session hostname ({0}) is different to actual host name ({1})", new Object[]{session.getPeerHost(), hostname});
				}
				return true;
			}
		});
                }
		return con;
	}

    /**
     * Send a message through to the FlowVisor.
     * This is called by the various user methods.
     *
     */
	protected String send(Gson g, Object request) throws IOException

	{
            // have to get a new connection each time, as you can't write to 
            // a HttpsUrlConnection once it's been read from. 
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
			logger.log(Level.SEVERE, "Response is not OK -- {0}  {1}", new Object[]{connection.getResponseCode(), connection.getResponseMessage()});
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


    /**
     * Gets a properly initialised GSON object, to use for parsing JSON.
     *
     * @return a Gson object that can be used to properly serialise/deserialise the JSON messages used.
     */
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
			logger.log(Level.WARNING, "Properties file not loaded: {0}, using defaults.", e.getLocalizedMessage());
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
	 * Add a slice to the FlowVisor.
	 *{@link AddSlice}
	 *
	 * @param s A the slice to be added.
	 * @return true if the slice was added sucessfully (I think)
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response
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

	/**
	 * Gets the list of slices from the FlowVisor.
	 *
	 * @return a list of Slices.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response
	 */
	public SliceList listSlices() throws IOException, JFVErrorResponseException
	{
		EmptyFVRpcRequest lsr = new EmptyFVRpcRequest(
				EmptyFVRpcRequest.NoParamType.list_slices);
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

	/**
	 * Gets the devices that the FlowVisor can see.
	 *
	 * @return a list of Dpids.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response
	 */
	public DataPaths listDatapaths() throws IOException,
			JFVErrorResponseException
	{
		EmptyFVRpcRequest lsr = new EmptyFVRpcRequest(
				EmptyFVRpcRequest.NoParamType.list_datapaths);
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

	/**
	 * Returns the information about a device.
	 *
	 * @param d the Dpid of the device to query.
	 * @return Information about the device.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response
	 */
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

	/**
	 * Gets information about a slice.
	 *
	 * @param sliceName the name of the slice to get information about.
	 * @return Information about a slice.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response
	 */
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

	/**
	 * Gets information about the health of the FlowVisor, such as the number
	 * of active DB sessions, the number of idle DB sessions, the average delay etc.
	 * @return Information about the health of the running FlowVisor.
	 * @throws JFVErrorResponseException if the response is an error response
	 * @throws IOException
	 */
	public FVHealth listFVHealth() throws JFVErrorResponseException,
			IOException
	{
		EmptyFVRpcRequest lsr = new EmptyFVRpcRequest(
				EmptyFVRpcRequest.NoParamType.list_fv_health);
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

	/**
	 * Get the health of a slice.
	 *
	 * @param sliceName the name of the slice
	 * @return information about the health of the slice.
	 * @throws JFVErrorResponseException if the response is an error response
	 * @throws IOException
	 */
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

	/**
	 * Get statistics about a slice.
	 *
	 * @param sliceName the name of the slice.
	 * @return statistics about a slice.
	 * @throws JFVErrorResponseException if the response is an error response
	 * @throws IOException
	 */
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

	/**
	 * Updates a slice with new parameters.
	 *
	 * @param u the changes to the slice.
	 * @return true if the slice was successfully updated.
	 * @throws JFVErrorResponseException if the response is an error response
	 * @throws IOException
	 */
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

	/**
	 * Removes a slice.
	 * @param sliceName the name of the slice to be removed.
	 * @return true if the slice was successfully removed.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response
	 */
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

	/**
	 * Add new flowspaces.
	 *
	 * @param fs A list of flowspaces to add.
	 * @return the id of the flowspace request.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response.
	 */
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
	 * @param f the flowspace definition
	 * @return the id of the flowspace request.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response.
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
	 * @return the id of the flowspace request.
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

	/**
	 * Gets the version information of the FlowVisor instance.
	 * @return version information.
	 * @throws JFVErrorResponseException if the response is an error response.
	 * @throws IOException
	 */
	public Version getVersion() throws JFVErrorResponseException, IOException
	{
		EmptyFVRpcRequest lsr = new EmptyFVRpcRequest(
				EmptyFVRpcRequest.NoParamType.list_version);
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

	/**
	 * Updates the password for a slice.
	 * @param sliceName the name of the slice.
	 * @param password the new password.
	 * @return true if the password was successfully updated.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response.
	 */
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

	/**
	 * Update flowspaces.
	 *
	 * @param flowspaces a list of flowspaces to update.
	 * @return the id of the flowspace request.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response.
	 */
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

	/**
	 * Update a single flowspace.
	 * @param flowspace flowspace
	 * @return the id of the flowspace request.
	 * @throws IOException
	 * @throws JFVErrorResponseException if the response is an error response.
	 */
	public int updateFlowspace(Flowspace flowspace) throws IOException,
			JFVErrorResponseException
	{
		UpdateFlowspace l = new UpdateFlowspace();
		l.add(flowspace);
		return updateFlowspace(l);
	}
}
