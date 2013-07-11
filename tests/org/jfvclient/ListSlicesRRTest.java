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

import static org.junit.Assert.*;

import java.io.IOException;

import org.jfvclient.responses.Slice;
import org.jfvclient.responses.SliceList;
import org.junit.Test;

/**
 * Checks if the default fvadmin slice is present.
 *
 * @author Niklas Rehfeld
 *
 */
public class ListSlicesRRTest
{

	/**
	 * test if the 'fvadmin' slice is in the list returned by list-slices.
	 * @throws IOException
	 * @throws JFVErrorResponseException
	 */
	@Test
	public void test() throws IOException, JFVErrorResponseException
	{
		JFVClient c = new JFVClient();
		SliceList sl = c.listSlices();
		assertTrue(sl.contains(new Slice("fvadmin")));
	}

}
