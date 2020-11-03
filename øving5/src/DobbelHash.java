import java.util.HashMap;
import java.util.Random;

class DobbelHash {
    static final int TABELL_STØRELLSE = 6000011;
    static int currStørerelse;
    static int [] tabell ;
    static Random rand = new Random();
    public static int kolisjon=0;


    DobbelHash()
    {
        tabell = new int[TABELL_STØRELLSE];
        currStørerelse = 0;
        for (int i=0; i<TABELL_STØRELLSE; i++) {
            tabell[i] = -1;
        }
    }

    // henter random tall
    static int getRandom(){
        int tall;
        tall = rand.nextInt(100000000-10) +10;
        if(tall==0){
           tall= getRandom();
        }
        if(tall<0){
            tall= -1*tall;
        }
        return tall;
    }

    public static int getPrime() {
        int num = getRandom();


        while (!erPrimtall(num)) {
            num = rand.nextInt(1000) + 1;
        }
        return num;
    }

    static boolean erPrimtall(int input) {
        if (input <= 3 || input % 2 == 0)
            return input == 2 || input == 3;
        int delilig = 3;
        while ((delilig <= Math.sqrt(input)) && (input % delilig != 0))
            delilig += 2; //går gjennom mulige divaisor
        return input % delilig != 0;
        //return true/false
    }

    //Sjekker hvis hash tabell er full
    static boolean erFull() {
        if (currStørerelse == TABELL_STØRELLSE)
            return true;
        else
            return false;
    }

    public static int hashFunksjon1(int key) {
        int indeks= key % TABELL_STØRELLSE;
        if(indeks<0){
            indeks= -1*indeks;
        }
        return indeks;

    }

    public static int hashFunksjon2(int key) {
        int svar = 0;
        int x = TABELL_STØRELLSE;
        svar = key % (x - 1) + 1;

        if(svar<0 || svar==0){
            svar=(svar*-1)+1;
        }
        if (svar==TABELL_STØRELLSE){
            svar=svar-2;
        }

        return svar;
    }

     static void insert(int key) {
        if (erFull()) {
            return;
        }
        int indeks = hashFunksjon1(key);


        // kollisjon oppstår
        if (tabell[indeks] != -1) {
            kolisjon++;
            int indeks2 = hashFunksjon2(key);

            while (true) {
                // get newIndex
                indeks = (indeks + indeks2) % TABELL_STØRELLSE;

                //lagre key hvis kollisjonen ikke oppstår
                if (tabell[indeks] == -1) {
                    tabell[indeks] = key;
                    //currStørerelse++;
                    break;
                }

            }
        } else {
            // Ingen kollisjon
            tabell[indeks] = key;
            currStørerelse++;
        }
    }

    public void getTabell() {
        for (int i = 0; i < TABELL_STØRELLSE; i++) {
            if (tabell[i] != -1) {
                System.out.println(i + " --->" + tabell[i]);
            } else {
                System.out.println(i);
            }
        }
    }


    public static void main(String[] args) {
        HashMap hashMap= new HashMap();

        int [] hjelpTabell= new int[5000000];
        DobbelHash dh = new DobbelHash();
        Random random = new Random();
        int rand = random.nextInt();

        for(int i=0; i<hjelpTabell.length; i++){
            hjelpTabell[i]=getRandom();
        }
        long startTid= System.nanoTime();
        for(int i=0; i<hjelpTabell.length; i++){
            insert(hjelpTabell[i]);
        }
        long endTid= System.nanoTime();


        long startTid1= System.nanoTime();
        for(int i=0; i<hjelpTabell.length; i++){
            hashMap.put(hjelpTabell[i],hjelpTabell[i]);
        }
        long endTid1= System.nanoTime();



        System.out.println("kollisjonen er "+kolisjon);
        System.out.println("tidbrukt for min  metoden er "+(endTid-startTid));
        System.out.println("tidbrukt for java metoden er "+(endTid1-startTid1));
    }
}

