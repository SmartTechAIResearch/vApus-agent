/*
 * Copyright 2014 (c) Sizing Servers Lab
 * University College of West-Flanders, Department GKG
 *  
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.wmi.agent;

import be.sizingservers.vapus.agent.Agent;
import be.sizingservers.vapus.agent.Server;
import be.sizingservers.vapus.agent.util.Entities;
import com.google.gson.Gson;
import java.net.Socket;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 *
 * @author Didjeeh
 */
public class PollWMIAndSend extends TimerTask {

    private final Server server;
    private final Socket socket;
    private final String wiw;

    public PollWMIAndSend(Entities wiwEntities, Server server, Socket socket) {
        this.wiw = new Gson().toJson(wiwEntities);
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            this.server.send(WMIProxy.INSTANCE.refreshValues(this.wiw), this.socket);
        } catch (Exception ex) {
            Agent.getLogger().log(Level.SEVERE, "Failed sending counters (stopping the timer task now): {0}", ex);
            super.cancel();
        }
    }
}
