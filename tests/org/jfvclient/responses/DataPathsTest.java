/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    public DataPathsTest() throws IOException
    {
        String input = TestUtils.readTestInput(DataPaths.class);
        Gson g = TestUtils.getGson();
        instance = g.fromJson(input, DataPaths.class);
    }

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