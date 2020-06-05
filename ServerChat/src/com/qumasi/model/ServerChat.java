package com.qumasi.model;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat {
    private static final int PORT = 7777;

    public static void main(String[] args) {
        ServerChat serverApp = new ServerChat();
        serverApp.ServerSocketExample();
    }

    private void ServerSocketExample() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server up and ready for connection....");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Server accept the connection ....");
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
