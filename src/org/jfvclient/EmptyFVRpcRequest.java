/**
 *
 */
package org.jfvclient;

import com.google.gson.JsonNull;

/**
 * This class is used to create FVRpcRequests that do not take any params. These
 * are:
 * <ul>
 * <li />list-version
 * <li />list-links
 * <li />list-slices
 * <li />list-datapaths
 * <li />list-fv-health
 * <li />save-config
 * </ul>
 *
 * @author Niklas Rehfeld
 *
 */
public class EmptyFVRpcRequest extends FVRpcRequest<JsonNull>
{

	/**
	 * Requests that require no parameters. Rather than implementing all of
	 * these as empty classes, it is easier to just enumerate them and have them
	 * as a special class of requests.
	 */
	public static enum NoParamType {

		/**
		 * list-slices request
		 */
		list_slices,
		/**
		 * list-version request
		 */
		list_version,
		/**
		 * list-datapaths request
		 */
		list_datapaths,
		/**
		 * list-links request
		 */
		list_links,
		/**
		 * list-fv-health request
		 */
		list_fv_health,
		/**
		 * save-config request
		 */
		save_config,
	}

	/**
	 * Creates a new EmptyFVRpcRequest with a generated request ID.
	 *
	 * @param t the method of the request.
	 */
	public EmptyFVRpcRequest(NoParamType t)
	{
		this(t, t.name() + Math.round(Math.random() * 1000));
	}

	/**
	 * Create a new EmptyFVRpcRequest with the given type and id.
	 *
	 * @see NoParamType
	 * @param t
	 *            the method
	 * @param id
	 *            a request ID. Must not be null.
	 */
	public EmptyFVRpcRequest(NoParamType t, String id)
	{
		super();
		switch (t)
		{
		case list_slices:
			setMethod("list-slices");
			break;
		case list_version:
			setMethod("list-version");
			break;
		case list_datapaths:
			setMethod("list-datapaths");
			break;
		case list_links:
			setMethod("list-links");
			break;
		case list_fv_health:
			setMethod("list-fv-health");
			break;
		case save_config:
			setMethod("save-config");
			break;
		default:
			throw new AssertionError(t.name());
		}
		setId(id);
		setParams(JsonNull.INSTANCE);
	}

}
