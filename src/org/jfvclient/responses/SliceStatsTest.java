package org.jfvclient.responses;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;

import org.jfvclient.testing.TestUtils;
import org.junit.Test;

import com.google.gson.Gson;

public class SliceStatsTest
{

	@Test
	public void test() throws IOException
	{
		String in = TestUtils.readTestInput(SliceStats.class);
		Gson g = TestUtils.getGson();
		SliceStats s = g.fromJson(in,SliceStats.class);
		assertNotNull(s.getRx());
		assertNotNull(s.getTx());
		assertNotNull(s.getDrop());


	}

}
