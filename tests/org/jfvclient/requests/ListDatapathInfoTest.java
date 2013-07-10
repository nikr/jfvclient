/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfvclient.requests;

import com.google.gson.Gson;
import java.io.IOException;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Niklas Rehfeld
 */
@Ignore("No test data yet")
public class ListDatapathInfoTest
{
    ListDatapathInfo instance;

    public ListDatapathInfoTest() throws IOException
    {
        String s = TestUtils.readTestInput(ListDatapathInfo.class);
        Gson g = TestUtils.getGson();
        instance = g.fromJson(s, ListDatapathInfo.class);
    }

    /**
     * Test of getDpid method, of class ListDatapathInfo.
     */
    @Test
    public void testGetDpid()
    {
        System.out.println("getDpid");
        String expResult = "00:00:00:ff:f1:01:01:02";
        String result = instance.getDpid().getDpid();
        assertEquals(expResult, result);
    }
}