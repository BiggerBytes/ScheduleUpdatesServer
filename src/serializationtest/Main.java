/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializationtest;

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
    
    public static void main(String[] args) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        final int PORT = 25560;
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(PORT);
            DataFactory.loadData();//Loading data --- TODO load again everyhour
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
}
