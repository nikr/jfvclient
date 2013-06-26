/**
 *
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
