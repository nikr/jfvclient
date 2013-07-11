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
package org.jfvclient.responses;

import com.google.gson.Gson;
import java.io.IOException;
import org.jfvclient.testing.TestUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * tests reading in valid Slice responses.
 *
 * @author Niklas Rehfeld
 */
public class SliceTest
{
    Slice instance;

    /**
     * reads input from Slice.in
     * @throws IOException
     */
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