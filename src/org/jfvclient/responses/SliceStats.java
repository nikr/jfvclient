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

import java.util.Map;

/**
 *
 *
 * <pre>
 * {
 *   "tx" : {
 *            &lt;sender-name&gt; : {
 *              &lt;msg-type-name&gt; : &lt;value&gt;,
 *              &lt;msg-type-name&gt; : &lt;value&gt;,
 *              .
 *              .
 *              .
 *              &lt;msg-type-name&gt; : &lt;value&gt;
 *            }
 *          },
 *   "rx" : {
 *            &lt;sender-name&gt; : {
 *              &lt;msg-type-name&gt; : &lt;value&gt;,
 *              &lt;msg-type-name&gt; : &lt;value&gt;,
 *              .
 *              .
 *              .
 *              &lt;msg-type-name&gt; : &lt;value&gt;
 *            }
 *          },
 *   "drop" : {
 *            &lt;sender-name&gt; : {
 *              &lt;msg-type-name&gt; : &lt;value&gt;,
 *              &lt;msg-type-name&gt; : &lt;value&gt;,
 *              .
 *              .
 *              .
 *              &lt;msg-type-name&gt; : &lt;value&gt;
 *            }
 *          }
 * }
 * </pre>
 *
 * TODO: Turn this into a more useful class, with the members in proper types.
 * @author Niklas Rehfeld
 */
public class SliceStats
{
	private Map<String, Map<String, Number>> tx;
	private Map<String, Map<String, Number>> rx;
	private Map<String, Map<String, Number>> drop;

	/**
	 * @return the tx
	 */
	public Map<String, Map<String, Number>> getTx()
	{
		return tx;
	}

	/**
	 * @return the rx
	 */
	public Map<String, Map<String, Number>> getRx()
	{
		return rx;
	}

	/**
	 * @return the drop
	 */
	public Map<String, Map<String, Number>> getDrop()
	{
		return drop;
	}

}
