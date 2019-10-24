package com.chat.tests;

import com.chat.client.DrLuckyClient;
import com.chat.client.DrLuckyClientHandler;
import com.chat.client.DrLuckyClientRunnable;
import com.chat.server.DrLuckyServer;
import com.chat.server.DrLuckyServerHandler;
import com.chat.server.DrLuckyServerRunnable;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

public class MessageTest {

    @Test
    public void testSalutationMessage() throws Exception {
        DrLuckyServerRunnable runnableServer = new DrLuckyServerRunnable();
        Thread chatServer = new Thread(runnableServer);
        chatServer.start();
        Thread.sleep(10000);
        DrLuckyClientRunnable runnableClient = new DrLuckyClientRunnable();
        Thread chatClient = new Thread(runnableClient);
        chatClient.start();
        Thread.sleep(5000);
        String testMessage = "hello";
        DrLuckyClient.lastMessage = testMessage;
        Thread.sleep(2000);
        String testAgainst = "";
        while(true){
            if(DrLuckyServerHandler.lastMessage != "" && DrLuckyServerHandler.lastMessage != null){
                testAgainst = DrLuckyServerHandler.lastMessage;
                assertEquals(testMessage, testAgainst);
                System.out.println("Client: " + testMessage + " Server: " + testAgainst );
                break;
            }
        }
    }
}
