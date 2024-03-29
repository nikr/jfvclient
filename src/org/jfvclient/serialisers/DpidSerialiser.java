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
package org.jfvclient.serialisers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.jfvclient.data.Dpid;

/**
 * A Serialiser for the DPID class. The dpid is really a string, but it needs to
 * be of the form
 * <ul>
 * <li/><code>hh:hh:hh:hh:hh:hh:hh:hh</code>,
 * <li/><code>any</code>,
 * <li/><code>all</code>, or
 * <li/><code>ALL_DPIDS</code>
 * </ul>
 * in order to be a valid DPID.
 *
 * @see org.jfvclient.deserialisers.DpidDeserialiser
 * @author Niklas Rehfeld
 */
public class DpidSerialiser implements JsonSerializer<Dpid>
{

	@Override
	public JsonElement serialize(Dpid src, Type typeOfSrc,
			JsonSerializationContext context)
	{
		if (!src.isValid())
		{
			throw new JsonParseException("invalid DPID" + src.getDpid());
		}

		return new JsonPrimitive(src.getDpid());
	}
}
