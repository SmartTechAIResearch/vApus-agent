/*
 * Copyright 2014 (c) Sizing Servers Lab
 * University College of West-Flanders, Department GKG
 * 
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.app.agent;

import be.sizingservers.vapus.agent.Agent;
import be.sizingservers.vapus.agent.Monitor;
import be.sizingservers.vapus.agent.Server;
import be.sizingservers.vapus.agent.util.Entities;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.net.Socket;
import java.util.logging.Level;

/**
 *
 * @author Didjeeh
 */
public class AppMonitor extends Monitor {

    //Commented code is example code from the vApus-wmi agent to help you. I suggest that you look at the existing agent.
    
    //private PollWMIAndSend pollWmiAndSend;

    /**
     * A empty instance only to be used to call getConfig() and getWDYH(). You
     * must do the needed init stuff here.
     */
    public AppMonitor() {
    }

    /**
     *
     * @param server
     * @param socket
     * @param id
     */
    public AppMonitor(Server server, Socket socket, long id) {
        super(server, socket, id);
    }

    @Override
    public void setConfig() {
        if (Monitor.config == null) {
            try {
//                Monitor.config = WMIProxy.INSTANCE.getHardwareInfo();
            } catch (Exception ex) {
                Agent.getLogger().log(Level.SEVERE, "Failed setting config: {0}", ex);
            }
        }
    }

    @Override
    public void setWDYH() {
        if (Monitor.wdyh == null) {
            try {
//                WMIProxy.INSTANCE.setResolvePath(Directory.getExecutingDirectory(AppMonitor.class));
//
//                Monitor.wdyh = WMIProxy.INSTANCE.getWDYH();
                Monitor.wdyhEntities = new Gson().fromJson(Monitor.wdyh, Entities.class);
//            } catch (URISyntaxException ex) {
//                Agent.getLogger().log(Level.SEVERE, "Could not set WDYH: {0}", ex);
            } catch (JsonSyntaxException ex) {
                Agent.getLogger().log(Level.SEVERE, "Could not set WDYH: {0}", ex);
            }
        }
    }

    @Override
    public void start() {
        try {
            if (super.running) {
                return;
            }
            super.running = true;

//            super.poller = new Timer();
//            this.pollWmiAndSend = new PollWMIAndSend(super.getWIWEntities(), super.server, super.socket);
//
//            int interval = Properties.getSendCountersInterval();
//            this.poller.scheduleAtFixedRate(this.pollWmiAndSend, 0, interval);
        } catch (Exception ex) {
            stop();
            Agent.getLogger().log(Level.SEVERE, "Failed at starting the wmi monitor: {0}", ex);
        }
    }

    @Override
    public void stop() {
        if (!super.running) {
            return;
        }
        super.running = false;

        super.poller.cancel();
        super.poller.purge();
        super.poller = null;

//        this.pollWmiAndSend.cancel();
//        this.pollWmiAndSend = null;
    }
}
