package ex1.serverPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientProcess extends Thread {
    private Socket socket;
    private static int clientCount = 0;
    private int myClientId;

    public ClientProcess(Socket socket) {
        this.socket = socket;
        clientCount++;
        this.myClientId = clientCount;
    }

    public void run() {
        try {
            String clientIP = socket.getRemoteSocketAddress().toString();
            System.out.println("Client n°" + myClientId + " connecté depuis l'IP : " + clientIP);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            output.println("Bienvenue ! Vous êtes le client n°" + myClientId);

            String operation = input.readLine();
            if (operation != null) {
                System.out.println("Opération reçue du client n°" + myClientId + " : " + operation);
                String resultat = calculer(operation);
                output.println(resultat);
                System.out.println("Résultat envoyé au client n°" + myClientId + " : " + resultat);
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Erreur de connexion avec le client n°" + myClientId);
        }
    }

    private String calculer(String operation) {
        Pattern pattern = Pattern.compile("^(\\d+)\\s*([+*/-])\\s*(\\d+)$");
        Matcher matcher = pattern.matcher(operation);
        if (!matcher.matches()) {
            return "Erreur : opération invalide.";
        }
        int a = Integer.parseInt(matcher.group(1));
        String operateur = matcher.group(2);
        int b = Integer.parseInt(matcher.group(3));
        int resultat = 0;
        switch (operateur) {
            case "+": resultat = a + b; break;
            case "-": resultat = a - b; break;
            case "*": resultat = a * b; break;
            case "/":
                if (b == 0) return "Erreur : division par zéro.";
                resultat = a / b;
                break;
        }
        return String.valueOf(resultat);
    }
}