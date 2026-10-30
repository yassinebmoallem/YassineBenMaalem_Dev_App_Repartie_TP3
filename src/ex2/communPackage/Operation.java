package ex2.communPackage;

import java.io.Serializable;

public class Operation implements Serializable {
    private int operande1;
    private String operateur;
    private int operande2;

    public Operation(int operande1, String operateur, int operande2) {
        this.operande1 = operande1;
        this.operateur = operateur;
        this.operande2 = operande2;
    }

    public int getOperande1() { return operande1; }
    public String getOperateur() { return operateur; }
    public int getOperande2() { return operande2; }

    @Override
    public String toString() {
        return operande1 + " " + operateur + " " + operande2;
    }
}