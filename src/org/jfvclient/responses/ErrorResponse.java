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

/**
 * ErrorResponse message from FlowVisor.
 *
 * The codes defined by FlowVisor are:
 * <table>
 * <tbody>
 * <tr>
 * <th>code</th>
 * <th>message</th>
 * <th>meaning</th>
 * </tr>
 * <tr>
 * <td>-32700</td>
 * <td>Parse error</td>
 * <td>Invalid JSON was received by the server.</td>
 * </tr>
 * <tr>
 * <td>-32600</td>
 * <td>Invalid Request</td>
 * <td>The JSON sent is not a valid Request object.</td>
 * </tr>
 * <tr>
 * <td>-32601</td>
 * <td>Method not found</td>
 * <td>The method does not exist / is not available.</td>
 * </tr>
 * <tr>
 * <td>-32602</td>
 * <td>Invalid params</td>
 * <td>Invalid method parameter(s).</td>
 * </tr>
 * <tr>
 * <td>-32603</td>
 * <td>Internal error</td>
 * <td>Internal JSON-RPC error.</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Niklas Rehfeld
 */
public class ErrorResponse
{

	/** Invalid JSON was received by the server. */
	public static final int PARSE_ERROR = -32700;
	/** The JSON sent is not a valid Request object. */
	public static final int INVALID_REQUEST = -32600;
	/** The method does not exist / is not available */
	public static final int METHOD_NOT_FOUND = -32601;
	/** Invalid method parameter(s). */
	public static final int INVALID_PARAMS = -32602;
	/** Internal JSON-RPC error */
	public static final int INTERNAL_ERROR = -32603;

	private int code;
	private String msg;
	private Object data;

	/**
	 * Get the error code.
	 *
	 * @return the error code.
	 */
	public int getCode()
	{
		return code;
	}

	/**
	 * gets the reason or meaning of the error, as defined in the table above.
	 *
	 * @return the meaning of the error code.
	 */
	public String getReason()
	{
		String reason;
		switch (code)
		{
		case PARSE_ERROR:
			reason = "Invalid JSON was received by the server.";
			break;
		case METHOD_NOT_FOUND:
			reason = "The method does not exist / is not available.";
			break;
		case INVALID_REQUEST:
			reason = "The JSON sent is not a valid Request object.";
			break;
		case INVALID_PARAMS:
			reason = "Invalid method parameter(s)";
			break;
		case INTERNAL_ERROR:
			reason = "Internal JSON-RPC error.";
			break;
		default:
			reason = "Unknown";
			break;
		}

		return reason;

	}

	/**
	 *
	 * @return The error message.
	 */
	public String getMsg()
	{
		return msg;
	}

	/**
	 *
	 * @return extra data that relates to the error.
	 */
	public Object getData()
	{
		return data;
	}

        /**
         * Formats the error as: 
         * <pre>
         * Code: "code", Message: "message", Data: "data"
         * </pre>
         * 
         * @return A String representation of the error. 
         */
	@Override
	public String toString()
	{
		return "Code: " + code + ", Message: " + msg
				+ (data != null ? ", Data: " + data : "");
	}
}
