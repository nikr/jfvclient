/**
 *
 */
package org.jfvclient.requests;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Represents an <code>update-slice</code> command.
 *
 * <pre>
 * {
 *  "slice-name" : &lt;name&gt;,
 *  "controller-host" : &lt;hostname&gt;,
 *         OPTIONAL
 *  "controller-port" : &lt;port-number&gt;,
 *         OPTIONAL
 *  "admin-contact" : &lt;email&gt;,
 *  "drop-policy" : "exact|rule",
 *         OPTIONAL
 *  "recv-lldp" : &lt;boolean&gt;,
 *         OPTIONAL
 *  "flowmod-limit" : &lt;number&gt;,
 *         OPTIONAL
 *  "rate-limit" : &lt;number&gt;,
 *         OPTIONAL
 *  "admin-status" : &lt;boolean&gt;
 *         OPTIONAL
 * }
 * </pre>
 *
 *The response is a <code>boolean</code>.
 *
 * @author Niklas Rehfeld
 *
 */
public class UpdateSlice
{
	@SerializedName("slice-name")
	private String sliceName;
	@SerializedName("controller-host")
	private String controllerHost;
	@SerializedName("controller-port")
	private int controllerPort;
	@SerializedName("admin-contact")
	private String adminContact;
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
	 * Simplest <code>update-slice</code> command possible, includes the two
	 * mandatory members, <code>slice-name</code> and <code>admin-contact</code>
	 * <p>
	 * You will generally want to call some of the <code>set*()</code> methods
	 * afterwards to actually modify something about the slice.
	 *
	 * @param sliceName
	 *            The name of the slice to modify. case sensitive.
	 * @param adminContact
	 *            The email of the slice admin.
	 */
	public UpdateSlice(String sliceName, String adminContact)
	{
		this.sliceName = sliceName;
		this.adminContact = adminContact;
	}

	/**
	 * @param dropPolicy
	 *            the dropPolicy to set
	 */
	public void setDropPolicy(String dropPolicy)
	{
		if (!dropPolicy.matches("exact|rule"))
			throw new IllegalArgumentException(
					"Drop policy must be either `rule` or `exact`, not "
							+ dropPolicy);
		this.dropPolicy = dropPolicy;
	}

	/**
	 * @return the sliceName
	 */
	public String getSliceName()
	{
		return sliceName;
	}

	/**
	 * @return the controllerHost
	 */
	public String getControllerHost()
	{
		return controllerHost;
	}

	/**
	 * @return the controllerPort
	 */
	public int getControllerPort()
	{
		return controllerPort;
	}

	/**
	 * @return the adminContact
	 */
	public String getAdminContact()
	{
		return adminContact;
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

	/**
	 * @param sliceName
	 *            the sliceName to set
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}

	/**
	 * @param controllerHost
	 *            the controllerHost to set
	 */
	public void setControllerHost(String controllerHost)
	{
		this.controllerHost = controllerHost;
	}

	/**
	 * @param controllerPort
	 *            the controllerPort to set
	 */
	public void setControllerPort(int controllerPort)
	{
		this.controllerPort = controllerPort;
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

}
