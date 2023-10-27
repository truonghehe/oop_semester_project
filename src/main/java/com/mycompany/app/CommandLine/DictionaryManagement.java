package com.mycompany.app.CommandLine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class DictionaryManagement extends Dictionary {
    private static final Scanner sc = new Scanner(System.in);

    /**
     * this is for the basic dictionary
     */

    public static void insertFromCommandline() {
        int wordAmount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < wordAmount; i++) {
            String english_word = sc.nextLine();
            String vietnamese_word = sc.nextLine();
            dictionary.put(english_word, new Word(english_word, vietnamese_word));
        }
    }

    /**
     * function 1: add new word to dictionary
     */
    public static void add_word() {
        System.out.println("Enter the english word: ");
        String english_word = sc.nextLine().toLowerCase();
        System.out.println("Enter the word translated to vietnamese: ");
        String vietnamese_word = sc.nextLine().toLowerCase();
        dictionary.put(english_word, new Word(english_word, vietnamese_word));
    }

    /**
     * function 2: delete word from dictionary
     */
    public static void delete_word() {
        System.out.println("Enter the english word: ");
        String word = sc.nextLine().toLowerCase();
        if (dictionary.containsKey(word)) {
            dictionary.remove(word);
        } else {
            System.out.println("This word is not exist");
        }
    }

    /**
     * function 3: update meaning of word
     */
    public static void update_word() {
        System.out.println("you want to change the vietnamese meaning or english meaning? \n (V/E) : ");
        String respond = sc.nextLine().toUpperCase();
        if (respond.equals("V")) {
            System.out.println("Enter the english word: ");
            String word = sc.nextLine().toLowerCase();
            if (!english_word_exist(word)) {
                System.out.println("the word " + word + " doesn't exist in the dictionary");
                return;
            }
            System.out.println("Enter the updated word: ");
            String update = sc.nextLine().toLowerCase();
        } else if (respond.equals("E")) {
            System.out.println("Enter the vietnamese word: ");
            String word = sc.nextLine().toLowerCase();
            if (!vietnamese_word_exist(word)) {
                System.out.println("the word " + word + " doesn't exist in the dictionary");
                return;
            }
            System.out.println("Enter the updated word: ");
            String update = sc.nextLine().toLowerCase();

        } else System.out.println("Unknown command");
    }

    /**
     * function 4: show the whole dictionary in table form
     */
    public static void showAllWords() {
        for (int i = 0; i < dictionary.size(); i++) {
            int number = i + 1;
            String english = dictionary.get(i).getWord_target();
            String vietnamese = dictionary.get(i).getWord_explain();
            System.out.printf("%-2d | %-10s | %s%n", number, english, vietnamese);
        }
    }

    public static void dictionaryImportFromFile(String path) throws FileNotFoundException {
        FileReader reader = new FileReader(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tmp = line.split("<html>");
                String ss = tmp[0];
                Word word = new Word(ss, "<html>" + tmp[1]);
                dictionary.put(ss, word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dictionaryImportFromFile() throws FileNotFoundException {
        String path = sc.nextLine();
        dictionaryImportFromFile(path);
    }

    /**
     * function 9: export Word array to "word_list.txt" file
     */
    public static void dictionaryExportToFile(String path) {

    }

    public static void dictionaryExportToFile() {
    }

    /**
     * assisting functions: checking if the english/vietnamese word exist in the word array
     *
     * @param word_checking the word being checked
     * @return true if the word exist,  false otherwise
     */
    private static boolean english_word_exist(String word_checking) {
        return false;
    }

    private static boolean vietnamese_word_exist(String word_checking) {
        return false;
    }
}
