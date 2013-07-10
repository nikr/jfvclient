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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niklas Rehfeld
 */
public class DatapathInfoTest
{

    private DatapathInfo instance;

    /**
     * reads in DataPathInfo.in
     * @throws IOException
     */
    public DatapathInfoTest() throws IOException
    {
        String input = TestUtils.readTestInput(DatapathInfo.class);
//        Gson g = new Gson();

        Gson g = TestUtils.getGson();
        instance = g.fromJson(input, DatapathInfo.class);
    }

    /**
     * Test of getDpid method, of class DatapathInfo.
     */
    @Test
    public void testGetDpid()
    {
        System.out.println("getDpid");
        String expResult = "00:00:00:ff:f1:01:01:02";
        String result = instance.getDpid();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumPorts method, of class DatapathInfo.
     */
    @Test
    public void testGetNumPorts()
    {
        System.out.println("getNumPorts");
        int expResult = 2;
        int result = instance.getNumPorts();
        assertEquals(expResult, result);

    }

    /**
     * Test of getPortList method, of class DatapathInfo.
     */
    @Test
    public void testGetPortList()
    {
        System.out.println("getPortList");

        List<Integer> expResult = Arrays.asList(new Integer[] {2, 32});
        List<Integer> result = instance.getPortList();


        assertEquals(expResult, result);
//        assertArrayEquals(expResult, result.toArray());
    }

    /**
     * Test of getPortNames method, of class DatapathInfo.
     */
    @Test
    public void testGetPortNames()
    {
        System.out.println("getPortNames");

        List<String> expResult = Arrays.asList(new String[] {"s1-eth0", "s1-eth1"});
        List<String> result = instance.getPortNames();
//        assertArrayEquals(expResult, result.toArray());
        assertEquals(expResult, result);
    }

    /**
     * Test of getConnection method, of class DatapathInfo.
     */
    @Test
    public void testGetConnection()
    {
        System.out.println("getConnection");
        String expResult = "/192.168.1.1:8765-->/192.168.1.32:9876";
        String result = instance.getConnection();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCurrentFlowmodUsage method, of class DatapathInfo.
     */
    @Test
    public void testGetCurrentFlowmodUsage()
    {
        System.out.println("getCurrentFlowmodUsage");

        Map<String, Integer> expResult = new HashMap<String, Integer>();
        expResult.put("Slice1", 23);
        expResult.put("fvadmin", 2);
        Map<String, Integer> result = instance.getCurrentFlowmodUsage();
        assertEquals(expResult, result);
    }
}