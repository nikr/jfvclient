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
