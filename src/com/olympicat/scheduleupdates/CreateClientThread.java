package com.olympicat.scheduleupdates;

/**
 *
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
