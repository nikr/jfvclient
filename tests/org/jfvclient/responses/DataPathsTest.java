/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfvclient.responses;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public DataPathsTest() throws IOException
    {
        fail("I know it doesn't work. It won't read the data.");
        String input = TestUtils.readTestInput(DataPaths.class);
        Gson g = TestUtils.getGson();
        instance = g.fromJson(input, DataPaths.class);
    }

    @Test
    public void testGetDPIDs()
    {
        fail("I know it doesn't work.");
        
        List<String> expected  = new ArrayList<String>();
        expected.add("00:11:22:33:44:55:66:77");
        expected.add("11:22:33:44:55:66:77:88");
        expected.add("any");
        expected.add("all");
        expected.add("ALL_DPIDS");
        
        assertEquals(expected, instance.getDpids());
    }
}