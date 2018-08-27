/*
 * 2014 Sizing Servers Lab, affiliated with IT bachelor degree NMCT
 * University College of West-Flanders, Department GKG (www.sizingservers.be, www.nmct.be, www.howest.be/en) 
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.agent.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
*
 */
public class HostName {

    public static String get() {
        try {
            return InetAddress.getLocalHost().getHostName();      
        } catch (UnknownHostException ex) {
            //Ignore.
        }
        return null;
    }
}
