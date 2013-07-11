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

    /**
     * reads test input from ListDatapathInfo.in
     * @throws IOException
     */
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