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
package org.jfvclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

/**
 * An authenticator which gets the username and password from a properties file.
 * This is used to log into the FlowVisor instance.
 *
 */
public class PropertiesFileAuthenticator extends Authenticator
{

    private File propsFile;
    private Properties config;
    
    private String uname;
    private String pw;

    public PropertiesFileAuthenticator(File f) throws IOException
    {
        propsFile = f;
        config = new Properties(); 
        reload();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(uname, pw.toCharArray());
    }
    
    public final void reload() throws IOException
    {
        if (!propsFile.canRead())
        {
            throw new IOException("Can't open " + propsFile.getName() + " for reading.");
        }
        
        config.load(new FileInputStream(propsFile));
        uname = config.getProperty("username");
        pw = config.getProperty("password");
        if (uname == null || pw == null)
        {
            throw new IOException("Properties file doesn't contain 'username' and 'password' keys.");
        }        
    }
}
