/**
 *
 */
package org.jfvclient.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Implementation of an add-slice command.
 *
 * JSON format:
 *
 * <pre>
 * {
 *  "slice-name" : <name>,
 *  "controller-url" : <pseudo-url>,
 *  "admin-contact" : <email>,
 *  "password" : <password>,
 *  "drop-policy" : "exact|rule",
 *           OPTIONAL DEFAULT "exact"
 *  "recv-lldp" : <boolean>,
 *           OPTIONAL DEFAULT false
 *  "flowmod-limit" : <number>,
 *         OPTIONAL DEFAULT -1
 *  "rate-limit" : <number>,
 *         OPTIONAL DEFAULT -1
 *  "admin-status" : <boolean>
 *         OPTIONAL DEFAULT true
 * }
 * </pre>
 *
 * and returns a boolean.
 *
 * @author Niklas Rehfeld
 *
 */
public class AddSlice
{
	@SerializedName("slice-name")
	private String sliceName;
	@SerializedName("controller-url")
	private String controllerURL;
	@SerializedName("admin-contact")
	private String adminContact;
	private String password;
	@SerializedName("drop-policy")
	private String dropPolicy;
	@SerializedName("recv-lldp")
	private boolean recvLLDP;
	@SerializedName("flowmod-limit")
	private int flowmodLimit;
	@SerializedName("rate-limit")
	private int rateLimit;
	@SerializedName("admin-status")
	private boolean adminStatus;

	/**
	 * This constructor fills in all of the mandatory elements of an
	 * {@code add-slice} query.
	 *
	 * @param sliceName
	 *            the name of the slice.
	 * @param controllerURL
	 *            the url of the controller. must be in format
	 *            {@code proto:host[:port]} where {@code proto} is usually TCP
	 *            or UDP.
	 * @param adminContact
	 *            the contact of the slice administrator.
	 * @param password
	 *            the administrative password for the slice.
	 */
	public AddSlice(String sliceName, String controllerUrl,
			String adminContact, String password)
	{
		this.sliceName = sliceName;
		if (isValidControllerUrl(controllerUrl))
		{
			this.controllerURL = controllerUrl;
		} else
			throw new IllegalArgumentException("Not a valid controller URL:  "
					+ controllerUrl);
		this.adminContact = adminContact;
		this.password = password;
		setDefaults();
	}

	private boolean isValidControllerUrl(String url)
	{
		return url.matches("(tcp|udp):\\w+(:\\d+)?");
	}

	private void setDefaults()
	{
		setDropPolicy("exact");
		setRecvLLDP(false);
		setFlowmodLimit(-1);
		setRateLimit(-1);
		setAdminStatus(true);
	}

	/**
	 * @param sliceName
	 *            the sliceName to set
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}

	/**
	 * @param controllerURL
	 *            the controllerURL to set
	 */
	public void setControllerURL(String controllerURL)
	{
		this.controllerURL = controllerURL;
	}

	/**
	 * @param adminContact
	 *            the adminContact to set
	 */
	public void setAdminContact(String adminContact)
	{
		this.adminContact = adminContact;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @param dropPolicy
	 *            the dropPolicy to set
	 */
	public void setDropPolicy(String dropPolicy)
	{
		if (!isValidDropPolicy(dropPolicy))
			throw new IllegalArgumentException(
					"Drop policy must be either `rule` or `exact`, not "
							+ dropPolicy);
		this.dropPolicy = dropPolicy;
	}

	private boolean isValidDropPolicy(String dropPolicy)
	{
		return dropPolicy.matches("exact|rule");
	}

	/**
	 * @param recvLLDP
	 *            the recvLLDP to set
	 */
	public void setRecvLLDP(boolean recvLLDP)
	{
		this.recvLLDP = recvLLDP;
	}

	/**
	 * @param flowmodLimit
	 *            the flowmodLimit to set
	 */
	public void setFlowmodLimit(int flowmodLimit)
	{
		this.flowmodLimit = flowmodLimit;
	}

	/**
	 * @param rateLimit
	 *            the rateLimit to set
	 */
	public void setRateLimit(int rateLimit)
	{
		this.rateLimit = rateLimit;
	}

	/**
	 * @param adminStatus
	 *            the adminStatus to set
	 */
	public void setAdminStatus(boolean adminStatus)
	{
		this.adminStatus = adminStatus;
	}

	/**
	 * @return the sliceName
	 */
	public String getSliceName()
	{
		return sliceName;
	}

	/**
	 * @return the controllerURL
	 */
	public String getControllerURL()
	{
		return controllerURL;
	}

	/**
	 * @return the adminContact
	 */
	public String getAdminContact()
	{
		return adminContact;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return the dropPolicy
	 */
	public String getDropPolicy()
	{
		return dropPolicy;
	}

	/**
	 * @return the recvLLDP
	 */
	public boolean isRecvLLDP()
	{
		return recvLLDP;
	}

	/**
	 * @return the flowmodLimit
	 */
	public int getFlowmodLimit()
	{
		return flowmodLimit;
	}

	/**
	 * @return the rateLimit
	 */
	public int getRateLimit()
	{
		return rateLimit;
	}

	/**
	 * @return the adminStatus
	 */
	public boolean isAdminStatus()
	{
		return adminStatus;
	}

}
