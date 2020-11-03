import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        char[] characters = null;
        int[] weights = null;
        char[] charContent = null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("fileName: ");
        String fileName = scanner.nextLine();
        System.out.println("Option: ");
        System.out.println("1. compress a file: ");
        System.out.println("2. decompress a file: ");
        System.out.println("3. show huffman codes: ");
        int option = scanner.nextInt();
        // get option
        if (option != 2) {

            File file = new File(fileName);
            // handler exception "FileNotFoundException"
            try {
                // A skanner for å få filinnhold Tekst I / O
                Scanner in = new Scanner(file);
                /*  stringbuilder å foretrekke enn
                    legge til en streng mens en streng oppretter
                    en ny forekomst hver gang jeg legger til */
                StringBuilder builder = new StringBuilder();
                // while we haven't reached the end of the stream
                while (in.hasNext()) {
                    // append the line read from file
                    builder.append(in.nextLine());
                }
                // obtain file content as an array of characters

                charContent = builder.toString().toCharArray();
                ArrayList<Character> charactersList = new ArrayList<>();
                ArrayList<Integer> weightsList = new ArrayList<>();
                // to lists to store both distinct character and their weights
                int index;
                for (int i = 0; i < charContent.length; i++) {
                    if ((index = charactersList.indexOf(charContent[i])) >= 0) {
                        // the character exists in the list
                        // increment its weight
                        weightsList.set(index, weightsList.get(index) + 1);
                    } else {
                        // character doesn't exist in the list
                        // add it
                        // and assign weight 1
                        charactersList.add(charContent[i]);
                        weightsList.add(1);
                    }
                }

                characters = new char[charactersList.size()];
                weights = new int[weightsList.size()];

                for (int i = 0; i < charactersList.size(); i++) {

                    characters[i] = charactersList.get(i);
                    weights[i] = weightsList.get(i);
                    System.out.println(characters[i]+"------"+weights[i]);
                    // obtain a fixed size array from the two array lists
                }
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            }
            HuffmanCodeGenerator generator = new HuffmanCodeGenerator();
            // create a generator
            generator.startHuffman(characters, weights);
            // start the huffman coding
            // by giving the characters and their weights
            if (option == 1) {
                generator.komprimer(charContent, fileName + ".komprimert");
            } else {
                // option  3
                generator.printResult(charContent);
            }
        } else {
            //option 2
            HuffmanCodeGenerator generator = new HuffmanCodeGenerator();
            generator.dekomprimer(fileName);
        }

    }
}