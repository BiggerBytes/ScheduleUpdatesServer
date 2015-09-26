/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializationtest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Avishay
 */
public class ServerThread extends Thread {
    static int num = 0;
    Socket client;
    
    public ServerThread(Socket client) {
        this.client = client;
    }
    
    public void run() {
        ObjectOutputStream is;
        BufferedReader in;
        Integer classID;
        try {
            System.out.println("Client is in!");
            
            is = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));           
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            classID = in.read();
            
            is.writeObject(DataFactory.classesChanges.get(classID));
            System.out.println("Object sent");

            is.close();
            in.close();
            client.close();
            
            } catch (Exception e) {
                
            }
        }
    }
   
