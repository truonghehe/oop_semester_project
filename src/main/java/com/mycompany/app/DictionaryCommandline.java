package com.mycompany.app;

public class DictionaryCommandline {
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
}
