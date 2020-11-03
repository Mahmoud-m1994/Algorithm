import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

class Kant{
    Kant neste;
    Node til;
    public Kant (Node n, Kant nst){
        this.neste=nst;
        this.til= n;
    }
}


class Node {
    Kant kant1;
    int id;
    Object d; //forgjenger
    /*public Node(int id){
        this.id=id;
    }*/
}
class Forgj{
    int dist; //avstand mellom noden og startnoden.
    Node forgj;
    static int uendelig=1000000000;
    public int finnDist(){
        return dist;
    }
    public Node finnForgj(){
        return forgj;
    }
    public Forgj(){
        dist =uendelig;
    }
}

//Class for Topologisk list
class TopoList{
    boolean funnet;
    Node neste;
}

class Graf{
    int N ;//Antall noder
    int  K; //ANtall kanter
    Node[] node;
    Kø kø;

    public void lesGraf(String fileName) throws IOException{
        FileReader reader = new FileReader(fileName);
        BufferedReader br = new BufferedReader(reader);
        StringTokenizer st=new StringTokenizer(br.readLine());
        N= Integer.parseInt(st.nextToken());
        node=new Node[N];
        for(int i=0; i<N; i++){
            node[i]=new Node();
        }
        K=Integer.parseInt(st.nextToken());
        for(int i=0;i<K;i++){
            st=new StringTokenizer(br.readLine());
            int fra= Integer.parseInt(st.nextToken());  //kantens startpunkt
            int til= Integer.parseInt(st.nextToken());  //Kantens endepunkt
            Kant k=new Kant(node[til],node[fra].kant1);
            node[fra].kant1=k;
            node[fra].id=fra;
            node[til].id=til;
        }
    }

    //Gjör gafen klar til søket, alle noder får uendelig avstand og ingen forgjenger.
    public void initForgj(Node s){
        for (int i=0;i<N;i++){
            node[i].d=new Forgj();
        }

        ((Forgj)s.d).dist=0;
    }

    public void breddeForstSok(Node s){
        initForgj(s);
        kø = new Kø(N-1);
        kø.leggIKø(s);
        System.out.println("Node       Forgje      distanse");
        System.out.println(s.id+"                       "+((Forgj)s.d).dist);  //Skriver info om den første noden
        while (!kø.tom()){
            Node n = (Node) kø.nesteIKø();

            for(Kant k =n.kant1; k !=null; k= k.neste) {
                Forgj f = (Forgj) k.til.d;
                if (f.dist == f.uendelig) {  //Legger den i køen og merkerer den som funnet
                    f.dist = ((Forgj) n.d).dist + 1;
                    f.forgj = n;
                    kø.leggIKø(k.til);
                }
            }
            if(n!=s ) { //For å sjekke om det er startNode
                System.out.println(n.id + "          " + ((Forgj)n.d).finnForgj().id + "            " + ((Forgj)n.d).dist);
            }
        }
    }

    public static void main(String[]args) throws IOException {
        Graf graf=new Graf();
        graf.lesGraf("/Users/mahmoudibrahim/IdeaProjects/Algoritmer/Øving7/src/Øving7-graf.webarchive");
        graf.breddeForstSok(graf.node[5]);



    }
}