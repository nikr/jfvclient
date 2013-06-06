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

package org.jfvclient.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

/**
 *
 * @author Niklas Rehfeld
 */
public class TestUtils {

    static File testDataFolder = new File("test-data");
    
    public static String readInput(Class testclass) throws IOException
    {
        if (!testDataFolderValid())
            throw new IOException("Test data folder " + testDataFolder.getAbsolutePath() + "is not a readable directory.");
        File f = new File(testDataFolder, testclass.getSimpleName() + ".in");
        BufferedReader r = new BufferedReader(new FileReader(f));
        String out = "";
        while (r.ready())
        {
            out += r.readLine();
        }
        return out;
    }
    
    private static boolean testDataFolderValid()
    {
        return (testDataFolder.isDirectory() && testDataFolder.canRead());
    }
    
}