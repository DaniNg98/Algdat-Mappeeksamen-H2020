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
        // Henter programkode 5.2.8 d) fra kompendiet

        // treet har ingen nullverdier
        if (verdi == null) return false;

        // q skal være forelder til p
        Node<T> p = rot, q = null;

        // leter etter verdi
        while (p != null)
        {
            int cmp = comp.compare(verdi,p.verdi);
            // går til venstre
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }
            // går til høyre
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }
            // den søkte verdien ligger i p
            else break;
        }
        // finner ikke verdi
        if (p == null) return false;

        // Hvis vi p ikke har barn eller p har nøyaktig ett barn
        if (p.venstre == null || p.høyre == null)  {
            // Barne noden
            Node<T> b;

            // Hvis p har venstre barn. Da blir b venstre barnet til p
            if (p.venstre != null) {
                b = p.venstre;
            }
            // Hvis p ikke har venstre barn. Da blir b høyre barnet til p
            else {
                b = p.høyre;
            }
            // Hvis p er rotnoden, settes rotreferansen til b
            // som enten er p.venstre, p.høyre eller null
            if (p == rot) {
                rot = b;

                // Hvis b eksisterer på vi sette b sin forelder peker
                // til null
                if (b != null) {
                    b.forelder = null;
                }

            }

            // Hvis p er venstre barnet til q
            else if (p == q.venstre) {
                // q.venstre er forelder til b
                q.venstre = b;
                // hvis barnet b eksisterer
                if (b != null) {
                    // Setter b.forelder peker til q
                    b.forelder = q;
                }
            }
            // Hvis p ikke er venstre barnet til q
            else {
                // q.høyre er forelder til b
                q.høyre = b;
                // Setter b.forelder peker til q
                if (b != null) {
                    b.forelder = q;
                }
            }
        }
        // p har 2 barn
        else  {

            // Skal finne den nestet i inorden
            // s peker på samme node som p foreløpig
            // r peker på p sin høyre foreløpig
            Node<T> s = p, r = p.høyre;

            // Løper ned r sin venstre side
            while (r.venstre != null) {
                s = r;
                r = r.venstre;
            }

            // kopierer verdien i r til p
            p.verdi = r.verdi;

            // Hvis s ikke peker på p
            if (s != p) {
                // Setter s sin venstre peker over til r sin høyre barn
                s.venstre = r.høyre;
            }
            // Hvis s peker på p
            else {
                // Flytter s sin høyre over til r sin høyre
                s.høyre = r.høyre;
            }
        }

        // det er nå én node mindre i treet
        antall--;
        // Øker endringer
        endringer++;
        return true;

    }

    public int fjernAlle(T verdi) {
        if (tom()) {
            return 0;
        }

        // Kopierer kildekode fra løsningsforslaget til oppgave 3 i avsnitt 5.2.8 fra kompendiet
        // teller antall slettet
        int antallSlettet = 0;

        // Siden fjern() er en boolean kan vi bruke
        // en while løkke til å kalle på fjern()
        // og fjerne alle verdiene fra treet.
        while (fjern(verdi)) {
            antallSlettet++;
        }

        return antallSlettet;
    }

    public int antall(T verdi) {
        // Kopierer programkode fra løsningsforslaget til oppgave 2 i ansvitt 5.2.6 fra kompendiet
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
        // Kopierte programkode fra løsningsforslaget til oppgave 2 i avsnitt 5.2.4 fra kompendiet
        if (!tom()) nullstill(rot);  // nullstiller
        rot = null;
        antall = 0;      // treet er nå tomt
        endringer++;   // treet er endret
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
                // p er venstre barn til f og p er enebarn
                // Hvis p sin f.høyre ikke finnes. Da er f den neste
                if (f.høyre == null) {
                    p = f;
                } else {
                    // p er venstre barn til f og p er ikke enebarn
                    // Hvis p sin f.høyre finnes. Da er f.høyre den neste
                    p = f.høyre;

                    // Traverserer ned f.høyre sitt subtre. Den neste er noden
                    // vi lander på som ikke har noen barn
                    while (p.venstre != null || p.høyre != null) {
                        // Flytter peker til venstre barn hvis den eksisterer
                        if (p.venstre != null) {
                            p = p.venstre;
                        } else {
                            // Flytter peker til høyre barn hvis den eksisterer¢
                            p = p.høyre;
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
        // p er den første noden i postorden
        Node<T> p = førstePostorden(rot);

        // Looper igjennom hele treet i postorden
        while (p != null) {
            // Skriver ut
            oppgave.utførOppgave(p.verdi);
            // Flytter p til neste postorden
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        // Bruker ideen fra metoden printPreorder() fra forelesningen MyBinaryTree

        // Rekursivt gå til venstre
        if (p.venstre != null) {
            postordenRecursive(p.venstre, oppgave);
        }

        // Rekursivt gå til høyre
        if (p.høyre != null) {
            postordenRecursive(p.høyre, oppgave);
        }

        // Skriver ut verdien
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {

        // Lager en ny arraylist
        ArrayList<T> liste = new ArrayList<>();

        // Legger inn verdier i listen vår i nivåorden ved hjelp av nivåorden()
        nivåorden(liste);

        // Returnerer
        return liste;

        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        // Henter programkode 5.2.3 c) fra kompendiet.

        // Lager et nytt tre
        EksamenSBinTre tre = new EksamenSBinTre<>(c);

        // Itererer over Arraylisten data. For hver verdi vi løper over,
        // legges verdien inn i treet.
        data.forEach(tre::leggInn);

        // Returnerer treet
        return tre;
    }

    /////////////////// Hjelpemetoder hentet fra kompendiet //////////////////////////////////////

    // Kopiert inn programkode 5.1.6 a) fra kompendiet. Denne skal brukes i metoden serialize()
    public void nivåorden(ArrayList liste)
    {
        // Treet er tomt
        if (tom()) return;

        // Lager en kø
        Queue<Node<T>> queue = new LinkedList<>();
        // Legger inn roten
        queue.add(rot);

        // Så lenge køen ikke tom så tar vi ut verdien fra køen
        // og legger den inn i vår Arraylist
        while (!queue.isEmpty())
        {
            Node<T> p = queue.remove();

            // Legger til den verdien som blir tatt ut fra køen inn i listen vår
            liste.add(p.verdi);

            if (p.venstre != null) {
                queue.add(p.venstre);
            }
            if (p.høyre != null) {
                queue.add(p.høyre);
            }
        }
    }

    // Kopierte programkode fra løsningsforslaget til oppgave 2 i avsnitt 5.2.4 fra kompendiet
    // Hjelpemetode som bruker i nullstill()
    private void nullstill(Node<T> p)
    {
        if (p.venstre != null)
        {
            nullstill(p.venstre);      // venstre subtre
            p.venstre = null;          // nuller peker
        }
        if (p.høyre != null)
        {
            nullstill(p.høyre);        // høyre subtre
            p.høyre = null;            // nuller peker
        }
        p.verdi = null;              // nuller verdien
    }

} // ObligSBinTre
