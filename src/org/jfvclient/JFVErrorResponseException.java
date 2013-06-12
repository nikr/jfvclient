/**
 *
 */
package org.jfvclient;

import org.jfvclient.responses.ErrorResponse;

//import java.io.Exception;

/**
 * @author Niklas Rehfeld
 *
 */
public class JFVErrorResponseException extends Exception
{

	private ErrorResponse error;

	public JFVErrorResponseException(ErrorResponse cause)
	{
		this.error = error;
	}

	public ErrorResponse getError()
	{
		return error;
	}

}
