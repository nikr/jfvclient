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

package org.jfvclient.testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.jfvclient.data.Dpid;
import org.jfvclient.deserialisers.DpidDeserialiser;
import org.jfvclient.serialisers.DpidSerialiser;

/**
 * Useful methods for reading in test data and suchlike.
 *
 *
 * @author Niklas Rehfeld
 */
public class TestUtils
{

	static File testDataFolder = new File("test-data");

	/**
	 * Reads the test input corresponding to the class under test.
	 *
	 * For instance if the class being tested is Dpid, then it will read in a
	 * file called Dpid.in.
	 *
	 * @param testclass
	 *            the class to be tested
	 * @return a String containing the contents of the test input.
	 * @throws IOException
	 */
	public static String readTestInput(Class<?> testclass) throws IOException
	{
		if (!testDataFolderValid())
			throw new IOException("Test data folder "
					+ testDataFolder.getAbsolutePath()
					+ "is not a readable directory.");
		File f = new File(testDataFolder, testclass.getSimpleName() + ".in");
		BufferedReader r = new BufferedReader(new FileReader(f));
		String out = "";
		while (r.ready())
		{
			out += r.readLine();
		}
		r.close();
		return out;
	}

	private static boolean testDataFolderValid()
	{
		return (testDataFolder.isDirectory() && testDataFolder.canRead());
	}

	/**
	 *
	 * @return a Gson instance set up with the correct serialisers and
	 *         deserialisers.
	 */
	public static Gson getGson()
	{
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Dpid.class, new DpidDeserialiser());
		gb.registerTypeAdapter(Dpid.class, new DpidSerialiser());
		return gb.create();
	}
}
