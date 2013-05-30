/**
 *
 */
package org.flowcreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.minidev.json.JSONObject;

import org.apache.http.ParseException;
import org.flowcreator.data.FlowSpace;
import org.flowcreator.data.MatchStruct;
import org.flowcreator.data.Slice;
import org.flowcreator.data.SliceAction;
import org.flowcreator.net.FVRpcConnector;
import org.flowcreator.queries.AddFlowspaceRequest;
import org.flowcreator.queries.AddSliceRequest;
import org.flowcreator.queries.ListSlicesRequest;
import org.flowcreator.queries.Version;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Parser;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

/**
 * @author Niklas Rehfeld
 *
 */
public class FlowCreator
{

	private Properties config;

	private FVRpcConnector connector;

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable
	{
		FlowCreator c = new FlowCreator();

		 c.test();
//		 c.testAddFlowSpace();
//		c.testAddSlice();

	}

	private void test() throws ParseException, IOException,
			JSONRPC2ParseException
	{
		Map<String, Object> response = connector.sendRequest(new Version());
		System.out.println("RESPONSE: " + response);
		// for (String s : response.keySet())
		// {
		// System.out.println(s + " -> " + response.get(s) + " ("
		// + response.get(s).getClass() + ")");
		// }

		response = connector.sendRequest(new ListSlicesRequest());
		System.out.println("RESPONSE: " + response);

	}

	private void testAddFlowSpace() throws ParseException, IOException,
			JSONRPC2ParseException
	{
		SliceAction[] actions =
			{ new SliceAction("Slice1", 7) };
		MatchStruct m = new MatchStruct();
		// m.put("in_port", 1);

		FlowSpace f = new FlowSpace("flow1", "any", 10, m, actions);
		// System.out.println(f.toJSONString());
		FlowSpace[] fs =
			{ f };
		AddFlowspaceRequest afr = new AddFlowspaceRequest(fs);
		// System.out.println(afr.getParameters());
		System.out.println("SENDING: " + afr.toJSONString());
		Map<String, Object> response = connector.sendRequest(afr);
		System.out.println("RESPONSE: " + response);

	}

	private void testAddSlice() throws ParseException, IOException,
			JSONRPC2ParseException
	{
		Map<String, Object> response = connector
				.sendRequest(new ListSlicesRequest());
		System.out.println("BEFORE: " + response);

		Slice s = new Slice("Slice1", "tcp:192.168.56.1:6633", "nik@localhost",
				"hatstand");
		AddSliceRequest sr = new AddSliceRequest(s);
		System.out.println("SENDING: " + sr.toJSONString());
		response = connector.sendRequest(sr);
		System.out.println("RECEIVED " + response);

		response = connector.sendRequest(new ListSlicesRequest());
		System.out.println("AFTER: " + response);

	}

	/**
	 * Gets the configuration from the default properties file
	 * (./resources/visor.properties) sets up the connector.
	 *
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws NumberFormatException
	 */
	public FlowCreator() throws NumberFormatException, CertificateException,
			NoSuchAlgorithmException, IOException
	{
		getConfigFromFile(new File("resources/visor.properties"));

		this.connector = new FVRpcConnector(config.getProperty("hostname",
				"localhost"), config.getProperty("username"),
				config.getProperty("password"), Integer.parseInt(config
						.getProperty("port")));

	}

	private void getConfigFromFile(File f)
	{
		config = new Properties();
		try
		{
			config.load(new FileInputStream(f));
		} catch (FileNotFoundException e)
		{
			System.err.println(e.getLocalizedMessage());
			// e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
