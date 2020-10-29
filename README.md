# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Vi har brukt git til å dokumentere arbeidet vårt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1:  
Henter først programkode 5.2.3 a) fra kompendiet inn i vår leggInn metode. Koden sammenligner først verdien vi ønsker å legge inn med
rotnoden. Hvis vår verdi er mindre enn rotnoden flyttes pekeren p til rotnodens venstre barn. Og hvis vår verdi er større eller lik
rotnoden flyttes pekeren p til rotnodens høyrebarn. Videre sammenligner vi vår verdi med p sin nye verdi. Også fortsetter vi sånn intill
vi har kommet til en bladnode. Nå kan vi opprette en ny node, og her må vi bare sørge for at referansen forelder(q) får riktig referanse.
Videre sjekker vi om q eksisterer. Hvis ikke er verdien vår rotnoden. Hvis verdien vår er mindre en forelderen q blir vår verdi
venstrebarnet til q. Og hvis vår er større eller lik forelderen q blir vår verdi høyrebarnet til q. Til slutt oppdaterer vi antall.

* Oppgave 2:  
Henter programkode fra løsningsforslaget til oppgave 2 i avnsnitt 5.2.6 fra kompendiet. For å løse denne oppgaven oppretter vi først en peker p
som refererer til rot. Også må vi ha en teller som oppdateres for hver gang verdien forekommer. For å finne hvor mange ganger verdien forekommer
bruker vi en while-løkke. Vi starter på toppen av treet(roten) og går nedover. Vi kan enkelt bruke comp.compare til å sammenlikne vår verdi med p.verdi.
Hvis vår verdi er mindre enn p.verdi flytter vi pekeren p til venstre barn. Og hvis vår verdi er lik p.verdi, øker vi telleren også flytter vi pekeren p
til høyre barn. Til slutt returnerer vi telleren.  

* Oppgave 3a:  
Vi vet at første postorden er den bladnoden som ligger lengst til venstre eller til høyre. Så vi kan først begynne med å løpe ned i treet så lenge
p har venstre eller om p har høyre barn. Flytter pekeren p til venstre barn hvis den eksisterer. Flytter pekeren p til høyre barn hvis venstre barn
ikke eksisterer. Og når vi har kommet til bladnoden returnerer vi bladnoden som er første i postorden.  

* Oppgave 3b:  
For å finne neste postorden sjekker vi først om f(p.forelder) har en forelder. Vi vet at hvis p ikke har en forelder, altså at p er rotnoden,
da må p være den siste i postorden. Nå må vi håndtere det tilfellet der p er høyre barn til sin forelder f. Da sjekker vi om p peker refererer til
f.høyre. Hvis det stemmer kopierer vi p til f fordi f må være den neste. Nå må vi håndtere det tilfellet der p er venstre barn og at p er enebarn.
Da sjekker vi bare om hvis p ikke refererer til f.høyre og at f.høyre ikke finnes. Hvis det stemmer er f den neste. Nå må vi sjekke om p er
venstre barn og ikke enebarn. Hvis det er tilfellet flytter vi p over til f.høyre. Og hvis f.høyre har barn så løper vi f sit høyre subtre intill vi
treffer en venstre eller høyre bladnode. Til slutt kan vi retunrnere null dersom f ikke har en forelder.  

* Oppgave 4a:  
For å skrive ut treet i postorden finner vi først den første i postorden med metoden førstePostorden(Node<T> p) og legger den verdien vi finner
inn i en node p. Deretter bruker vi en while-løkke til å skrive ut hele treet i postorden. Vi bruker oppgave til å skrive ut verdien til skjerm,
så setter vi p = nestePostorden(p) for å gi den neste i postorden. Også fortsetter vi sånn helt til p retunerer null;

* Oppgave 4b:
For å skrive ut treet i postorden rekursivt brukte jeg ideen fra forelesning Uke 10 om binærtrær i kode, 55 minutter inn i forelesningsvideoen.
Først går vi rekursivt ned i treet mot venstre dersom p.venstre finnes, også fortsetter vi sånn rekursivt fram til p.venstre er null. Når p.venstre
er null går enten rekursivt ned til høyre dersom p.høyre finnes eller så skriver vi ut verdien vi står på. Og sånn fortsetter vi rekursivt gjennom
hele treet fram til alle verdiene er skrevet ut.  

* Oppgave 5 a:  
For å serialisere henter jeg først programkode 5.1.6 a) fra kompendiet. Metoden jeg tok fra kompendiet skal brukes som en hjelpemetode i serialize().
Først endrer vi litt på hjelpemetoden nivåorden() slik at den tar inn en Arraylist. Også bytter vi Kø med en Queue. Det nivåorden(Arraylist liste)
gjør for oss er å traversere gjennom treet i nivå orden ved hjelp av en queue. I serialize() lager vi først en ny ArrayList<T> og kaller den liste.
Så kaller vi bare på nivåorden(ArrayList<T> liste) og lar den metoden skrivet ut hele treet i nivå orden for oss. Til slutt returnerer vi listen.  

* Oppgave 5 b:  
For å deserialisere henter jeg programkode 5.2.3 c) fra kompendiet. Gjør noen få endringer i programkode 5.2.3 c) slik at jeg kan bruke den i metoden
deserialize(ArrayList<K> data, Comparator<? super K> c). Vi erstatte SBinTre<T> med EksamensSBinTre<K> og s med data. Deretter vil koden kjøre. Så det
vi gjør er at vi først oppretter et nytt tre. Også itererer vi over Arraylisten data. For hver verdi vi løper over, legges verdien inn i treet. Til
slutt returnerer vi treet.  

* Oppgave 6 a:  
Kopierer først programkode 5.2.8 d) fra kompendiet som vi skal bruke i fjern(T verdi) metoden. Nå trenger vi bare å legge til litt kode som sørger for
at pekeren forelder får korrekt verdi i alle noder etter fjerning. For at det skulle bli litt lettere for meg å lese koden la jeg bare til flere line
breaks i if setningene også skrev jeg om på koden i linje 148. Men koden gjør fortsatt det samme. Den første ordenlige endringen jeg måtte gjøre ligger
i kode linje 165. Der la jeg til en if-check som sørger for at b sin forelder refererer til null hvis dersom vi har at p er rotnoden. Neste endering
ligger i kodelinje 176. Hvis p er venstre barnet til q setter vi q.venstre til b, og da må vi sørge for at b sin forelder peker på q. Siste endringen vi
må gjøre ligger i kodelinje 186. Hvis p ikke er venstre barnet til q setter vi q.høyre til b, og da må vi igjen sørge for at b sin forelder peker på q.
I tillegg la jeg til endringer++ helt på slutten. Koden kjørte som den skulle etter at disse endringene var gjort.

* Oppgave 6 b:
fjernAlle(T verdi) var relativt grei å skrive. Først så legger vi bare til en if-check som sjekker om treet er tomt. Hvis det stemmer returnerer vi 0
med en gang. Videre oppretter vi en teller. Også bruker vi fjern(T verdi) som en hjelpemetode til å fjerne alle forekomster av verdien vi ønkser å
fjerne. Siden fjern(T verdi) er en boolean passer det perfekt til at vi kan bruke en while-løkke til å fjerne alle forekomster av verdien. Så når vi
går inn i løkken while(fjern(verdi)) økes telleren hver gang vi finner en forekomst av verdien vi sletter. Til slutt returnerer vi telleren.  

* Oppgave 6 c:  
Kopierer programkode fra løsningsforslaget til oppgave 2 i avsnitt 5.2.4 fra kompendiet. Her bruker vi en hjelpemetode nullstill(Node<T> p) som
rekursivt sletter alle verdiene treet fra bunnen og opp til roten. I selve nullstill() metoden sjekker vi først om at treet ikke er tomt. Hvis
det stemmer kaller vi på nullstill(Node<T> p) og sletter hele treet. Så setter vi rot til null, antall lik 0 og øker endringer.











 