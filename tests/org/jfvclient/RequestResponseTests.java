package org.jfvclient;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * RequestResponseTests are Tests which need to send a request to an actual FlowVisor instance,
 * and then receive a response. This means that they require a specific setup.
 *
 * The setup is as follows:
 * <ul>
 * <li> The FlowVisor connection info is set in <code>resources/visor.properties</code> </li>
 * <li> The FlowVisor controls a Mininet network, using the default topology, with one switch (DPID=00:...:00:01) and two hosts. </li>
 *</ul>
 * @author Niklas Rehfeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{ ListDatapathInfoRRTest.class, ListDatapathRRTest.class })
public class RequestResponseTests
{

}
