/**
 *
 */
package org.jfvclient.queries;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Parser;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

/**
 * @author Niklas Rehfeld
 *
 */
public abstract class FVRpcQueryObject
{

	JSONRPC2Request request;
	String method;
//	Map<String, Object> params;

	public static final ContentType MIME_TYPE = ContentType
			.create("application/json");

	/**
	 *
	 *
	 * @param method
	 * @param requestId
	 */
	FVRpcQueryObject(String method, String requestId)
	{
//		params = new HashMap<String, Object>();
		request = new JSONRPC2Request(method, requestId);
//		request.setNamedParams(params);
		this.method = method;

	}

	/**
	 *
	 * @param method
	 */
	public FVRpcQueryObject(String method)
	{
		this(method, method + "-" + Math.round(Math.random() * 1000));
		// this.method = method;
	}

	/**
	 * Returns the method name for the rpc call.
	 *
	 * @return
	 */
	public String getMethod()
	{
		return method;
	}

	/**
	 * returns a map of the parameters used in the request.
	 *
	 * @return
	 */
	public Map<String, Object> getParameters()
	{
		return request.getNamedParams();
	}

	public void addNamedParameter(String name, Object value)
	{
		Map<String,Object> params = request.getNamedParams();
		if (params== null && request.getPositionalParams() == null) //only if not clobbering positional params.
		{
			params = new HashMap<String, Object>();
			request.setNamedParams(params);
		}
		params.put(name, value);
	}

	public void setNamedParams(JSONObject p)
	{
		request.setNamedParams(p);
	}

	public void setPositionalParams(List<Object> params)
	{
		request.setPositionalParams(params);
	}

	/**
	 * The expected number of parameters. This should be overridden by
	 * subclasses.
	 *
	 * @return
	 */
	public abstract int getMinParameterCount();

//	public abstract Collection<String> getExpectedResponseKeys();

	// public void setHost(String hostname, int port);

	/**
	 * Send a request to the specified host, using a HttpClient. The HttpClient
	 * is specified because in my setup I have to make a special one to acccept
	 * arbitrary self-signed SSL certificates etc.
	 *
	 * This also allows the client to be set up with a default port so that you
	 * don't have to enter it into the url.
	 *
	 * @param client
	 *            an initialised HttpClient.
	 * @param hostURL
	 *            the host URL.
	 * @return
	 * @throws ParseException
	 *             If the response's header elements can't be parsed.
	 * @throws IOException
	 *             If the request can't be sent.
	 * @throws JSONRPC2ParseException
	 *             If the response is not a valid JSON-RPC response.
	 */
	public JSONRPC2Response sendRequest(HttpClient client, String hostURL)
			throws ParseException, IOException, JSONRPC2ParseException
	{


		HttpPost postRequest = new HttpPost(hostURL);
		postRequest.setEntity(new StringEntity(request.toJSONString(),
				MIME_TYPE));

		HttpResponse hResponse = client.execute(postRequest);

		String resp = EntityUtils.toString(hResponse.getEntity());

		return new JSONRPC2Parser().parseJSONRPC2Response(resp);
//		return new JSONRPC2Response(request.getID()).parse(resp);
	}

	public String toJSONString()
	{
		return request.toJSONString();
	}

}
