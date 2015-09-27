/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleupdates;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Avishay
 */
public class Main {
    private static List<ScheduleChange> changeArr = null;
    private static final Long REFRESH_DELAY = (6l*60l*60l*1000l); //    =   6 hours in milliseconds

    public static void main(String[] args) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        final int PORT = 25560;
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(PORT);
            initHourlyDataRefresh();//Loading data --- TODO load again everyhour
        } catch (Exception e) {
            System.err.println("Couldn't litsen on port " + PORT);
            System.exit(-1);
        }
        
        while (true) {
            System.out.println("Waiting for a client.");
            Socket clientSocket = serverSocket.accept();
            Thread t = new ServerThread(clientSocket);
            t.start();
        }
    }

    private static Boolean toContinueRefresh = true;
    public static void initHourlyDataRefresh(){
        Runnable runnable = () -> {
            try {
                while (toContinueRefresh) {
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
