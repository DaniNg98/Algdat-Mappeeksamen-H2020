package no.oslomet.cs.algdat.Eksamen;


import org.w3c.dom.xpath.XPathResult;

import java.sql.Date;
import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        // Ikke lov til å legge inn nullverdier
        Objects.requireNonNull(verdi, "Ikke lov med nullverdier!");

        // Hentet fra kompendiet: Kildekode 5.2.3 a)
        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        // Sørger for at referansen q(forelder) får korrekt verdi i hver node
        p = new Node<>(verdi, q);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging


    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        // Setter en peker på rot roden
        Node<T> p = rot;
        // Teller
        int teller = 0;

        // Starter på toppen av treet
        while (p != null) {

            // Sammenlikner p sin verdi med vår verdi
            int cmp = comp.compare(verdi, p.verdi);

            // Hvis verdi er mindre enn p sin verdi. Gå til venstre
            if (cmp < 0) {
                // Flytter pekeren til venstre barn
                p = p.venstre;
            } else {
                // Øker telleren hvis verdi er lik p sin verdi
                if (cmp == 0) teller++;
                // Flytter pekeren til høyre barn
                p = p.høyre;
            }
        }
        return teller;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        // Hvis p.venstre eksisterer. Flytt p til p.venstre
        while (p.venstre != null || p.høyre != null) {
            if (p.venstre != null) {
                p = p.venstre;
            } else {
                p = p.høyre;
            }
            // Hvis p ikke har barn. Returner p
            if (p.venstre == null && p.høyre == null) {
                return p;
            }
        }
        return p;

    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        // Forelder noden til p
        Node <T> f = p.forelder;

        // Hvis p sin forelder ikke er null
        if (f != null) {
            // Hvis p er høyre barn til sin forelder f, er forelderen f den nesten
            if (p == f.høyre){
                p = f;
            } else {
                // Hvis p sin f.høyre ikke finnes. Da er f den neste
                if (f.høyre == null) {
                    p = f;
                } else {
                    // Hvis p sin f.høyre finnes. Da er f.høyre den neste
                    p = f.høyre;

                    while (f.venstre != null) {
                        p = f.venstre;
                        Node<T> current = p;
                        while (current != null) {
                            p.forelder = p.forelder.høyre;
                            current = p.forelder.venstre;
                        }
                    }
                }

            }
        } else {
            // Hvis p sin forelder er null. Da er vi på den siste noden i post orden.
            return null;
        }
        return p;

    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
