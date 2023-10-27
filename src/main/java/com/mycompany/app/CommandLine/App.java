package com.mycompany.app.CommandLine;
import java.io.IOException;

import com.mycompany.app.TranslateApi.TranslateApi;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println(TranslateApi.translate("vi","en","anh là con cáo"));
        DictionaryManagement.dictionaryImportFromFile("word_list.txt");
        DictionaryCommandline.dictionaryAdvanced();
    }
}
