package com.mycompany.app;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class DictionaryManagement extends Dictionary{
    private static final Scanner sc = new Scanner(System.in);
    public static void insertFromCommandline() {
        int wordAmount = Integer.parseInt(sc.nextLine());
        for ( int i = 0 ; i < wordAmount ; i++) {
            String english_word = sc.nextLine();
            english_word.toLowerCase() ;
            String vietnamese_word = sc.nextLine();
            vietnamese_word.toLowerCase();
            dictionary.add(new Word(english_word , vietnamese_word)) ;
        }
    }
    public static void dictionaryImportFromFile(String path)  {
        try  {
            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "" ;
            while ((line = bufferedReader.readLine()) != null) {
                // tach dong ra lam 2 string
                String[] word_ = line.split("\\s+", 2);
                if ( !word_exist(word_[0])) {
                    Word word = new Word(word_[0], word_[1]);
                    dictionary.add(word);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void dictionaryImportFromFile() throws IOException{
        System.out.println("Please enter path of file: ") ;
        String path = sc.nextLine() ;
        dictionaryImportFromFile(path);
    }
    public static void dictionaryLookup() {
        String word = sc.nextLine() ;
        if (word_exist(word)){
            for (Word value : dictionary) {
                if (value.getWord_target().equals(word)) {
                    System.out.println(value.getWord_explain());
                }
            }
        }
        else {
            System.out.println("Word not exist") ;
        }
    }
    public static void dictionaryExportToFile(String path) throws IOException {
        try {
            FileWriter writer = new FileWriter(path) ;
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            String word = "" ;
            for (Word value : dictionary) {
                word += value.getWord_target() + "    " + value.getWord_explain() + "\n";
            }
            bufferedWriter.write(word);
            bufferedWriter.close() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void dictionaryExportToFile() throws IOException {
        System.out.println("Please enter path of file: ") ;
        String path = sc.nextLine() ;
        dictionaryExportToFile(path);
    }
    public static void delete_word() {
        System.out.println("Enter the english word: ");
        String word = sc.nextLine();
        word.toLowerCase() ;
        if (word_exist(word)) {
            for (int i = 0; i < dictionary.size(); i++) {
                if (word.equals(dictionary.get(i).getWord_target())) dictionary.remove(i);
            }
        }
        else {
            System.out.println("Action not supported" );
        }
    }
    public static void add_word() throws IOException {
        System.out.println("Enter the english word: ");
        String english_word = sc.nextLine();
        System.out.println("Enter the word translated to vietnamese: ");
        String vietnamese_word = sc.nextLine();
        dictionary.add(new Word(english_word.toLowerCase(),vietnamese_word.toLowerCase()));
    }
    public static void update_word() {
        System.out.println("Enter the english word: ");
        String word = sc.nextLine();
        if ( word_exist(word.toLowerCase())) {
            System.out.println("Enter the update: ");
            String update = sc.nextLine() ;
            update.toLowerCase() ;
            for (Word value : dictionary) {
                if (word.equals(value.getWord_target())) value.setWord_explain(update.toLowerCase());
            }
        }
        else {
            System.out.println("Word not exist");
        }
    }
    private static boolean word_exist (String word_checking ) {
        for (Word word : dictionary) {
            if (word_checking.equals(word.getWord_target())) return true;
        }
        return false;
    }
}
