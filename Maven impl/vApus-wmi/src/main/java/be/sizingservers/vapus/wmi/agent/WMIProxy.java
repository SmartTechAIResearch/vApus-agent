/*
 * Copyright 2014 (c) Sizing Servers Lab
 * University College of West-Flanders, Department GKG
 * 
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.wmi.agent;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 *
 * @author Didjeeh
 */
public interface WMIProxy extends Library {
    WMIProxy INSTANCE = (WMIProxy) Native.loadLibrary("/WmiProxy.dll", WMIProxy.class);

    /**
     * Resolve the path for Newtonsoft.Json.dll. Should be located next to the .class / .jar.
     * @param path 
     */
    public void setResolvePath(String path);
    /**
     * As xml.
     * @return 
     */
    public String getHardwareInfo();
    /**
     * Json serialized Entities. __Total__ surrogate instance for wmi counters that have none.
     * @return 
     */
    public String getWDYH();
    
    /**
     * 
     * @param wiw 
     * @return  wiw with values.
     */
    public String refreshValues(String wiw);
}
