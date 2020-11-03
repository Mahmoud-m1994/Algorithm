public class Math_Task3 {
    double[] Xverdi ;
    double[] sannsynlighet ;

    public Math_Task3(double [] Xverdi, double [] sannsynlighet){
        this.Xverdi=Xverdi;
        this.sannsynlighet= sannsynlighet;
    }

    public double forventningen(){
        double svar=0;
        for (int i=0; i<Xverdi.length;i++){
            svar +=Xverdi[i]*sannsynlighet[i];
        }
        return svar;
    }
    public double varians(){
        double svar=0;

        for (int i=0; i<Xverdi.length;i++){

            svar += Math.pow(Xverdi[i],2)*sannsynlighet[i];
        }
        svar-=Math.pow(forventningen(),2);
        return svar;
    }
    public double standardavviket(){
        return Math.sqrt(varians());
    }
    public double [] fordelingsfunksjonen( ){
        double [] fordeling= new double[Xverdi.length];
        fordeling[0]= sannsynlighet[0];  // p(X<=x ) er alltid lik p(X=x) altså sannsynligheten
        for(int i=1; i<sannsynlighet.length;i++){
            fordeling[i]+= fordeling[i]+sannsynlighet[i-1];
        }
        return fordeling;
    }
}


class Test{
    public static void main(String[] args){
        double [] tabell1=  {0,0.3,0.5,0.6,0.7,0.8,1.0,1.2,1.6};
        double [] tabell2=  {1.0/28.0,2.0/28.0,5.0/28.0,8.0/28.0,5.0/28.0,3.0/28.0,2.0/28.0,3.0/56.0,1.0/56.0};
        Math_Task3 math_task3= new Math_Task3(tabell1,tabell2);


        System.out.println("forventningen er "+math_task3.forventningen());
        System.out.println("Varians "+math_task3.varians());
        System.out.println("standardviket "+math_task3.standardavviket());
        double[] fordeling=math_task3.fordelingsfunksjonen();
        System.out.println("Verdiene     Sannsynlighet          fordeling");
        for(int i=0;i<fordeling.length;i++){
            System.out.println(tabell1[i]+"          "+tabell2[i]+"     "+fordeling[i]);
        }
    }
}