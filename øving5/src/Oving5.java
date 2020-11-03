import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

class Hashtable {
    static private Node [] hashTable  = new Node[100];

    public static int kolisjon=0;
    static class Node{
        String key;
        String val;
        Node next;
    }
    static int A = 1327217885;
    String [] array;
    int arraySize;
    int itemsArray=0;


    public static int divhash(int k, int m){
        return k % m;
    }
    static int konvert(String ord){
        int sum=0;
        for(int i=0; i<ord.length();i++){
            char x = ord.charAt(i);
            sum += (x - '0')*i;
        }
        return sum;
    }
    public static boolean insertNames() throws IOException {
        File file = new File("/Users/mahmoudibrahim/IdeaProjects/Algoritmer/øving5/src/navn");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String name;
        while ((name = br.readLine()) != null) {
            insertName(konvert(name),name);
        }
        return true;
    }
    static void insertName(int key, String value){
        int x = konvert(value);
        int index = divhash(x,hashTable.length);
        //hver nøkkelstreng vill gi meg sin unike indeks
        Node newNode;
        newNode = new Node();
        //newNode.key = key;
        newNode.val = value;
        if (hashTable[index] == null){//the linked list is still empty and this is the first entry
            hashTable[index] = newNode;
        }
        else {
            Node runner; // the runner will go throught the linked list wothout loosing the head
            runner = hashTable[index];
            Node prevNode = new Node();
            while (runner!=null){
                prevNode = runner;
                runner = runner.next;
                kolisjon++;
            }
            runner = newNode;
            prevNode.next = runner;
        }
    }
    public int getAntallOptatt(){
        int svar=0;
        for (int i=0;i<hashTable.length;i++){
            if(hashTable[i] !=null){
                svar++;
            }
        }
        return svar;
    }
    //henter indeksen gitt et navn
    public int getIndeks(String name){
        int x = konvert(name);
        int index = divhash(x,hashTable.length);
        if(index==0)
            System.out.println("HALO 00000");
        return index;
    }



    public static void main(String[] args) throws IOException {
        Hashtable hashtable = new Hashtable();
        String input = "Mahmood,Dilawar";
        String n = "Derouiche,Emir";
        System.out.println("tester caro verdi "+hashtable.konvert(input));
        System.out.println("tester cora verdi "+hashtable.konvert(n));
        insertNames();
        System.out.println("last faktor er "+(double) hashtable.getAntallOptatt()/hashTable.length);

        System.out.println("Antall kollisjoner er "+ kolisjon);




    }
}
