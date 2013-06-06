/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfvclient.responses;

import java.io.IOException;
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

    public DatapathInfoTest()
    {
    }

    @BeforeClass
    public static void setUp() throws IOException
    {

        String input = TestUtils.readInput(DatapathInfo.class);

    }

    /**
     * Test of getDpid method, of class DatapathInfo.
     */
    @Test
    public void testGetDpid()
    {
        System.out.println("getDpid");
        DatapathInfo instance = new DatapathInfo();
        String expResult = "";
        String result = instance.getDpid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumPorts method, of class DatapathInfo.
     */
    @Test
    public void testGetNumPorts()
    {
        System.out.println("getNumPorts");
        DatapathInfo instance = new DatapathInfo();
        int expResult = 0;
        int result = instance.getNumPorts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPortList method, of class DatapathInfo.
     */
    @Test
    public void testGetPortList()
    {
        System.out.println("getPortList");
        DatapathInfo instance = new DatapathInfo();
        List expResult = null;
        List result = instance.getPortList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPortNames method, of class DatapathInfo.
     */
    @Test
    public void testGetPortNames()
    {
        System.out.println("getPortNames");
        DatapathInfo instance = new DatapathInfo();
        List expResult = null;
        List result = instance.getPortNames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnection method, of class DatapathInfo.
     */
    @Test
    public void testGetConnection()
    {
        System.out.println("getConnection");
        DatapathInfo instance = new DatapathInfo();
        String expResult = "";
        String result = instance.getConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentFlowmodUsage method, of class DatapathInfo.
     */
    @Test
    public void testGetCurrentFlowmodUsage()
    {
        System.out.println("getCurrentFlowmodUsage");
        DatapathInfo instance = new DatapathInfo();
        Map expResult = null;
        Map result = instance.getCurrentFlowmodUsage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}