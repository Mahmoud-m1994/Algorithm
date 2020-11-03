import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[]args) {
        SorteringOving quicksort = new SorteringOving();
        int[] ranArr = quicksort.randomArr(10);
        //int[] test = {0,5,4,7,9,2,1,4,10};
        int sumBefore= IntStream.of(ranArr).sum();
        int rounds = 0;
        double time;

        Date start = new Date();
        quicksort.quickSort(ranArr, 0, ranArr.length-1);
        Date end = new Date();
        rounds++;
        //quicksort.printArray(ranArr);

        time = (double)(end.getTime()-start.getTime())/rounds;
        for (int i=0; i<ranArr.length-2;i++) {
            if (ranArr[i + 1] < ranArr[i]) {
                System.out.println("the array is not sorted in "+ranArr[i+1]+" mindre enn"+ranArr[i]);
            }
        }
        int sumAfter= IntStream.of(ranArr).sum();
        if(sumAfter!=sumBefore){
            System.out.println("Summene er ikke like");
        }

        System.out.println("Milliseconds per round: " + time );
    }
}

class SorteringOving{
    public  void quickSort( int [] t, int v , int h){
        if (h-v>1000){
            int delepos = splitt(t,v,h);
            quickSort(t,v,delepos-1);
            quickSort(t,delepos+1,h);
        }
        else
            shellSort(t,v,h);
    }
    public static int splitt( int [] t, int v, int h){
        int iv;
        int ih;
        int m= medianSort(t,v,h);
        int dv = t[m];
        bytt(t,m,h-1);
        for (iv=v,ih=h-1;;){
            while (t[++iv] < dv);
            while (t[--ih]> dv);
            if (iv >= ih)
                break;
            bytt(t,iv,ih);
        }
        bytt(t,iv,h-1);
        return iv;
    }
    public void shellSort(int table[],int fra, int til){
        if( til<=fra)
            return;
        int s = (til-fra) / 2;
        while (s>0){
            for (int i=s+fra;i<til+1;i++){
                int j=i;
                int flytt = table[i];
                while (j>=fra+s && flytt<table[j-s]){
                    table[j]= table[j-s];
                    j -=s;
                }
                table[j]=flytt;
            }
            s = (s==2 )?1: (int) (s/2.2);
        }
    }

    public static int medianSort(int []t,int v, int h){
        int m =(v+h) /2;
        if(t[v]>t[m])
            bytt( t,v,m);
        if(t[m]>t[h]) {
            bytt( t,m,h);
            if (t[v] > t[m])
                bytt(t,v,m);
        }
        return m;
    }
    public static void bytt( int [] t ,int i , int j){
        int hjelp= t[j];
        t[j]=t[i];
        t[i]=hjelp;
    }

    public int[] randomArr(int size) {
        int[] retArr = new int[size];
        for(int i = 0; i < size; i++) {
            retArr[i] = ThreadLocalRandom.current().nextInt(1,100+1);
        }
        return retArr;
    }
    public void printArray(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.println(arr[i] + ", ");
        }
    }
}
