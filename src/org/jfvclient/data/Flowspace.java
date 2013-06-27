/**
 *
 */
package org.jfvclient.data;

import java.util.List;

import org.jfvclient.requests.ListFlowspace;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used in two circumstances. It is used as an argument for the
 * <code>add-flowspace</code> request, and it is used in the response to a
 * <code>list-flowspace</code> request. <br/>
 * The relevant JSON for the two requests is as follows:
 *
 * <code>add-flowspace</code> request.
 *
 * <pre>
 *  [
 *  {
 *    "name" : &lt;string&gt;,
 *    "dpid" : &lt;dpid&gt;,
 *    "priority" : &lt;number&gt;,
 *    "match" : &lt;match-struct&gt;,
 *    "queues" : [ &lt;queue_id&gt; ],
 *             OPTIONAL
 *    "force-enqueue" : &lt;queue_id&gt;,
 *             OPTIONAL
 *    "slice-action" :
 *    [
 *     {
 *       "slice-name" : &lt;name&gt; ",
 *       "permission" : &lt;perm-value&gt;
 *     }
 *    ]
 *  }
 * ]
 * </pre>
 *
 * returns a number, which is the request number used in
 * <code>list-fs-status</code> requests.
 *
 * <p/>
 *
 * <code>list-flowspace</code> request returns a similar object, but with the
 * request index also in the data:
 *
 * <pre>
 *  [
 *  {
 *    "id" : &lt;number&gt;,
 *    "name" : &lt;string&gt;,
 *    "dpid" : &lt;dpid&gt;,
 *    "priority" : &lt;number&gt;,
 *    "match" : &lt;match-struct&gt;,
 *    "queues" : [ &lt;queue_id&gt; ],
 *             OPTIONAL
 *    "force-enqueue" : &lt;queue_id&gt;,
 *             OPTIONAL
 *    "slice-action" :
 *    [
 *     {
 *       "slice-name" : &lt;name&gt; ",
 *       "permission" : &lt;perm-value&gt;
 *     }
 *    ]
 *  }
 * ]
 * </pre>
 *
 *
 * @author Niklas Rehfeld
 *
 */
public class Flowspace
{
	private String name;
	private Dpid dpid;
	private Integer priority;
	private MatchStruct match;
	private List<String> queues;
	@SerializedName("force-enqueue")
	private String forceEnqueue;
	@SerializedName("slice-action")
	private List<SliceAction> sliceActions;
	private Integer id; // only for list-flowspace responses.

	/**
	 *
	 * @param name
	 * @param dpid
	 * @param prio
	 * @param matches
	 * @param actions
	 */
	public Flowspace(String name, Dpid dpid, Integer prio, MatchStruct matches,
			List<SliceAction> actions)
	{
		this.name = name;
		this.dpid = dpid;
		this.priority = prio;
		this.match = matches;
		this.sliceActions = actions;
	}

	/**
	 * Add a single SliceAction to the list.
	 *
	 * @param action
	 */
	public void addSliceAction(SliceAction action)
	{
		sliceActions.add(action);
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the dpid
	 */
	public Dpid getDpid()
	{
		return dpid;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority()
	{
		return priority;
	}

	/**
	 * @return the match
	 */
	public MatchStruct getMatch()
	{
		return match;
	}

	/**
	 * @return the queues
	 */
	public List<String> getQueues()
	{
		return queues;
	}

	/**
	 * @return the forceEnqueue
	 */
	public String getForceEnqueue()
	{
		return forceEnqueue;
	}

	/**
	 * @return the sliceActions
	 */
	public List<SliceAction> getSliceActions()
	{
		return sliceActions;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param dpid
	 *            the dpid to set
	 */
	public void setDpid(Dpid dpid)
	{
		this.dpid = dpid;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Integer priority)
	{
		this.priority = priority;
	}

	/**
	 * @param match
	 *            the match to set
	 */
	public void setMatch(MatchStruct match)
	{
		this.match = match;
	}

	/**
	 * @param queues
	 *            the queues to set
	 */
	public void setQueues(List<String> queues)
	{
		this.queues = queues;
	}

	/**
	 * @param forceEnqueue
	 *            the forceEnqueue to set
	 */
	public void setForceEnqueue(String forceEnqueue)
	{
		this.forceEnqueue = forceEnqueue;
	}

	/**
	 * @param sliceActions
	 *            the sliceActions to set
	 */
	public void setSliceActions(List<SliceAction> sliceActions)
	{
		this.sliceActions = sliceActions;
	}

	/**
	 * This method is only used if this object is a response to a
	 * {@link ListFlowspace} request. Otherwise its value is undefined (but
	 * probably null).
	 *
	 * @return the request ID
	 */
	public Integer getId()
	{
		return id;
	}

}
