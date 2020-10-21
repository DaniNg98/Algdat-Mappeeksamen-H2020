package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        EksamenSBinTre<String> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(tre.antall());
        //test
    }
}
