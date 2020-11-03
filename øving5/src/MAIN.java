import java.util.Arrays;

class SimpleHashTable{

    //I declared an array of 10 linkedLists of type node
    static private Node [] hashTable  = new Node[10];

    public static void  main(String args[]){
        test();
        System.out.println(Arrays.toString(hashTable));
    }

    static class Node{
        String key;
        String val;
        Node next;
    }
    // method for entering a key and value into the hash table
    static void put(String key, String value){
        int location = Math.abs(key.hashCode())%10;
        System.out.println("location value in put is " + location);
        //every key string would give me its unique array index
        Node newNode;
        newNode = new Node();
        newNode.key = key;
        newNode.val = value;
        Node runner; // the runner will go throught the linked list wothout loosing the head
        runner = hashTable[location];
        if (hashTable[location] == null){//the linked list is still empty and this is the first entry
            hashTable[location] = newNode;
        }
        else {
            while (runner!=null){
                runner = runner.next;
            }
            runner = new Node();
            runner.key=key;
            runner.val = value;
        }
    }
    //end of put() method

    static void test(){
        put ("sammy", "0547723878");
        put ("kassoum","046743491");
        System.out.println(get("sammy"));
        System.out.println(get("kassoum"));
    }

    static String get(String key){
        Integer location = Math.abs(key.hashCode())%10;
        Node runner;
        runner = hashTable[location];
        while (runner!=null){
            if (runner.key.equals(key))
                return runner.val;
            runner = runner.next;
        }
        return null;
    }

}