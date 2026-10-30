package ex2.serverPackage;

import ex2.communPackage.Compteur;
import ex2.communPackage.Operation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientProcess extends Thread {
    private Socket socket;
    private Compteur compteur;
    private static int clientCount = 0;
    private int myClientId;

    public ClientProcess(Socket socket, Compteur compteur) {
        this.socket = socket;
        this.compteur = compteur;
        clientCount++;
        this.myClientId = clientCount;
    }

    public void run() {
        try {
            System.out.println("Client n°" + myClientId + " connecté depuis l'IP : "
                    + socket.getRemoteSocketAddress());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeObject("Bienvenue ! Vous êtes le client n°" + myClientId);

            Object recu = ois.readObject();
            while (recu instanceof Operation) {
                Operation op = (Operation) recu;
                String resultat = calculer(op);
                oos.writeObject(resultat);

                int total = compteur.incrementer();
                System.out.println("Client n°" + myClientId + " | Opération : " + op
                        + " | Résultat : " + resultat
                        + " | Nombre total d'opérations traitées : " + total);

                recu = ois.readObject();
            }

            System.out.println("Le client n°" + myClientId + " s'est déconnecté.");
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur de connexion avec le client n°" + myClientId);
        }
    }

    private String calculer(Operation op) {
        switch (op.getOperateur()) {
            case "+": return String.valueOf(op.getOperande1() + op.getOperande2());
            case "-": return String.valueOf(op.getOperande1() - op.getOperande2());
            case "*": return String.valueOf(op.getOperande1() * op.getOperande2());
            case "/":
                if (op.getOperande2() == 0) return "Erreur : division par zéro.";
                return String.valueOf(op.getOperande1() / op.getOperande2());
            default: return "Erreur : opérateur invalide.";
        }
    }
}