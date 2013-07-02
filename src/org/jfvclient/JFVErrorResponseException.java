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
