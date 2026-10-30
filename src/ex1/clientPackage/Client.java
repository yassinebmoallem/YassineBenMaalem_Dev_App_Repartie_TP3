package ex1.clientPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.1.2", 1234);
        System.out.println("Client connecté au serveur.");
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        Scanner clavier = new Scanner(System.in);
        System.out.print("Entrez une opération : ");
        String operation = clavier.nextLine().trim();
        if (!operation.matches("\\d+\\s*[+*/-]\\s*\\d+")) {
            System.out.println("Erreur : opération invalide. Exemple : 55 * 25");
        } else {
            output.println(operation);
            String resultat = input.readLine();
            System.out.println("Résultat reçu : " + resultat);
        }
        socket.close();
    }
}