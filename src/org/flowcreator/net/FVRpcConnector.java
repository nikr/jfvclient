/**
 *
 */
package org.flowcreator.net;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.flowcreator.queries.FVRpcQueryObject;
import org.flowcreator.queries.Version;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

/**
 * @author Niklas Rehfeld
 *
 */
public class FVRpcConnector
{

	private String hostAddr;
	private String hostName;
	private int hostPort;
	private String userName;
	private String userPassword;
	private HttpPost postRequest;
	private DefaultHttpClient httpc;


	/**
	 * @param hostName
	 * @param userName
	 * @param userPassword
	 * @param hostPort
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 */
	public FVRpcConnector(String hostName, String userName,
			String userPassword, int hostPort) throws CertificateException, NoSuchAlgorithmException, IOException
	{

		this.userName = userName;
		this.userPassword = userPassword;
		this.hostName = hostName;
		this.hostAddr = "https://" + hostName;
		if (hostPort >= 0)
		{
			this.hostPort = hostPort;
			// this.hostAddr += ":" + hostPort;
		}

		postRequest = new HttpPost(hostAddr + ":" + hostPort);
		initUnsafe();
	}

	/**
	 * FIXME not a very elegant solution.
	 *
	 * In fact it's not a solution at all, it's a workaround for very specific
	 * problems to do with the setup here... You should really have all the
	 * https stuff set up already...
	 *
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public void initUnsafe() throws CertificateException, IOException, NoSuchAlgorithmException
	{
//		KeyStore ks = KeyStore.getInstance("JKS", "SUN");

//		ks.load(null);
		// This bit is because it's running on a virtual machine inside
		// another host. So we have to bypass the hostname verifier.
		SchemeRegistry reg = new SchemeRegistry();
		SSLContext cont = SSLContext.getDefault();
		SSLSocketFactory sf = new SSLSocketFactory(cont,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		reg.register(new Scheme("https", hostPort, sf));

		httpc = new DefaultHttpClient(new BasicClientConnectionManager(reg));
//				new DefaultHttpClient().getParams());

		// set the username and password credentials for the flowvisor user.
		httpc.getCredentialsProvider().setCredentials(
				new AuthScope(null, hostPort),
				new UsernamePasswordCredentials(userName, userPassword));

	}



	/**
	 * Sends a request to the FlowVisor server and returns the response.
	 *
	 * TODO Check if the response is a valid JSON response and throw an
	 * exception if it isn't.
	 *
	 * @param jsonRequest
	 *            JSONRPC 2.0 request to send to FlowVisor
	 * @return A String consisting of the JSON-encoded response.
	 *
	 * @throws IOException
	 * @throws JSONRPC2ParseException
	 * @throws ParseException
	 */
	public String sendRequest(String jsonRequest)
			throws ParseException, JSONRPC2ParseException, IOException
	{
		FVRpcQueryObject v = new Version();
		JSONRPC2Response res = v.sendRequest(httpc, hostAddr);
		System.out.println(res.toString());
		String result = res.toString();
		return result;

	}

	public Map<String, Object> sendRequest(FVRpcQueryObject query) throws ParseException, IOException, JSONRPC2ParseException
	{
		JSONRPC2Response resp = query.sendRequest(httpc, hostAddr);
		if (resp.indicatesSuccess())
		{
			return resp.toJSONObject();
		}
		else
		{
			throw new IOException("Something went wrong " + resp.getError().getLocalizedMessage() + "\n" + resp.toJSONString());
		}
	}
}
