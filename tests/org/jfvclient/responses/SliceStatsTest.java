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

import static org.junit.Assert.*;

import java.io.IOException;

import org.jfvclient.testing.TestUtils;
import org.junit.Test;

import com.google.gson.Gson;

/**
 * tests for the slice statistics.
 *
 * @author Niklas Rehfeld
 *
 */
public class SliceStatsTest
{

	/**
	 * test reading in valid slice-stats responses. reads input from
	 * SLiceStats.in
	 *
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException
	{
		String in = TestUtils.readTestInput(SliceStats.class);
		Gson g = TestUtils.getGson();
		SliceStats s = g.fromJson(in, SliceStats.class);
		assertNotNull(s.getRx());
		assertNotNull(s.getTx());
		assertNotNull(s.getDrop());

	}

}
