import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

class Road {

    ArrayList<Node> graf = new ArrayList<Node>();
    HashMap<String, Integer> punkter = new HashMap<String, Integer>();

    int startNodeInd = -1;
    int sluttNodeInd = -1;


    public Road(String nodeFil, String kantFil, String intPunkt){

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nodeFil)));

            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());

            for(int i = 0; i < N; i++){
                st = new StringTokenizer(br.readLine());
                int nr = Integer.parseInt(st.nextToken());
                double bredde = Double.parseDouble(st.nextToken());
                double lengde = Double.parseDouble(st.nextToken());
                graf.add(new Node(nr, lengde, bredde));
            }
            br.close();

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Feil med node-fil");
        }

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(kantFil)));

            StringTokenizer st = new StringTokenizer(br.readLine());

            int K = Integer.parseInt(st.nextToken());

            for(int i = 0; i < K; i++){
                st = new StringTokenizer(br.readLine());
                graf.get(Integer.parseInt(st.nextToken())).nyKant( // legger til ny kant i node
                        graf.get(Integer.parseInt(st.nextToken())), // til-node
                        Integer.parseInt(st.nextToken()), // kjoretid
                        Integer.parseInt(st.nextToken()), // lengde
                        Integer.parseInt(st.nextToken())); // fartsgrense
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Feil med kant-fil");
        }

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(intPunkt)));

            StringTokenizer st = new StringTokenizer(br.readLine());

            int K = Integer.parseInt(st.nextToken());

            for(int i = 0; i < K; i++){
                st = new StringTokenizer(br.readLine());
                int nr = Integer.parseInt(st.nextToken());
                String placeholder = st.nextToken();
                String navn = st.nextToken().replaceAll("\"","");
                punkter.put(navn, nr);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Feil med interessepunkt-fil");
        }
    }

    void hentVeien() {
        int teller = -1;
        int node = sluttNodeInd;
        double timer = graf.get(node).getDistanse()/360000;
        System.out.println((int) Math.floor(timer) + " timer, " + (int) ((timer - Math.floor(timer)) * 100) * 60 / 100 + " minutter, " + (int) ((timer - Math.floor(timer)) * 10000) * 60 / 1000+" sekund");
        while (teller >= -1) {
            teller++;
            if (teller % 20 == 0 || node == startNodeInd)
                System.out.println(Math.toDegrees(graf.get(node).breddegrad) + ", " + Math.toDegrees(graf.get(node).lengdegrad));
            node = graf.get(node).getForgjenger();
            if (node == startNodeInd) teller = -2;
        }
    }

    public void dijkstras(int startNodeInd, int sluttNodeInd) {
        this.startNodeInd = startNodeInd;
        this.sluttNodeInd = sluttNodeInd;


        PriorityQueue<Node> ko = new PriorityQueue<>();

        graf.get(startNodeInd).setDistanse(0);
        ko.add(graf.get(startNodeInd));

        while (ko.size() > 0) {
            Node node = ko.poll();
            if (node.nummer == sluttNodeInd) break;
            ArrayList<int[]> kanter = node.getKanter();
            for (int[] kant : kanter) {
                Node nabo = graf.get(kant[0]);

                double nyDist = node.getDistanse() + kant[1];
                if (nabo.getDistanse() > nyDist) {
                    nabo.setDistanse(nyDist);
                    nabo.setForgjenger(node.nummer);
                    ko.remove(nabo);
                    ko.add(nabo);
                }
            }
        }
    }

    int avstand(Node n1, Node n2) {
        double sin_bredde = Math.sin((n1.breddegrad - n2.breddegrad) / 2.0);
        double sin_lengde = Math.sin((n1.lengdegrad - n2.lengdegrad) / 2.0);
        return (int) (35285538.46153846153846153846 * Math.asin(Math.sqrt(
                sin_bredde * sin_bredde + n1.cosBredde * n2.cosBredde * sin_lengde * sin_lengde)));
    }

    private class EstimatCompare implements Comparator<Node> {

        @Override
        public int compare(Node n1, Node n2) {
            double dist1 = n1.getDistanse() + n1.getEstimat();
            double dist2 = n2.getDistanse() + n2.getEstimat();
            if (dist1 > dist2) return 1;
            else return -1;
        }
    }

    public void aStjerne(int startNodeInd, int sluttNodeInd) {
        this.startNodeInd = startNodeInd;
        this.sluttNodeInd = sluttNodeInd;

        PriorityQueue<Node> ko = new PriorityQueue<>(1000, new EstimatCompare());

        Node sluttNode = graf.get(sluttNodeInd);

        graf.get(startNodeInd).setDistanse(0);
        ko.add(graf.get(startNodeInd));

        while (ko.size() > 0) {
            Node node = ko.poll();
            if (node.nummer == sluttNodeInd) break;
            ArrayList<int[]> kanter = node.getKanter();
            for (int[] kant : kanter) {
                Node nabo = graf.get(kant[0]);

                double nyDist = node.getDistanse() + kant[1];
                if (nabo.getDistanse() > nyDist) {
                    nabo.setDistanse(nyDist);
                    nabo.setForgjenger(node.nummer);
                    ko.remove(nabo);
                    nabo.setEstimat(avstand(node, sluttNode));
                    ko.add(nabo);
                }
            }
        }
    }

    void skrivVei(String path, boolean alle) {
        System.out.println("Kjøretid: " + graf.get(sluttNodeInd).getDistanse()/3600);
        try (BufferedWriter skriver = new BufferedWriter(new FileWriter(path))) {
            if (!alle) {
                int teller = -1;
                int node = sluttNodeInd;
                while (teller >= -1) {
                    teller++;
                    if (teller % 10 == 0 || node == startNodeInd)
                        skriver.write(Math.toDegrees(graf.get(node).breddegrad) + ", " + Math.toDegrees(graf.get(node).lengdegrad) + "\n");
                    node = graf.get(node).getForgjenger();
                    if (node == startNodeInd) teller = -2;
                }
            } else {
                int node = sluttNodeInd;
                while (node != -1) {
                    skriver.write(Math.toDegrees(graf.get(node).breddegrad) + ", " + Math.toDegrees(graf.get(node).lengdegrad) + "\n");
                    node = graf.get(node).getForgjenger();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void resetNoder() {
        for (Node n : graf) {
            n.setDistanse(Double.MAX_VALUE);
            n.setForgjenger(-1);
            n.setEstimat(0);
        }
    }

    void skrivInt(String key){
        punkter.toString();
    }

    void dijkstras(String start, String slutt){
        int sen = punkter.get(start);
        int sto = punkter.get(slutt);

        System.out.println("Start :  " + sen + " Slutt : " + sto);
        dijkstras(punkter.get(start), punkter.get(slutt));
    }

    void aStjerne(String start, String slutt){
        aStjerne(punkter.get(start), punkter.get(slutt));
    }

    public static void main(String[] args) {
        Road nordVei = new Road("/Users/mahmoudibrahim/IdeaProjects/Algoritmer/Øving 13/src/norder.txt", "/Users/mahmoudibrahim/IdeaProjects/Algoritmer/Øving 13/src/kanter.txt", "/Users/mahmoudibrahim/IdeaProjects/Algoritmer/Øving 13/src/interessepkt.txt");

        int start = nordVei.punkter.get("Kabelvåg");  // Retunerer verdien til punktet blir mappet Hafnarfjörður
        int slutt = nordVei.punkter.get("Røros");

        nordVei.resetNoder();
        startTimer();
        nordVei.dijkstras(start,slutt);
        avsluttTimer("Dijkstras ");
        // nordVei.skrivVei("./Output/Dijkstras.txt", false);

        nordVei.resetNoder();
        startTimer();
        nordVei.aStjerne(start,slutt);
        avsluttTimer("A* ");
//        nordVei.skrivVei("./Output/aStjerne.txt", false);
        nordVei.hentVeien();
    }



    private static long tid = 0;

    private static void startTimer() {
        tid = -System.currentTimeMillis();
    }

    private static void avsluttTimer(String beskrivelse) {
        tid += System.currentTimeMillis();
        System.out.println(beskrivelse + " tok " + tid + " millisekund.");
    }

}

class Node implements Comparable<Node>{
    final int nummer;
    double startDelta = 0;

    private double distanse = Double.MAX_VALUE;
    private int forgjenger = -1;
    private int estimat = 0;
    final double breddegrad;
    final double lengdegrad;
    final double cosBredde;
    private ArrayList<int[]> kanter = new ArrayList<>();

    public Node(int nr, Double lengde, Double bredde){
        this.nummer = nr;
        this.breddegrad = bredde * Math.PI / 180;
        this.lengdegrad = lengde * Math.PI / 180;
        this.cosBredde = Math.cos(this.breddegrad);
    }

    public void nyKant(Node tilNode, int kjoretid, int lengde, int fartsgrense) {
        kanter.add(new int[]{tilNode.nummer, kjoretid, lengde, fartsgrense});
    }

    public ArrayList<int[]> getKanter() {
        return kanter;
    }

    public void setForgjenger(int forgjenger) {
        this.forgjenger = forgjenger;
    }

    public int getForgjenger() {
        return forgjenger;
    }

    public void setDistanse(double distanse) {
        this.distanse = distanse;
    }

    public double getDistanse() {
        return distanse;
    }

    public void setEstimat(int estimat) {
        this.estimat = estimat;
    }

    public int getEstimat() {
        return estimat;
    }

    @Override
    public int compareTo(Node n) {
        double dist = n.getDistanse();
        if (this.distanse == dist) return 0;
        else if (this.distanse > dist) return 1;
        else return -1;
    }
}