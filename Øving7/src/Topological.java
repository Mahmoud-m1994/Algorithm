import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Topological {
    private int K;   // Antall kanter
    private LinkedList<Integer> liste[]; // Liste
    int N;

    public void lesGraf(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        BufferedReader br = new BufferedReader(reader);
        StringTokenizer st=new StringTokenizer(br.readLine());
        N= Integer.parseInt(st.nextToken());
        K=Integer.parseInt(st.nextToken());
        liste = new LinkedList[K];
        for(int i=0; i<K; i++){
            liste[i] = new LinkedList();
        }
        for(int i=0;i<K;i++){
            st=new StringTokenizer(br.readLine());
            int fra= Integer.parseInt(st.nextToken());  //kantens startpunkt
            int til= Integer.parseInt(st.nextToken());  //Kantens endepunkt
            leggTilKant(fra,til);
        }
    }
    void leggTilKant(int v,int w) { liste[v].add(w); }


    void df_topo(int v, boolean funnet[],Stack stack) {

        funnet[v] = true;
        Integer i;

        Iterator<Integer> kanten = liste[v].iterator();
        while (kanten.hasNext())
        {
            i = kanten.next();
            if (!funnet[i])
                df_topo(i, funnet, stack);
        }

        stack.push(new Integer(v));
    }


    void topologiSort(){
        Stack stakk = new Stack();

        // merk alle kantene som ikke funnet
        boolean funnet[] = new boolean[K];
        for (int i = 0; i < N; i++) {
            funnet[i] = false;
        }


        for (int i = 0; i < N; i++) {
            if (funnet[i] == false) df_topo(i, funnet, stakk);
        }

        // Print contents of stack
        while (stakk.empty()==false)
            System.out.print(stakk.pop() + " ");
    }

    public static void main(String args[]) throws IOException {
        // Create a graph given in the above diagram
        Topological g = new Topological();
        g.lesGraf("/Users/mahmoudibrahim/IdeaProjects/Algoritmer/Øving7/src/L7g5.webarchive");
        g.topologiSort();
    }
}