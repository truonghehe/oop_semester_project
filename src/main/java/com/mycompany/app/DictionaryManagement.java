package com.mycompany.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The DictionaryManagement class provides management functions for a dictionary, including adding, deleting, updating,
 * and exporting words, as well as importing words from a file.
 */
public class DictionaryManagement extends Dictionary {

    private static final Scanner sc = new Scanner(System.in);
    private static final String SPLITTING_CHARACTERS = "<html>";
    public static Map<String, Word> data = new HashMap<>();

    /**
     * Inserts words from the command line into the dictionary.
     */
    public static void insertFromCommandline() {
        int wordAmount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < wordAmount; i++) {
            String english_word = sc.nextLine();
            String vietnamese_word = sc.nextLine();
            dictionary.add(new Word(english_word, vietnamese_word));
        }
    }

    /**
     * Sets up a map with English words as keys and corresponding Word objects as values.
     */
    public static void setMap() {
        for (int i = 0; i < dictionary.size(); i++) {
            String englishWord = dictionary.get(i).getWord_target();
            data.put(englishWord, dictionary.get(i));
        }
    }

    /**
     * Adds a new word to the dictionary.
     */
    public static void add_word() {
        System.out.println("Enter the English word: ");
        String english_word = sc.nextLine().toLowerCase();
        System.out.println("Enter the word translated to Vietnamese: ");
        String vietnamese_word = sc.nextLine().toLowerCase();
        dictionary.add(new Word(english_word, vietnamese_word));
    }

    /**
     * Deletes a word from the dictionary.
     */
    public static void delete_word() {
        System.out.println("Enter the English word: ");
        String word = sc.nextLine().toLowerCase();
        if (english_word_exist(word) || vietnamese_word_exist(word)) {
            for (int i = 0; i < dictionary.size(); i++) {
                if (word.equals(dictionary.get(i).getWord_target())) {
                    dictionary.remove(i);
                    break;
                }
            }
            System.out.println(word + " has been removed!");
        } else System.out.println("Word doesn't exist");
    }

    /**
     * Updates the meaning of a word in the dictionary.
     */
    public static void update_word() {
        System.out.println("Do you want to change the Vietnamese meaning or English meaning? (V/E): ");
        String respond = sc.nextLine().toUpperCase();
        if (respond.equals("V")) {
            System.out.println("Enter the English word: ");
            String word = sc.nextLine().toLowerCase();
            if (!english_word_exist(word)) {
                System.out.println("The word " + word + " doesn't exist in the dictionary");
                return;
            }
            System.out.println("Enter the updated word: ");
            String update = sc.nextLine().toLowerCase();
            for (Word value : dictionary) {
                if (word.equals(value.getWord_target())) {
                    value.setWord_explain(update);
                    System.out.println("The meaning of " + word + " has been updated to " + update);
                }
            }
        } else if (respond.equals("E")) {
            System.out.println("Enter the Vietnamese word: ");
            String word = sc.nextLine().toLowerCase();
            if (!vietnamese_word_exist(word)) {
                System.out.println("The word " + word + " doesn't exist in the dictionary");
                return;
            }
            System.out.println("Enter the updated word: ");
            String update = sc.nextLine().toLowerCase();
            for (Word value : dictionary) {
                if (word.equals(value.getWord_explain())) value.setWord_target(update);
                System.out.println("The meaning of " + word + " has been updated to " + update);
            }
        } else System.out.println("Unknown command");
    }

    /**
     * Displays the whole dictionary in table form.
     */
    public static void showAllWords() {
        System.out.println("No | English    | Vietnamese");
        System.out.println("____________________________");
        for (int i = 0; i < dictionary.size(); i++) {
            int number = i + 1;
            String english = dictionary.get(i).getWord_target();
            String vietnamese = dictionary.get(i).getWord_explain();

            System.out.printf("%-2d | %-10s | %s%n", number, english, vietnamese);
        }
    }

    /**
     * Looks up a word in the dictionary and displays its English and Vietnamese meanings.
     */
    public static void dictionaryLookup() {
        System.out.println("Enter the word you are looking for: ");
        String word = sc.nextLine().toLowerCase();
        if (english_word_exist(word)) {
            for (Word value : dictionary) {
                if (value.getWord_target().equals(word)) {
                    System.out.println(value.getWord_target() + "\t" + value.getWord_explain());
                    break;
                }
            }
            for (Word value : dictionary) {
                if (value.getWord_explain().equals(word)) {
                    System.out.println(value.getWord_target() + "\t" + value.getWord_explain());
                    break;
                }
            }
        } else {
            System.out.println("Word does not exist");
        }
    }

    /**
     * Searches for words in the dictionary based on user input.
     */
    public static void search() {
        System.out.println("What word are you searching for: ");
        while (true) {
            String word = sc.next();
            for (int i = 0; i < dictionary.size(); i++) {
                if (dictionary.get(i).getWord_target().equals(word)) {
                    System.out.println("Word meaning: " + dictionary.get(i).getWord_explain());
                    return;
                }
                if (dictionary.get(i).getWord_target().contains(word)) {
                    System.out.print(dictionary.get(i).getWord_target() + " ");
                }
            }
        }
    }

    /**
     * Imports words from a file into the dictionary.
     *
     * @param path the path to the file containing words
     * @throws IOException if an I/O error occurs
     */
    public static void dictionaryImportFromFile(String path) throws IOException {
        FileReader fis = new FileReader(path);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, definition);
            dictionary.add(wordObj);
        }
    }

    /**
     * Imports words from a file into the dictionary, prompting the user for the file path.
     *
     * @throws IOException if an I/O error occurs
     */
    public static void dictionaryImportFromFile() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the path to the file: ");
        String path = input.nextLine();
        dictionaryImportFromFile(path);
    }

    /**
     * Exports words from the dictionary to a file.
     *
     * @param path the path to the file to export words to
     */
    public static void dictionaryExportToFile(String path) {
        try (FileWriter writer = new FileWriter(path);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (Word value : dictionary) {
                String word = value.getWord_target() + value.getWord_explain() + "\n";
                bufferedWriter.write(word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if an English word exists in the dictionary.
     *
     * @param word_checking the word being checked
     * @return true if the word exists, false otherwise
     */
    private static boolean english_word_exist(String word_checking) {
        for (Word word : dictionary) {
            if (word_checking.equals(word.getWord_target())) return true;
        }
        return false;
    }

    /**
     * Checks if a Vietnamese word exists in the dictionary.
     *
     * @param word_checking the word being checked
     * @return true if the word exists, false otherwise
     */
    private static boolean vietnamese_word_exist(String word_checking) {
        for (Word word : dictionary) {
            if (word_checking.equals(word.getWord_explain())) return true;
        }
        return false;
    }
}
