package ex1.serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1244);
        System.out.println("Serveur multi-thread en attente de clients...");

        while (true) {
            Socket socket = serverSocket.accept();
            ClientProcess clientThread = new ClientProcess(socket);
            clientThread.start();
        }
    }
}