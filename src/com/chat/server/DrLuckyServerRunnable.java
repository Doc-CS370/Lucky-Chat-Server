package com.chat.server;

public class DrLuckyServerRunnable implements Runnable {
    public DrLuckyServerRunnable(){

    }
    @Override
    public void run() {
        try {
            DrLuckyServer.start();
        } catch (Exception e) {
            System.out.println("Failed to start server!");
        }
    }
}
