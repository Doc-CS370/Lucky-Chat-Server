package com.chat.client;


public class DrLuckyClientRunnable implements Runnable {

    public DrLuckyClientRunnable(){

    }

    @Override
    public void run() {
        try {
            DrLuckyClient.start();
        } catch (Exception e) {
            System.out.println("Failed to start the client!");
        }
    }
}