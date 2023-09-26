package com.mycompany.app;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URLEncoder;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        DictionaryManagement.dictionaryImportFromFile("word_list.txt");
        DictionaryCommandline.dictionaryAdvanced();

        try {
            String langTo = "en";
            String langFrom = "vi";
            String text = "Xin chào tất cả ae thương mến :)))";
            String urlStr = "https://script.google.com/macros/s/AKfycbxiQVsKyWiGXFDU8LeW-fi9KfS0ZIE01ovCpDUJkbJL0-3R6lw/exec" +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlStr);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("\nResponse: " + responseBody);
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

