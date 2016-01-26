package com.biggerbytes.scheduleupdates;

/**
 * In order to preserve a good state of the machine, this thread limits the acceptance of simultaneous clients up to 50 by using a queue that is declared at the Main class. <br><br>Implements the Pub/sub design pattern 
 * @author Avishay
 */

public class CreateClientThread extends Thread {
    public void run() {
        while (true) {
            while (!Main.clientsQ.isEmpty()) {
                if (Main.threadCount < 50) {
                    Main.threadCount++;
                    Thread t = new ServerThread(Main.clientsQ.remove());
                    t.start();
                }
            }
        }
    }
}
