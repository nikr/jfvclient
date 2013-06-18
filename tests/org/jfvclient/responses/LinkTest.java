/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfvclient.responses;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfvclient.data.Dpid;
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
        String input;
        try
        {
            input = TestUtils.readTestInput(Link.class);
            System.out.println("input: " + input);
            instance =  g.fromJson(input, Link.class);
        } catch (IOException ex)
        {
            Logger.getLogger(LinkTest.class.getName()).log(Level.SEVERE, null,
                    ex);
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
        Dpid d = new Dpid(expResult);
        System.out.println(instance);
        Dpid result = instance.getSrc_dpid();
        assertEquals(d, result);
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
        Dpid expResult = new Dpid("22:33:44:55:66:77:88:99");
        Dpid result = instance.getDst_dpid();
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