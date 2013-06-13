/**
 *
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
