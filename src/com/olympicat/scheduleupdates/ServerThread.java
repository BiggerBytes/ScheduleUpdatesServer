 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olympicat.scheduleupdates;

import com.olympicat.scheduleupdates.serverdatarecievers.ScheduleChange;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        ObjectInputStream in;
        Integer classID;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.print(dateFormat.format(date) + ": ");
        try {
            System.out.println("Client is in!");
            
            is = new ObjectOutputStream(client.getOutputStream()); 
            in = new ObjectInputStream(client.getInputStream());          
            classID = (Integer) in.readObject();
            System.out.println("Recieved request for id " + classID);
            if (DataFactory.classesChanges.get(classID) != null) {
                ScheduleChange[] su = DataFactory.classesChanges.get(classID);
                is.writeObject(su);
                System.out.println("Send array with class that has " + su[0].getTeacherName());
                System.out.println("Object sent for ID " + classID);
            }
            else {
                is.writeObject(new ScheduleChange[0]);
                System.out.println("Sent empty object.");
            }

            is.close();
            in.close();
            client.close();
            
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
   
