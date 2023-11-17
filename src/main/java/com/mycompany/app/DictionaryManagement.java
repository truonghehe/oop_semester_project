package com.mycompany.app;
import java.io.*;
import java.util.*;


public class DictionaryManagement extends Dictionary {
    private static final Scanner sc = new Scanner(System.in);
    private static final String SPLITTING_CHARACTERS = "<html>";
    public static Map<String,Word> data = new HashMap<>();
    /**
     * this is for the basic dictionary.
     */
    public static void insertFromCommandline() {
        int wordAmount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < wordAmount; i++) {
            String english_word = sc.nextLine();
            String vietnamese_word = sc.nextLine();
            dictionary.add(new Word(english_word, vietnamese_word));
        }
    }
    public static void setMap(){
        for ( int i = 0 ; i < dictionary.size() ; i++){
            String englishWord = dictionary.get(i).getWord_target();
            data.put(englishWord,dictionary.get(i));
        }
    }
    /**
     * function 1: add new word to dictionary.
     */
    public static void add_word() {
        System.out.println("Enter the english word: ");
        String english_word = sc.nextLine().toLowerCase();
        System.out.println("Enter the word translated to vietnamese: ");
        String vietnamese_word = sc.nextLine().toLowerCase();
        dictionary.add(new Word(english_word, vietnamese_word));
    }

    /**
     * function 2: delete word from dictionary.
     */
    public static void delete_word() {
        System.out.println("Enter the english word: ");
        String word = sc.nextLine().toLowerCase();
        if (english_word_exist(word) || vietnamese_word_exist(word) ) {
            for (int i = 0; i < dictionary.size(); i++) {
                if (word.equals(dictionary.get(i).getWord_target())) {
                    dictionary.remove(i);
                    break;
                }
            }
            System.out.println(word + " has been removed!");
        } else System.out.println("word doesn't exist");
    }

    /**
     * function 3: update meaning of word.
     */
    public static void update_word() {
        System.out.println("you want to change the vietnamese meaning or english meaning? \n (V/E) : ");
        String respond = sc.nextLine().toUpperCase();
        if (respond.equals("V")) {
            System.out.println("Enter the english word: ");
            String word = sc.nextLine().toLowerCase();
            if ( !english_word_exist(word) ) {
                System.out.println("the word " + word + " doesn't exist in the dictionary");
                return;
            }
            System.out.println("Enter the updated word: ");
            String update = sc.nextLine().toLowerCase();
            for (Word value : dictionary) {
                if (word.equals(value.getWord_target())) {
                    value.setWord_explain(update);
                    System.out.println("the meaning of " + word + " has been updated to " + update);
                }
            }
        }
        else if (respond.equals("E")) {
            System.out.println("Enter the vietnamese word: ");
            String word = sc.nextLine().toLowerCase();
            if ( !vietnamese_word_exist(word) ) {
                System.out.println("the word " + word + " doesn't exist in the dictionary");
                return;
            }
            System.out.println("Enter the updated word: ");
            String update = sc.nextLine().toLowerCase();
            for (Word value : dictionary) {
                if (word.equals(value.getWord_explain())) value.setWord_target(update);
                System.out.println("the meaning of " + word + " has been updated to " + update);
            }
        }
        else System.out.println("Unknown command");
    }

    /**
     * function 4: show the whole dictionary in table form.
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
     * function 5: look up for the whole word.
     */
    public static void dictionaryLookup () {
        System.out.println("enter the word you are looking for: ");
        String word = sc.nextLine().toLowerCase();
        if (english_word_exist(word)) {
            for (Word value : dictionary) {
                if (value.getWord_target().equals(word)) {
                    System.out.println(value.getWord_target() + "\t" + value.getWord_explain());
                    break;
                }
            }
            for (Word value : dictionary ) {
                if (value.getWord_explain().equals(word)) {
                    System.out.println(value.getWord_target() + "\t" + value.getWord_explain());
                    break;
                }
            }
        }
        else {
            System.out.println("Word not exist");
        }
    }

    /**
     * function 6: search for word.
     */
    public static void search() {
        System.out.println("what word are you searching: ");
        while (true) {
            String word = sc.next();
            for (int i = 0; i < dictionary.size(); i++) {
                if (dictionary.get(i).getWord_target().equals(word)) {
                    System.out.println("word meaning: " + dictionary.get(i).getWord_explain());
                    return;
                }
                if (dictionary.get(i).getWord_target().contains(word)) {
                    System.out.print(dictionary.get(i).getWord_target() + " ");
                }
            }
        }
    }

    /**
     * function 8: insert words from file "word_list.tt" to word array.
     */
    public static void dictionaryImportFromFile (String path) throws IOException {
        FileReader fis = new FileReader(path);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            System.out.println(line);
            String word = parts[0];
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, definition);
            dictionary.add(wordObj);
        }
    }
    public static void dictionaryImportFromFile () throws IOException {
        Scanner input = new Scanner(System.in) ;
        String path = input.nextLine() ;
        dictionaryImportFromFile(path);
    }
    /**
     * function 9: export Word array to "word_list.txt" file.
     */
    public static void dictionaryExportToFile (String path) {
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
     * assisting functions: checking if the english/vietnamese word exist in the word array.
     * @param word_checking the word being checked
     * @return true if the word exist,  false otherwise
     */private static boolean english_word_exist (String word_checking ){
        for (Word word : dictionary) {
            if (word_checking.equals(word.getWord_target())) return true;
        }
        return false;
    }
    private static boolean vietnamese_word_exist (String word_checking ){
        for (Word word : dictionary) {
            if (word_checking.equals(word.getWord_explain())) return true;
        }
        return false;
    }

}
