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
public class SliceTest
{
    Slice instance;
    
    public SliceTest() throws IOException
    {
        String s = TestUtils.readTestInput(Slice.class);
        Gson g = TestUtils.getGson();
        instance = g.fromJson(s, Slice.class);
    }

    /**
     * Test of getSlice_name method, of class Slice.
     */
    @Test
    public void testGetSlice_name()
    {
        System.out.println("getSlice_name");
        String expResult = "Slice1";
        String result = instance.getSlice_name();
        assertEquals(expResult, result);
    }

    /**
     * Test of isAdmin_status method, of class Slice.
     */
    @Test
    public void testIsAdmin_status()
    {
        System.out.println("isAdmin_status");
        boolean expResult = true;
        boolean result = instance.isAdmin_status();
        assertEquals(expResult, result);
    }

}