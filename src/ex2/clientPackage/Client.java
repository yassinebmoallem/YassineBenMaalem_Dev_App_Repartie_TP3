package ex2.clientPackage;


import ex2.communPackage.Operation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetAddress adresseServeur = InetAddress.getByName("localhost");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(adresseServeur, 1244));

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        String accueil = (String) ois.readObject();
        System.out.println(accueil);

        Scanner clavier = new Scanner(System.in);
        Pattern pattern = Pattern.compile("^(\\d+)\\s*([+*/-])\\s*(\\d+)$");

        while (true) {
            System.out.print("Entrez une opération  ou \"quit\" pour quitter : ");
            String saisie = clavier.nextLine().trim();

            if (saisie.equals("quit")) {
                oos.writeObject(null);
                break;
            }

            Matcher matcher = pattern.matcher(saisie);
            if (!matcher.matches()) {
                System.out.println("Erreur : opération invalide.");
                continue;
            }

            Operation op = new Operation(
                    Integer.parseInt(matcher.group(1)),
                    matcher.group(2),
                    Integer.parseInt(matcher.group(3)));
            oos.writeObject(op);

            String resultat = (String) ois.readObject();
            System.out.println("Résultat reçu : " + resultat);
        }

        oos.close();
        ois.close();
        clavier.close();
        socket.close();
    }
}