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
