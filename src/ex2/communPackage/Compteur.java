package ex2.communPackage;

public class Compteur {
    private int compteur = 0;

    public synchronized int incrementer() {
        compteur++;
        return compteur;
    }

    public synchronized int getCompteur() {
        return compteur;
    }
}