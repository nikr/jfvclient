/**
 *
 */
package org.jfvclient.responses;

import com.google.gson.annotations.SerializedName;

/**
 * The response from a <code>list-fv-health</code> request.
 *
 * corresponds to the following JSON:
 * <pre>
 * {
  "average-delay" : &lt;millisec-delay&gt;,
  "instant-delay" : &lt;millisec-delay&gt;,
  "active-db-sessions" : &lt;number&gt;,
  "idle-db-sessions" : &lt;number&gt;
}</pre>
 *
 * @author Niklas Rehfeld
 *
 */
public class FVHealth
{
	@SerializedName("average-delay")
	private int averageDelay;
	@SerializedName("instant-delay")
	private int instantDelay;
	@SerializedName("active-db-sessions")
	private int activeDbSessions;
	@SerializedName("idle-db-sessions")
	private int idleDbSessions;

	/**
	 * @return the averageDelay
	 */
	public int getAverageDelay()
	{
		return averageDelay;
	}

	/**
	 * @return the instantDelay
	 */
	public int getInstantDelay()
	{
		return instantDelay;
	}

	/**
	 * @return the activeDbSessions
	 */
	public int getActiveDbSessions()
	{
		return activeDbSessions;
	}

	/**
	 * @return the idleDbSessions
	 */
	public int getIdleDbSessions()
	{
		return idleDbSessions;
	}
}
