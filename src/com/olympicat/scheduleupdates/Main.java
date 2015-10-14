/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olympicat.scheduleupdates;

import com.olympicat.scheduleupdates.serverdatarecievers.ScheduleChange;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Avishay
 */
public class Main {
    private static List<ScheduleChange> changeArr = null;
    private static final Long REFRESH_DELAY = (1l*60l*60l*1000l); //    =   1h in milliseconds

    public static void main(String[] args) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        final int PORT = 25565;
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(PORT);
            initHourlyDataRefresh();
        } catch (Exception e) {
            System.err.println("Couldn't listen on port " + PORT);
            System.exit(-1);
        }
        
        while (true) {
            System.out.println("Waiting for a client.");
            Socket clientSocket = serverSocket.accept();
            Thread t = new ServerThread(clientSocket);
            t.start();
        }
    }

    public static void initHourlyDataRefresh(){
        Runnable runnable = () -> {
            try {
                while (true) { // Thread running in the background all the time
                    DataFactory.loadData();
                    synchronized (Thread.currentThread()){
                        Thread.currentThread().sleep(REFRESH_DELAY);
                    }
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable, "DataRefreshThread");
        thread.start();
    }
}
