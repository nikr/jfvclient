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

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfvclient.data.Dpid;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This fails at the moment.
 *
 * @author Niklas Rehfeld
 */
public class DataPathsTest
{

    private DataPaths instance;

    /**
     * Reads input from DataPaths.in
     * @throws IOException
     */
    public DataPathsTest() throws IOException
    {
        String input = TestUtils.readTestInput(DataPaths.class);
        Gson g = TestUtils.getGson();
        instance = g.fromJson(input, DataPaths.class);
    }

    /**
     * tests the getDPIDs method.
     */
    @Test
    public void testGetDPIDs()
    {

        List<Dpid> expected  = new ArrayList<Dpid>();

        expected.add(new Dpid("00:11:22:33:44:55:66:77"));
        expected.add(new Dpid("11:22:33:44:55:66:77:88"));
        expected.add(new Dpid("any"));
        expected.add(new Dpid("all"));
        expected.add(new Dpid("ALL_DPIDS"));

        assertEquals(expected, instance.getDpids());
    }
}