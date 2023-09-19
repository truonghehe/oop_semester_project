package com.mycompany.app;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
        public static ArrayList<Word> Dictionary = new ArrayList<>();
    public static void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int wordAmount = Integer.parseInt(sc.nextLine());
        for ( int i = 0 ; i < wordAmount ; i++) {
            String english_word = sc.nextLine();
            String vietnamese_word = sc.nextLine();

            Dictionary.add(new Word(english_word , vietnamese_word)) ;
        }
    }
}
