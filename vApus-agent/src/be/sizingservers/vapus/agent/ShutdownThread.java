/*
 * 2014 Sizing Servers Lab, affiliated with IT bachelor degree NMCT
 * University College of West-Flanders, Department GKG (www.sizingservers.be, www.nmct.be, www.howest.be/en) 
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.agent;

/**
 * To be used in the Agent constructor like so: Runtime.getRuntime().addShutdownHook(new ShutdownThread(server));
 *    
*
 */
public class ShutdownThread extends Thread {

    private final Server server;

    /**
     * To be used in the Agent constructor like so: Runtime.getRuntime().addShutdownHook(new ShutdownThread(server));
     * 
     * @param server 
     */
    public ShutdownThread(Server server) {
        this.server = server;
    }

    /**
     * 
     */
    @Override
    public void run() {
        this.server.stop();
    }
}
