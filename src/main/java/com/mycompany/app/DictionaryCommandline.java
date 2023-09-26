package com.mycompany.app;

import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandline extends Dictionary {
    private static final Scanner sc = new Scanner(System.in);
    public static void showAllWords() throws IOException {
            System.out.println("No | English    | Vietnamese");
            System.out.println("____________________________");
        for (int i = 0; i < dictionary.size(); i++) {
            int number = i + 1;
            String english = dictionary.get(i).getWord_target();
            String vietnamese = dictionary.get(i).getWord_explain();

            System.out.printf("%-2d | %-10s | %s%n", number, english, vietnamese);
        }
    }
    public static void dictionaryBasic() throws IOException {
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }
    public static void dictionarySearcher() {

    }
    public static void dictionaryAdvanced() throws IOException {
        //insert english and vietnamese words
        DictionaryManagement.dictionaryImportFromFile("word_list.txt");
        //main loop
        System.out.print("Welcome to my application! ");
        while (true) {

            System.out.println(
                    "\n [0] Exit appication " +
                    "\n [1] Add word " +
                    "\n [2] Remove word " +
                    "\n [3] Update word " +
                    "\n [4] Display the whole dictionary " +
                    "\n [5] lookup word" +
                    "\n [6] Search word " +
                    "\n [7] Game " +
                    "\n [8] Import from file" +
                    "\n [9] Export to file" +
                    "\n Your action: ");
            switch (sc.nextInt()){
                case 0 -> {
                    DictionaryManagement.dictionaryExportToFile("word_list.txt");
                    System.exit(0);
                }
                case 1 -> DictionaryManagement.add_word();
                case 2 -> DictionaryManagement.delete_word();
                case 3 -> DictionaryManagement.update_word();
                case 4 -> DictionaryManagement.showAllWords();
                case 5 -> DictionaryManagement.dictionaryLookup();
                case 8 -> DictionaryManagement.dictionaryImportFromFile();
                case 9 -> DictionaryManagement.dictionaryExportToFile();
            }
        }
    }
}
