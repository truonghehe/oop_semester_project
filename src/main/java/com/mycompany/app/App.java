package com.mycompany.app;
import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        DictionaryManagement.dictionaryImportFromFile("word_list.txt");
        DictionaryCommandline.dictionaryAdvanced();
    }
}

