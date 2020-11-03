import java.util.*;
public class Task1 {
    public static void main(String[]args){
        int startverdi=50;
        int kjøpDag=0;
        int selgDag=0;
        int maksForskjell=-10;

        int [] kursForandring= new int[10];
        int [] prisen = new int[10];

        //random tabell for kursforandring
        for (int i=0;i<kursForandring.length;i++){
                kursForandring[i]= (int)((Math.random()*10)-7);
                System.out.println("Kurs forandring er"+ kursForandring[i]);
        }

        // Henter prisen hverdag basert på kursforandring
        prisen[0]=startverdi+kursForandring[0];
        System.out.println("Kurs startVerdi er"+ prisen[0]);
        //setter første dags verdi
        for (int i=1;i<kursForandring.length;i++){
            prisen[i]= prisen[i-1]+kursForandring[i];
            System.out.println("Kurs verdi er"+ prisen[i]);

        }

        // Finner best dag til å kjøpe/selge
        long startDato1 = System.nanoTime();

        for(int i=1;i<prisen.length;i++){
            for(int j=i+1;j<prisen.length;j++){
                if(prisen[j]-prisen[i]>maksForskjell){
                    maksForskjell=prisen[j]-prisen[i];
                    kjøpDag=i;
                    selgDag=j;
                    System.out.println("KJøpedag er "+kjøpDag+" og selgedag er " +selgDag);
                }
            }
        }
        long endDato1 = System.nanoTime();

        System.out.println("Kjøp dag nr " + kjøpDag + ", og selg dag nr " + selgDag+" forskjellen er "+ (prisen[selgDag]- prisen[kjøpDag]));
        System.out.println(endDato1-startDato1 + " nanoseconds");
    }
}
