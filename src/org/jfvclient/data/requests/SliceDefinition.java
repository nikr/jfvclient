/**
 *
 */
package org.jfvclient.data;

import java.util.Map;

//import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

/**
 * Partial implementation of an add-slice command. Does not include the
 * non-mandatory fields.
 *
 * @author Niklas Rehfeld
 *
 */
public class Slice
{
	private String name;
	/** needs to be of form proto:hostname[:port] where 'proto' is probably TCP or UDP**/
	private String controllerURL;
	private String adminEmail;
	private String password;
	private String dropPolicy;
	private Boolean adminStatus;
	private boolean isResponse;

	public Slice(Map<String, Object> results)
	{

	}

	/**
	 * @param name
	 * @param controllerURL
	 * @param adminEmail
	 * @param password
	 */
	public Slice(String name, String controllerURL, String adminEmail,
			String password)
	{
		this.name = name;
		this.controllerURL = controllerURL;
		this.adminEmail = adminEmail;
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the controllerURL
	 */
	public String getControllerURL()
	{
		return controllerURL;
	}

	/**
	 * @param controllerURL the controllerURL to set
	 */
	public void setControllerURL(String controllerURL)
	{
		this.controllerURL = controllerURL;
	}

	/**
	 * @return the adminEmail
	 */
	public String getAdminEmail()
	{
		return adminEmail;
	}

	/**
	 * @param adminEmail the adminEmail to set
	 */
	public void setAdminEmail(String adminEmail)
	{
		this.adminEmail = adminEmail;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the isResponse
	 */
	public boolean isResponse()
	{
		return isResponse;
	}


	/**
	 * @return the dropPolicy
	 */
	public String getDropPolicy()
	{
		return dropPolicy;
	}

	/**
	 * @return the adminStatus
	 */
	public Boolean getAdminStatus()
	{
		return adminStatus;
	}

	/**
	 * @param dropPolicy the dropPolicy to set
	 */
	public void setDropPolicy(String dropPolicy)
	{
		this.dropPolicy = dropPolicy;
	}

	/**
	 * @param adminStatus the adminStatus to set
	 */
	public void setAdminStatus(Boolean adminStatus)
	{
		this.adminStatus = adminStatus;
	}



}
