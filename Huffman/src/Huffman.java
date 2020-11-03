import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
class HuffmanCodeGenerator {


    private void huffman() {
        int[] toMinst = new int[2];
        finnToMinste(toMinst);
        create(toMinst[0], toMinst[1]);
    }
    // such that minst[0] <= minst[1] <= minst[i]
    // for all i < lastTre altså alle tegnene
    private void finnToMinste(int[] minst) {
        if (f.tegnene[0].frekvens < f.tegnene[1].frekvens) {
            minst[0] = 0;

            minst[1] = 1;
        } else {
            minst[0] = 1;
            minst[1] = 0;
        }
        for (int i = 2; i <= sisteTre; i++) {
            if (f.tegnene[minst[0]].frekvens > f.tegnene[i].frekvens) {
                minst[1] = minst[0];
                minst[0] = i;
            } else if (f.tegnene[minst[1]].frekvens > f.tegnene[i].frekvens) {
                minst[1] = i;
            }
        }
    }

    // Lager en ny tre basert på de to minste noder jeg har funnet og oppdaterer tree veriene
    // Reduserer størrelsen til nodeTre altså slår de to minste sammen. Den minste settes til venstre
    private void create(int min0, int min1) {
        t.nodes[f.tegnene[min0].root].p = t.nodes[f.tegnene[min1].root].p = sisteNode;
        t.nodes[sisteNode].l = f.tegnene[min0].root;
        t.nodes[sisteNode].r = f.tegnene[min1].root;
        f.tegnene[min1].frekvens += f.tegnene[min0].frekvens;
        f.tegnene[min1].root = sisteNode;
        f.tegnene[min0] = f.tegnene[sisteTre];
        if (sisteNode != 2 * (n - 1)) {
            t.nodes[sisteNode].p = sisteNode + 1;
        }
        sisteNode++;
    }

    //     // Koden er 0 eller 1 til et tegn c
    char[] hentKode(char c) {
        for (int i = 0; i < characters.length; i++) {
            if (characters[i] == c) {
                return codes[i];
            }
        }
        return null;
    }

    public void startHuffman(char[] characters, int[] weights) {
        this.characters = characters;
        this.weights = weights;
        n = characters.length;
        sisteNode = n;
        sisteTre = n - 1;
        codes = new char[n][];
        f = new BinaryTree(weights);
        t = new BinaryTree(n * 2 - 1);
        // while vi har parent og children
        while (sisteTre >= 1) {
            huffman();
            sisteTre--;
        }
        code();
        System.out.println("Tree verdien");
        System.out.println("Indeks\tVenstre\thøyre\tparent");
        for (int i = 0; i < 2 * n - 1; i++) {
            System.out.println(i + "\t" + t.nodes[i].l + "\t" + t.nodes[i].r + "\t" + t.nodes[i].p);
        }
    }

    private void code() {
        for (int i = 0; i < n; i++) {
            char codeFlipped[] = new char[n - 1];
            int j = i, k = 0;
            while (t.nodes[j].p != -1) {
                if (t.nodes[t.nodes[j].p].l == j) {
                    codeFlipped[k] = '0';
                } else {
                    codeFlipped[k] = '1';
                }
                j = t.nodes[j].p;
                k++;
            }

            codes[i] = new char[k];
            j = 0;
            while (k > 0) {
                codes[i][j] = codeFlipped[k - 1];
                System.out.println("Codes--"+codes[i][j]);
                k--;
                j++;
            }
            System.out.println("Code of character " + characters[i] + " of weight " + weights[i] + " is :[" + String.valueOf(codes[i]) + "]"
            );
        }
    }

    //  Klassen defineres strukturen til tre
    class BinaryTree {

        // the tree defines a Node structure
        class Node {

            public int l = -1;
            public int r = -1;
            public int p = -1;

        }
        // for å lage en array med tegnene og beholkde deres verdi
        class Tegn {


            int root;
            int frekvens;

            public Tegn(int holder, int frekvens) {
                this.root = holder;
                this.frekvens = frekvens;
            }

        }
        public Tegn[] tegnene;
        public Node[] nodes;

        //initilaserer binærTree
        public BinaryTree(int size) {
            nodes = new Node[size];
            for (int i = 0; i < size; i++) {
                nodes[i] = new Node();
            }

        }
        public BinaryTree(int[] frekvensene) {
            tegnene = new Tegn[frekvensene.length];
            for (int i = 0; i < frekvensene.length; i++) {
                tegnene[i] = new Tegn(i, frekvensene[i]);
            }
        }
    }

    // hold avstands tegn verdier
    char characters[];
    // hold deres frekvens (antall ganger de er repetert)
    int weights[];
    //  coresponding codes
    char[][] codes;

    // binærTre variable
    BinaryTree t , f;

    // n: antall tegn
    // sisteTre er den nåværende siste elementet av binæreTre
    // sisteNode er de neste rooten i huffMan tre
    int n , sisteTre, sisteNode;

    // initialize the generator
    public HuffmanCodeGenerator() {
    }

    // sammenligne det med den orginale, og fhuffman dekoder resultatet
    public void printResult(char charContent[]) {
        System.out.println("Original text with binary value: ");
        for (int i = 0; i < charContent.length; i++) {
            if (i % 5 == 0 && i != 0) {
                System.out.println(""); //Ny linje
            }
            String binaryValue = Integer.toBinaryString(charContent[i]);
            while (binaryValue.length() < 8) {
                binaryValue = "0" + binaryValue;
            }
            System.out.print(binaryValue);
        }
        System.out.println("");
        System.out.println("[Original " + (charContent.length) + " bytes ], [ " + charContent.length * 8 + " bits ]");
        // 1 byte to represent a teg: normal ASCII
        System.out.println("-----------------------");
        System.out.println("Bruker huffman koding: ");
        int bitCount = 0;
        for (int i = 0; i < charContent.length; i++) {
            char[] code = hentKode(charContent[i]);
            for (int j = 0; j < code.length; j++) {
                if (bitCount % (40) == 0 && bitCount != 0) {
                    System.out.println("");
                }
                System.out.print(code[j]);
                bitCount++;
            }
        }
        System.out.println("");
        System.out.println("Kan bli redusert til [ " + (bitCount / 8) + " bytes ], [ " + bitCount + " bits ] " + (int) ((bitCount) / (8.0 * charContent.length) * 100) + " %");
    }
    // Read a text file and compress it to a file with .compress extention
    // Les file og koprimer den til file med komprimerte tvidelse
    public void komprimer(char[] charContent, String fileName) {

        try {
            File file = new File(fileName);
            DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
            // Lager metaData
            // Lager orginale innhold
            stream.writeInt(charContent.length);

            //lagrer antall forskjellige tegn
            stream.writeInt(characters.length); //92
            for (int i = 0; i < characters.length; i++) {
                // lagre tegnverdi
                stream.writeChar(characters[i]);  //ø
                // dens frekvens
                stream.writeInt(weights[i]); //2
            }
            // slutt av lagring av meta data

            int bitCount = 0;
            // tell den nåværende biten i byte for å lagre
            byte b = 0;
            // byte til å lagre "Binary I/O"
            System.out.println("lagring av binærsekvens: ");
            // loop through each character
            for (int i = 0; i < charContent.length; i++) {
                // få sin kode som en rekke av tegnene eksempel: {'1', '0','1'}
                // Verdien kan enten være 0 eller 1
                char code[] = hentKode(charContent[i]);
                for (int j = 0; j < code.length; j++) {
                    if (code[j] == '1') {
                        b |= 0x01;
                        // set første biten = 1
                    }
                    if (bitCount == 7) {
                        // byte er full ===> reset bitTelleren
                        bitCount = 0;
                        // reset bits counter
                        String binaryValue = Integer.toBinaryString(Byte.toUnsignedInt(b));
                        //System.out.println("-------------"+binaryValue+"  --"+b+"..");
                        // få den binære verdien av det usignerte tegnet (byte)
                        while (binaryValue.length() < 8) {
                            binaryValue = "0" + binaryValue;
                            // legg til prefikser av nuller siden  en byte skal ha 8 biter
                        }
                        System.out.print(binaryValue);
                        stream.write(b);
                        // skriver byte til stream
                        b = 0;
                    } else {
                        System.out.println("Shift to left"+b+" --"+bitCount);
                        b <<= 1;
                        // shift til venstre for å legge den nye biten og beholde det jeg har fra før
                        bitCount++;
                    }
                }

            }
            /* i huffman-kode kan summen av alle koder ikke være et multiplum av 8
            ==> jeg legger nuller til den siste byten fra høyre for å fylle byten*/
            if (bitCount != 0) {
                b <<= 7 - bitCount;
                System.out.println("Bitcount shifted left with 0  "+bitCount);
                stream.write(b);
            }
            String binaryValue = Integer.toBinaryString(Byte.toUnsignedInt(b));
            while (binaryValue.length() < 8) {
                binaryValue = "0" + binaryValue;
            }
            System.out.print(binaryValue);
            System.out.println("");
            stream.flush();
            // flush to stream

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // lese en komprimert fil og komprimere den til originalen
    public void dekomprimer(String fileName) {
        StringBuilder builder = new StringBuilder();
        // string builder for å lagre huffman-koden
        int contentLength = 0;
        try {
            File file = new File(fileName);
            DataInputStream stream = new DataInputStream(new FileInputStream(file));
            // hente metadata
            contentLength = stream.readInt();
            int length = stream.readInt(); // leser inout bytes fra inputstream, og read leser bytes og lagrer dem i en buffer
            char[] chars = new char[length];
            int[] weights = new int[length];
            for (int i = 0; i < length; i++) {
                chars[i] = stream.readChar();
                weights[i] = stream.readInt();
            }
            // slutt henting data

            // finn huffman-kode fra metadata
            startHuffman(chars, weights);
            // Her har jeg huffman-koden
            while (true) {
                byte b = stream.readByte();     // Leser byte
                int v = Byte.toUnsignedInt(b);  //kaster byte til int

                String binaryValue = Integer.toBinaryString(v); // Bruker integer verdien til byte for å konvertere den til en string
                while (binaryValue.length() < 8) {
                    binaryValue = "0" + binaryValue;
                }
                builder.append(binaryValue);
                // få de binære kodene som strøm av tegn
            }

        } catch (Exception e) {

            if (builder.length() != 0) {

                String content = builder.toString();
                // string content (huffman-kode) 01001....

                StringBuilder uncompressed = new StringBuilder();
                String codes[] = new String[this.codes.length];
                for (int i = 0; i < codes.length; i++) {
                    codes[i] = String.valueOf(this.codes[i]);
                }
                // codes er konvertert til strings 010,11 ....
                int start = 0;
                while (start < content.length() && contentLength != uncompressed.length()) {
                    /* Stop når start overskrider innholdslengden
                    / eller når innholdet Lengde == til ukomprimert lengde */
                    for (int i = 0; i < codes.length; i++) {
                        /* sjekk hvilken huffman-kode sekvensen starter med
                           med en start offset dekoder jeg kode sekvensiell kode etter kode*/
                        if (content.startsWith(codes[i], start)) {
                            uncompressed.append(characters[i]);
                            // legge til den tilsvarende tegnverdien på koden
                            start += codes[i].length();
                            //  legg til kodelengden for å starte
                        }
                    }
                }
                try {
                    PrintWriter writer = new PrintWriter(new FileOutputStream(new File(fileName.replace(".compressed", ".new"))));
                    /* skriv den ukomprimerte strengen til en annen fil og fjern .utvidelse */
                    writer.append(uncompressed.toString());
                    writer.flush();
                    // Text I/O
                } catch (Exception ex) {
                }
            } else {
                System.out.println("builder empty");
            }
        }
    }

}