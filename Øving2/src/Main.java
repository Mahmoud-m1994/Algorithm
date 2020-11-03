import java.util.Random;
import java.lang.*;
class Math{
    public static double finnPotens (double x,int n) {
        double svar= 0;

        if(n==0){
            svar= 1;
        }
        if(n>0){
            svar= x*finnPotens(x,n-1);
        }
        return svar;
    }
    public static double finnPotens2 (double x,int n) {
        double svar= 0;

        if(n==0){
            return  1;
        }
        if(n%2==0){
            svar= finnPotens2(x*x,n/2);
        }
        if(n%2>0){
            svar=x*finnPotens2(x*x,(n-1)/2);
        }
        return svar;
    }
}
class Main {
    public static void main(String[]args){
        long startDate = System.nanoTime();
        double svar1= Math.finnPotens(0.5,1000);
        long endDate = System.nanoTime();

        long startDate2 = System.nanoTime();
        double svar2= Math.finnPotens2(0.5,1000);
        long endDato2 = System.nanoTime();

        System.out.println("Svar1 er"+ svar1 + " tiden ble brukt er " + (endDate-startDate) );
        System.out.println("Svar2 er"+ svar2 + " tiden ble brukt er " + (endDato2-startDate2) );

    }
}
