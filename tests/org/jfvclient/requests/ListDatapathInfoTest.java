/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfvclient.requests;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niklas Rehfeld
 */
public class ListDatapathInfoTest
{
    
    public ListDatapathInfoTest()
    {
    }

    /**
     * Test of getDpid method, of class ListDatapathInfo.
     */
    @Test
    public void testGetDpid()
    {
        System.out.println("getDpid");
        ListDatapathInfo instance = new ListDatapathInfo();
        String expResult = "";
        String result = instance.getDpid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}