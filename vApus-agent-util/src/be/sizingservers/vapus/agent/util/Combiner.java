/*
 * 2014 Sizing Servers Lab, affiliated with IT bachelor degree NMCT
 * University College of West-Flanders, Department GKG (www.sizingservers.be, www.nmct.be, www.howest.be/en) 
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.agent.util;

import java.util.ArrayList;

/**
 *
*
 */
public class Combiner {

    /**
     * 
     * @param arr
     * @param separator
     * @return 
     */
    public static String combine(ArrayList<String> arr, String separator) {
        StringBuilder sb = new StringBuilder();

        if (!arr.isEmpty()) {
            for (int i = 0; i != arr.size() - 1; i++) {
                sb.append(arr.get(i));
                sb.append(separator);
            }
            sb.append(arr.get(arr.size() - 1));
        }
        return sb.toString();
    }
}
