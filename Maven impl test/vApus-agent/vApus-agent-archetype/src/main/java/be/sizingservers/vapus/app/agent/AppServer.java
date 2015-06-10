/*
 * Copyright 2014 (c) Sizing Servers Lab
 * University College of West-Flanders, Department GKG
 * 
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.app.agent;

import be.sizingservers.vapus.agent.Monitor;
import be.sizingservers.vapus.agent.Server;
import java.net.Socket;

/**
 *
 * @author Didjeeh
 */
public class AppServer extends Server {

    @Override
    protected Monitor getNewMonitor(Server server, Socket socket, long id) {
        return new AppMonitor(server, socket, id);
    }
    
}
