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
package org.jfvclient.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Implementation of an add-slice command.
 *
 * JSON format:
 *
 * <pre>
 * {
 *  "slice-name" : &lt;name&gt;,
 *  "controller-url" : &lt;pseudo-url&gt;,
 *  "admin-contact" : &lt;email&gt;,
 *  "password" : &lt;password&gt;,
 *  "drop-policy" : "exact|rule",
 *           OPTIONAL DEFAULT "exact"
 *  "recv-lldp" : &lt;boolean&gt;,
 *           OPTIONAL DEFAULT false
 *  "flowmod-limit" : &lt;number&gt;,
 *         OPTIONAL DEFAULT -1
 *  "rate-limit" : &lt;number&gt;,
 *         OPTIONAL DEFAULT -1
 *  "admin-status" : &lt;boolean&gt;
 *         OPTIONAL DEFAULT true
 * }
 * </pre>
 *
 * and returns a boolean.
 * <p>
 * The
 * <code> fvctl </code> help states the following: <br/>
 *
 * Creates a new slice. <br/>
 * The slicename can contain any character except except the newline (Note:
 * slicenames are case insensitive). <br/>
 * The controller url is of the form tcp:hostname:port, so for example
 * tcp:example.com:12345 is a valid controller url. <br/>
 * The admin email is used for administrative purposes if there is a problem
 * with the slice The remaining parameters are optional.<br/>
 * The drop policy, defines what kind of rule should be pushed to the switch if
 * there is no response from the controller (this includes if there is no
 * controller connected). Currently two modes are supported, 'exact' and 'rule',
 * 'exact' matches the packet exactly and 'rule' matches the rule that the
 * packet triggered. <br/>
 * Flowmod limit limits the number of flowmods this slice can emit.<br/>
 * The rate limits the number of OpenFlow messages that can be sent to the
 * switches from this slice. <br/>
 * You may also set the slice to receive unknown LLDP messages. <br/>
 * Optionally, you may set this slice as disabled initially, this is then
 * changed via an update-slice call. <br/>
 * Finally, the password is the slice password.
 * </p>
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
    private Boolean recvLLDP;
    @SerializedName("flowmod-limit")
    private Integer flowmodLimit;
    @SerializedName("rate-limit")
    private Integer rateLimit;
    @SerializedName("admin-status")
    private Boolean adminStatus;

    /**
     * This constructor fills in all of the mandatory elements of an
     * {@code add-slice} query.
     *
     * @param sliceName the name of the slice. The slicename can contain any
     * character except except the newline (Note: slicenames are case
     * insensitive).
     * @param controllerUrl the url of the controller. must be in format
     * {@code proto:host[:port]} where {@code proto} is usually TCP or UDP.
     * @param adminContact the contact of the slice administrator.
     * @param password the administrative password for the slice.
     */
    public AddSlice(String sliceName, String controllerUrl,
                    String adminContact, String password)
    {
        this.sliceName = sliceName;
        if (isValidControllerUrl(controllerUrl))
        {
            this.controllerURL = controllerUrl;
        }
        else
        {
            throw new IllegalArgumentException("Not a valid controller URL:  "
                    + controllerUrl);
        }
        this.adminContact = adminContact;
        this.password = password;
        setDefaults();
    }

    /**
     * This one's a bit dangerous. Make sure you set all the relevant fields
     * before you send it. (i.e. call {@link #isValidRequest() })
     */
    public AddSlice()
    {
        setDefaults();
    }

    /**
     * Checks if all the mandatory fields are filled in and that they are
     * well-formed.
     *
     * @return
     */
    public boolean isValidRequest()
    {
        return isValidControllerUrl(this.controllerURL)
                && (sliceName != null)
                && (adminContact != null)
                && (password != null);
    }

    private boolean isValidControllerUrl(String url)
    {
        return (url != null && url.matches("(tcp|udp):\\w+(:\\d+)?"));
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
     * Sets the SLice name. The slicename can contain any character except
     * except the newline (Note: slicenames are case insensitive).
     *
     * @param sliceName the slice name to set.
     */
    public void setSliceName(String sliceName)
    {
        this.sliceName = sliceName;
    }

    /**
     * Set the controller URL. The controller url is of the form
     * <code>tcp:hostname:port</code>, so for example tcp:example.com:12345 is a
     * valid controller url.
     *
     * @param controllerURL the controllerURL to set
     */
    public void setControllerURL(String controllerURL)
    {
        this.controllerURL = controllerURL;
    }

    /**
     * Sets the admininstrator's contact (email). The admin email is used for
     * administrative purposes if there is a problem with the slice
     *
     * @param adminContact the admin contact to set
     */
    public void setAdminContact(String adminContact)
    {
        this.adminContact = adminContact;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Sets the slice's drop policy. The drop policy defines what kind of rule
     * should be pushed to the switch if there is no response from the
     * controller (this includes if there is no controller connected). Currently
     * two modes are supported, 'exact' and 'rule', 'exact' matches the packet
     * exactly and 'rule' matches the rule that the packet triggered.
     *
     * @param dropPolicy the dropPolicy to set
     */
    public void setDropPolicy(String dropPolicy)
    {
        if (!isValidDropPolicy(dropPolicy))
        {
            throw new IllegalArgumentException(
                    "Drop policy must be either `rule` or `exact`, not "
                    + dropPolicy);
        }
        this.dropPolicy = dropPolicy;
    }

    private boolean isValidDropPolicy(String dropPolicy)
    {
        return dropPolicy.matches("exact|rule");
    }

    /**
     * @param recvLLDP the recvLLDP to set
     */
    public void setRecvLLDP(boolean recvLLDP)
    {
        this.recvLLDP = recvLLDP;
    }

    /**
     * Sets the Flowmod limit for the slice. Flowmod limit limits the number of
     * flowmods this slice can emit.
     *
     * @param flowmodLimit the flowmodLimit to set
     */
    public void setFlowmodLimit(int flowmodLimit)
    {
        this.flowmodLimit = flowmodLimit;
    }

    /**
     * Sets the OF message rate limit for the slice. The rate limits the number
     * of OpenFlow messages that can be sent to the switches from this slice.
     *
     * @param rateLimit the rateLimit to set
     */
    public void setRateLimit(int rateLimit)
    {
        this.rateLimit = rateLimit;
    }

    /**
     * Whether the slice is initally disabled or not. You may set this slice as
     * disabled initially, this is then changed via an update-slice call.
     *
     * @param adminStatus set to false to initially disable the slice.
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
