/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfvclient.responses;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfvclient.testing.TestUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Niklas Rehfeld
 */
public class DatapathInfoTest
{

    DatapathInfo instance;

    public DatapathInfoTest() throws IOException
    {
        String input = TestUtils.readTestInput(DatapathInfo.class);
        Gson g = new Gson();
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
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}