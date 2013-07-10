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

import com.google.gson.annotations.SerializedName;

/**
 * A slice-action structure, as found in <code>add-flowspace</code> and
 * <code>update-flowspace</code> requests.
 *
 * The following description is stolen from the <code>fvctl</code> man page, the
 * source of which is at <a
 * href="https://github.com/OPENNETWORKINGLAB/flowvisor">https://github
 * .com/OPENNETWORKINGLAB/flowvisor</a>
 * <p>
 * Slice actions is a comma separated list of slices that have control over a
 * specific FlowSpace. Slice actions are of the form
 * "slicename1=perm[slicename2=perm[...]]". Each slice can have three types of
 * permissions over a flowspace: DELEGATE, READ, and WRITE. Permissions are
 * currently a bitmask specified as an integer, with DELEGATE=1, READ=2,
 * WRITE=4. So, "alice=5,bob=2" would give Alice's slice DELEGATE and WRITE
 * permissions (1+4=5), but Bob only READ permissions. Improving this interface
 * is on the TODO list.
 * <p>
 * <ul>
 * <li>DELEGATE A slice can delegate control of this flowspace to another slice.
 * It also has permissions to un-delegate/reclaim the flowspace.</li>
 *
 * <li>READ A slice receives packet_in's matching this flow entry, can send LLDP
 * messages and stats to switches in this flow entry, but cannot write to or
 * change the switch's flow table. This is useful for implementing a monitoring
 * slice.</li>
 *
 * <li>WRITE A slice has all of the permissions of READ but can also write to
 * the flow table if the flow_mod matches this flow entry. FlowVisor will try to
 * rewrite a flow_mod (if necessary) as the logical intersection of a slice's
 * flow_mod and the union of its FlowSpace.</li>
 * </ul>
 *
 * @see Flowspace
 * @author Niklas Rehfeld
 *
 */
public class SliceAction
{
	@SerializedName("slice-name")
	private String sliceName;
	private int permission;

	/**
	 * A slice receives packet_in's matching this flow entry, can send LLDP
	 * messages and stats to switches in this flow entry, but cannot write to or
	 * change the switch's flow table. This is useful for implementing a
	 * monitoring slice.
	 */
	public static final int READ_PERMISSION = 2;
	/**
	 * A slice can delegate control of this flowspace to another slice. It also
	 * has permissions to un-delegate/reclaim the flowspace.
	 */
	public static final int DELEGATE_PERMISSION = 1;
	/**
	 * A slice has all of the permissions of READ but can also write to the flow
	 * table if the flow_mod matches this flow entry. FlowVisor will try to
	 * rewrite a flow_mod (if necessary) as the logical intersection of a
	 * slice's flow_mod and the union of its FlowSpace.
	 */
	public static final int WRITE_PERMISSION = 4;

	/**
	 *
	 *
	 * @param slicename
	 *            The name of a the slice which has the permission
	 * @param permission
	 *            The permissions given to the slice.
	 */
	public SliceAction(String slicename, int permission)
	{

		this.sliceName = slicename;

		this.setPermission(permission);
	}

	/**
	 * Not entirely sure about this one. do some of these permissions make
	 * sense, e.g read+delegate? or read+write, as write already has the read
	 * permissions?
	 *
	 * TODO find out what permissions are actually valid.
	 *
	 * @param perm
	 *            permission
	 * @return true if this is a valid permission.
	 */
	private boolean isValidPermission(int perm)
	{
		return (perm <= 6);
	}

	/**
	 * @return the permission
	 */
	public int getPermission()
	{
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(int permission)
	{
		if (!isValidPermission(permission))
		{
			throw new IllegalArgumentException("invalid permission: "
					+ permission);
		}
		this.permission = permission;
	}

	/**
	 * @return the sliceName
	 */
	public String getSliceName()
	{
		return sliceName;
	}

	/**
	 * @param sliceName the sliceName to set
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}
}
