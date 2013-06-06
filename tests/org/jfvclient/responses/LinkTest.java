/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfvclient.responses;

import com.google.gson.Gson;
import java.io.IOException;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niklas Rehfeld
 */
public class LinkTest
{
    
    private Link instance;
    public LinkTest() 
    {
        Gson g = TestUtils.getGson();
        
        try
        {
        instance =  g.fromJson(TestUtils.readTestInput(Link.class), Link.class);
        } catch (IOException ex)
        {
            fail("Couldn't load test file.");
        }
    }

    /**
     * Test of getSrc_dpid method, of class Link.
     */
    @Test
    public void testGetSrc_dpid()
    {
        System.out.println("getSrc_dpid");
        String expResult = "11:22:33:44:55:66:77:88";
        String result = instance.getSrc_dpid();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSrc_port method, of class Link.
     */
    @Test
    public void testGetSrc_port()
    {
        System.out.println("getSrc_port");
        int expResult = 2;
        int result = instance.getSrc_port();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDst_dpid method, of class Link.
     */
    @Test
    public void testGetDst_dpid()
    {
        System.out.println("getDst_dpid");
        String expResult = "22:33:44:55:66:77:88:99";
        String result = instance.getDst_dpid();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDst_port method, of class Link.
     */
    @Test
    public void testGetDst_port()
    {
        System.out.println("getDst_port");
        int expResult = 1;
        int result = instance.getDst_port();
        assertEquals(expResult, result);
    }

}