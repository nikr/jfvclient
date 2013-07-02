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
 * @author Niklas Rehfeld
 *
 */
public class SliceInfo
{
	@SerializedName("slice-name")
	private String sliceName;
	@SerializedName("controller-url")
	private String controllerURL;
	@SerializedName("admin-contact")
	private String adminContact;
	@SerializedName("drop-policy")
	private String dropPolicy;
	@SerializedName("recv-lldp")
	private boolean recvLLDP;
	@SerializedName("current-rate")
	private String  currentRate;
	@SerializedName("current-flowmod-usage")
	private int currentFlowmodUsage;
	private String msg;
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
	 * @return the currentRate
	 */
	public String getCurrentRate()
	{
		return currentRate;
	}
	/**
	 * @return the currentFlowmodUsage
	 */
	public int getCurrentFlowmodUsage()
	{
		return currentFlowmodUsage;
	}
	/**
	 * @return the msg
	 */
	public String getMsg()
	{
		return msg;
	}
	/**
	 * @param sliceName the sliceName to set
	 */
	public void setSliceName(String sliceName)
	{
		this.sliceName = sliceName;
	}
	/**
	 * @param controllerURL the controllerURL to set
	 */
	public void setControllerURL(String controllerURL)
	{
		this.controllerURL = controllerURL;
	}
	/**
	 * @param adminContact the adminContact to set
	 */
	public void setAdminContact(String adminContact)
	{
		this.adminContact = adminContact;
	}
	/**
	 * @param dropPolicy the dropPolicy to set
	 */
	public void setDropPolicy(String dropPolicy)
	{
		this.dropPolicy = dropPolicy;
	}
	/**
	 * @param recvLLDP the recvLLDP to set
	 */
	public void setRecvLLDP(boolean recvLLDP)
	{
		this.recvLLDP = recvLLDP;
	}
	/**
	 * @param currentRate the currentRate to set
	 */
	public void setCurrentRate(String currentRate)
	{
		this.currentRate = currentRate;
	}
	/**
	 * @param currentFlowmodUsage the currentFlowmodUsage to set
	 */
	public void setCurrentFlowmodUsage(int currentFlowmodUsage)
	{
		this.currentFlowmodUsage = currentFlowmodUsage;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg)
	{
		this.msg = msg;
	}



}
