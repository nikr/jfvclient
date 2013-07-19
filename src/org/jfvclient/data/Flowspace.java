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
package org.jfvclient.data;

import java.util.List;

import org.jfvclient.requests.ListFlowspace;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Description of a flowspace. This class is used in several
 * circumstances. It is used as an argument for the <code>add-flowspace</code>
 * and <code>update-flowspace</code> requests, and it is used in the response to
 * a <code>list-flowspace</code> request. <br/>
 * The relevant JSON for the requests is as follows :
 * <p />
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
 * According to <code>fvctl help add-flowspace</code> AddFlowspace:
 * Creates a new rule in the flowspace with the given name. <br/>
 * Queues can be assigned to the slice by passing a comma seperated list of 
 * queue ids, for example '-q 1,2,3'. <br/>
 * If a forced queue is defined, then any flowmod matching this rule will see 
 * its OUTPUT actions replaced with the queue id given in the forced queue 
 * option. Note: The forced queue should be defined in the queue option and 
 * all these queue ids should be defined with the appropriate port on the 
 * switch. <br/>
 * See the fvctl man page for information on the format of <dpid>, <match>, and 
 * <slice-perm>.
 * <p/>
 * 
 * update-flowspace request (the same, but most fields are optional) :
 *
 * <pre>
 * [
 *  {
 *    "name" : &lt;string&gt;
 *    "dpid" : &lt;dpid&gt;,
 *            OPTIONAL
 *    "priority" : &lt;number&gt;,
 *            OPTIONAL
 *    "match" : &lt;match-struct&gt;,
 *            OPTIONAL
 *    "queues" : [ &lt;queue_id&gt; ],
 *             OPTIONAL
 *    "force-enqueue" : &lt;queue_id&gt;,
 *             OPTIONAL
 *    "slice-action" :
 *    [
 *     {
 *       "slice-name" : &lt;name&gt; ",
 *       "permission" : &lt;perm-value&gt;
 *     } OPTIONAL
 *    ]
 *  }
 * ]
 * </pre>
 *
 * also returns a number.
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
	 * This constructor sets all of the fields mandatory for an
	 * <code>add-flowspace</code> request.
	 *
	 *
	 * @param name
	 *            flowspace name
	 * @param dpid
	 *            the DPID that this flowspace will apply to.
	 * @param prio
	 *            the priority (bigger is higher)
	 * @param matches
	 *            a match structure.
	 * @param actions
	 *            A list of slice actions.
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
	 * This constructor sets all of the fields that are mandatory in an
	 * <code>update-flowspace</code> request.
	 *
	 * @param name
	 *            the name of the flowspace
	 * @param dpid
	 *            the dpid that this flowspace applies to.
	 */
	public Flowspace(String name, Dpid dpid)
	{
		this.name = name;
		this.dpid = dpid;
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

	/**
	 * @return returns true iff the names and DPIDs are the same.
	 */
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Flowspace))
			return false;

		Flowspace other = (Flowspace) o;
		return (name.equals(other.getName()) && dpid.equals(other.getDpid()));
	}
}
