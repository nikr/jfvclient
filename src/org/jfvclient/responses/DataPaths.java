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

import java.util.ArrayList;
import java.util.List;
import org.jfvclient.data.Dpid;

/**
 *This is simply a wrapper for a list of Dpids. It is not the most elegant solution, but it seems to work.
 *
 * @author Niklas Rehfeld
 */
public class DataPaths extends ArrayList<Dpid>{
//private List<Dpid> dpids;
//
  /**
   *
   * @return a list of DPIDs.
   */
	public List<Dpid> getDpids()
    {
        return this;
    }
}
