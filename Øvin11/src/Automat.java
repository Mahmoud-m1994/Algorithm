class Automat{

    char[] inputAlfabet;
    int[] akseptTilstander;
    int[][] nesteTilstand;

    public Automat(char[] inputAlfabet,int[] akseptTilstander,int[][] nesteTilstand){
        this.inputAlfabet=inputAlfabet;
        this.akseptTilstander=akseptTilstander;
        this.nesteTilstand=nesteTilstand;
    }

    //for å sjekke om alle tegnene i (input) finnes i inputalfabetet
    public boolean finnesIAlfabetet(char[] input){
        boolean ok=false;
        for(int i=0;i<input.length;i++){
            ok=false;
            for (int j=0;j<inputAlfabet.length;j++){
                if(input[i]==inputAlfabet[j]){
                    ok=true;
                }
            }
            if(ok==false) break;
        }
        return ok;
    }
    public boolean sjekkInput(char[] input){
        int currTilstand=0;
        boolean ok=false;
        if(input.length==0) ok=true;
        if(finnesIAlfabetet(input)==false) return false;
        int loop=0;
        while(loop<input.length){
            for(int i=0;i<input.length;i++){
                for (int n=0; n<inputAlfabet.length;n++){
                    if(input[i]==inputAlfabet[n])currTilstand=nesteTilstand[currTilstand][n];
                }
                loop++;
            }
        }

        for (int i=0;i< akseptTilstander.length;i++){
            if (currTilstand==akseptTilstander[i]) ok=true;
        }



        return ok;
    }







    public static void main (String[] args){
        int[][] oppgave8A= new int[4][2];
        oppgave8A[0][0]=1;
        oppgave8A[0][1]=0;
        oppgave8A[1][0]=1;
        oppgave8A[1][1]=2;
        oppgave8A[2][0]=2;
        oppgave8A[2][1]=3;

        Automat automat=new Automat(new char[]{'0', '1'}, new int[]{2},oppgave8A);
        System.out.println(automat.sjekkInput(new char[]{}));
        System.out.println(automat.sjekkInput(new char[]{'0','1','0'}));
        System.out.println(automat.sjekkInput(new char[]{'1','1','1'}));
        System.out.println(automat.sjekkInput(new char[]{'0','1','0','1','1','0'}));
        System.out.println(automat.sjekkInput(new char[]{'0','0','1','0','0','0'}));

        int[][] oppgave8B=new int[5][2];
        oppgave8B[0][0]=1;
        oppgave8B[0][1]=3;
        oppgave8B[1][0]=2;
        oppgave8B[1][1]=4;
        oppgave8B[2][0]=2;
        oppgave8B[2][1]=2;
        oppgave8B[3][0]=4;
        oppgave8B[3][1]=2;
        oppgave8B[4][0]=4;
        oppgave8B[4][1]=4;
        Automat automat2=new Automat(new char[]{'a', 'b'}, new int[]{4},oppgave8B);
        System.out.println("Oppgave8B");
        System.out.println(automat2.sjekkInput(new char[]{'a','b','b','b'}));
        System.out.println(automat2.sjekkInput(new char[]{'a','a','a','b'}));
        System.out.println(automat2.sjekkInput(new char[]{'b','a','b','a','b'}));



    }
}