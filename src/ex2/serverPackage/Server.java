package ex2.serverPackage;

import ex2.communPackage.Compteur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        Compteur compteur = new Compteur();

        ServerSocket serverSocket = new ServerSocket(1244);
        System.out.println("Serveur multi-thread (Objets) en attente de clients...");

        while (true) {
            Socket socket = serverSocket.accept();
            new ClientProcess(socket, compteur).start();
        }
    }
}