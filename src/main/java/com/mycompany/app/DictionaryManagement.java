package com.mycompany.app;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;

public class DictionaryManagement {
        public static ArrayList<Word> Dictionary = new ArrayList<>();
        private static Scanner sc = new Scanner(System.in);
        private static FileWriter writer;

    static {
        try {
            writer = new FileWriter("/Users/chuongdz/Desktop/oop/oop_semester_project/word_list.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertFromCommandline() {
        int wordAmount = Integer.parseInt(sc.nextLine());
        for ( int i = 0 ; i < wordAmount ; i++) {
            String english_word = sc.nextLine();
            String vietnamese_word = sc.nextLine();

            Dictionary.add(new Word(english_word , vietnamese_word)) ;
        }
    }
    public static void insertFromFile() {

    }
    public static void dictionaryLookup() {

    }
    public static void dictionaryExportToFile(String word) throws IOException {

        writer.write(word);
    }
    public static void delete_word() {

    }
    public static void add_word() throws IOException {
        System.out.println("enter the english word: ");
        String english_word = sc.nextLine();
        System.out.println("enter the word translated to vietnamese: ");
        String vietnamese_word = sc.nextLine();
        if ( !word_exist(english_word) ) dictionaryExportToFile(english_word + vietnamese_word);
    }
    private static boolean word_exist (String word_checking ) {
        if ( word_checking.charAt(0) <= 'Z' && word_checking.charAt(0) >= 'A') {
            word_checking.replace(word_checking.charAt(0) , (char) (word_checking.charAt(0) - 'A' + 'a'));
        }
        for ( int i = 0 ; i < Dictionary.size() ; i++) {
            if ( word_checking == Dictionary.get(i).getWord_target()) return true;
        }
        return false;
    }
}
