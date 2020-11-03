import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class EdmondsKarp {

    private int[][] flyt; //maks flyt mellom i og j
    private int[][] kapasitet; // kantens kapasitet
    private int[] parent; //parent (nodes)
    private boolean[] visited; //sjekker om noden er besøkt

    private int N, K;

    public EdmondsKarp (String filname) throws IOException {
        lesGraf(filname);
    }

    public void leggTilKant(int fra,  int til,  long vekt) {
        assert vekt >= 0;
        this.kapasitet[fra][til] += vekt;
        //System.out.println("Lagt til kant fra "+fra+"til "+til);
    }
    public void lesGraf(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        BufferedReader br = new BufferedReader(reader);
        StringTokenizer st=new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        //System.out.println(N);
        this.parent = new int[N];
        for (int i=0; i<N; i++){
            parent[i]=0;
        }
        this.flyt = new int[N][N];
        this.kapasitet = new int[N][N];
        this.visited = new boolean[N];
        K=Integer.parseInt(st.nextToken());
        for(int i=0; i<K;i++) {
            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            int vekt = Integer.parseInt(st.nextToken());
            leggTilKant(fra, til, vekt);
        }
    }


    public long getMaksFlyt(int s, int t) {
        while (true) {
            final Queue<Integer> Q = new ArrayDeque<Integer>(); //Kø for nodes
            Q.add(s);

            for (int i = 0; i < this.N; ++i)
                visited[i] = false;  //Merker alle som ikke er besøkt utenom current
            visited[s] = true;

            boolean sjekk = false;
            int current;
            while (!Q.isEmpty()) {
                current = Q.peek();
                if (current == t) {
                    sjekk = true;
                    break;
                }
                Q.remove();
                for (int i = 0; i < N; ++i) {
                    if (!visited[i] && kapasitet[current][i] > flyt[current][i]) {
                        visited[i] = true;
                        Q.add(i);
                        parent[i] = current;
                    }
                }
            }
            if (sjekk == false)
                break;

            long temp = kapasitet[parent[t]][t] - flyt[parent[t]][t];
            for (int i = t; i != s; i = parent[i])
                temp = Math.min(temp, (kapasitet[parent[i]][i] - flyt[parent[i]][i]));

            for (int i = t; i != s; i = parent[i]) {
                flyt[parent[i]][i] += temp;
                flyt[i][parent[i]] -= temp;
            }
        }

        long result = 0;
        for (int i = 0; i < N; ++i)
            result += flyt[s][i];
        return result;
    }
    public static void main(String[] args) throws IOException{
        EdmondsKarp maksFlyt1=new EdmondsKarp("/Users/mahmoudibrahim/IdeaProjects/Algoritmer/Øving8/src/flytgraf1.webarchive");
        System.out.println("Maks flyt fra graf1= "+maksFlyt1.getMaksFlyt(0,7));

    }

}