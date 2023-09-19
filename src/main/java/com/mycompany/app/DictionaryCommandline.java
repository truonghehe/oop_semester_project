package com.mycompany.app;

import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandline {
    private static Scanner sc = new Scanner(System.in);
    public static void showAllWords() {
            System.out.println("No | English    | Vietnamese");
            System.out.println("____________________________");
        for (int i = 0; i < DictionaryManagement.Dictionary.size(); i++) {
            int number = i + 1;
            String english = DictionaryManagement.Dictionary.get(i).getWord_target();
            String vietnamese = DictionaryManagement.Dictionary.get(i).getWord_explain();

            System.out.printf("%-2d | %-10s | %s%n", number, english, vietnamese);
        }
    }
    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }
    public static void dictionarySearcher() {

    }
    public static void dictionaryAdvanced() throws IOException {
        System.out.println("Welcome to my application! " +
                "\n [0] Exit appication " +
                "\n [1] Add word " +
                "\n [2] Remove word " +
                "\n [3] Update word " +
                "\n [4] Display the whole dictionary " +
                "\n [5] lookup word" +
                "\n [6] Search word " +
                "\n [7] Game " +
                "\n [8] Import from file" +
                "\n [9] Export from file" +
                "\n Your action: ");
        switch (sc.nextInt()){
            case 0 -> System.exit(0);
            case 1 -> DictionaryManagement.add_word();
        }

    }
}
