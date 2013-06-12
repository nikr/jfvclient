package org.jfvclient;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * RequestResponseTests are Tests which need to send a request to an actual
 * FlowVisor instance, and then receive a response. This means that they require
 * a specific setup.
 *
 * The setup is as follows:
 * <ul>
 * <li>The FlowVisor connection info is set in
 * <code>resources/visor.properties</code></li>
 * <li>The FlowVisor controls a Mininet network.</li>
 * <li> The Flowvisor topology is 'tree,2,2'. </li>
 * </ul>
 *
 * the tree topology looks as follows (switch port numbers in brackets)
 * <pre>
 *          -------
 *         |  S5   |
 *          -------
 *       (2)/    \(1)
 *      (3)/      \(3)
 *     -------    -------
 *    |  S6   |  |  S7   |
 *     -------    -------
 *   (1)/  \(2) (1)/   \(2)
 *    H1   H2     H3   H4
 *
 * @author Niklas Rehfeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{ ListDatapathInfoRRTest.class, ListDatapathRRTest.class,
			ListLinksRRTest.class, })
public class RequestResponseTests
{

}
