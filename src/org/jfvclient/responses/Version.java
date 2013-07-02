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

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 * {
 *   "flowvisor-version" : &lt;version&gt;,
 *   "db-version" : &lt;version&gt;
 * }
 * </pre>
 *
 *
 * @author Niklas Rehfeld
 *
 */
public class Version
{
@SerializedName("flowvisor-version") private String fvVersion;
@SerializedName("db-version") private String dbVersion;

/**
 * @return the fvVersion
 */
public String getFvVersion()
{
	return fvVersion;
}
/**
 * @return the dbVersion
 */
public String getDbVersion()
{
	return dbVersion;
}
/**
 * @param fvVersion the fvVersion to set
 */
public void setFvVersion(String fvVersion)
{
	this.fvVersion = fvVersion;
}
/**
 * @param dbVersion the dbVersion to set
 */
public void setDbVersion(String dbVersion)
{
	this.dbVersion = dbVersion;
}


}
