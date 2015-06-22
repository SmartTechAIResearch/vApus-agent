/*
 * Copyright 2015 (c) Sizing Servers Lab
 * University College of West-Flanders, Department GKG * 
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.agent.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.SocketException;

/**
 * This is a helper class to get the down and up speed.
 * @author Didjeeh
 */
public class BandwidthTest {

    /**
     * The receiving waits for a STOP byte (1) at the first byte in a separate package.
     * Make sure you've set the buffer sizes at both ends to the same value.
     */
    public static final int STOP = 1;
    /**
     * 10 MB (empty bytes) will be sent or received (use TCP to avoid dropped packages).
     */
    public static final int MESSAGESIZE = 10 * 1024 * 1024;
    /**
     * The message will be sent a 100 times. The receiving waits for a STOP byte (1) at the first byte in a separate package.
     */
    public static final int LOOPS = 100;

    /**
     * 
     * @param socket
     * @return The upload speed in Mbits per sec.
     * @throws IOException 
     */
    public static double GetUploadSpeed(Socket socket) throws IOException {
        System.out.print("Upload speed... ");

        byte[] b = CreateMessage(MESSAGESIZE);

        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);

        long startTime = System.nanoTime();
        for (int i = 0; i != LOOPS; i++) {
            dos.write(b);
        }

        double mbitsPerSec = GetMBitsPerSec(System.nanoTime() - startTime);

        System.out.println(new BigDecimal(mbitsPerSec).setScale(2, BigDecimal.ROUND_HALF_UP) + " Mbps");

        b = new byte[1];
        b[0] = STOP;
        dos.write(b);

        return mbitsPerSec;
    }

    /**
     * 
     * @param socket
     * @return The download speed in MBits per sec.
     * @throws SocketException
     * @throws IOException 
     */
    public static double GetDownloadSpeed(Socket socket) throws SocketException, IOException {
        System.out.print("Download speed... ");

        byte[] b = new byte[socket.getReceiveBufferSize()];

        InputStream in = socket.getInputStream();
        DataInputStream dis = new DataInputStream(in);

        long startTime = System.nanoTime();
        do {
            dis.read(b);
        } while (b[0] != STOP);

        double mbitsPerSec = GetMBitsPerSec(System.nanoTime() - startTime);

        System.out.println(new BigDecimal(mbitsPerSec).setScale(2, BigDecimal.ROUND_HALF_UP) + " Mbps");

        return mbitsPerSec;
    }

    private static byte[] CreateMessage(int size) {
        byte[] b = new byte[size];
        for (int i = 0; i != b.length; i++) {
            b[i] = 0;
        }
        return b;
    }

    private static double GetMBitsPerSec(long elapsedNanos) {
        return ((double) ((long) MESSAGESIZE * LOOPS * 8) / (1000000)) / (elapsedNanos / 1000000000);
    }
}
