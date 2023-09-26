package com.mycompany.app;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
public class App
{
    public static void main( String[] args ) throws IOException
    {
        DictionaryManagement.dictionaryImportFromFile("word_list.txt");
        DictionaryCommandline.dictionaryAdvanced();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = "https://api.dexscreener.com/latest/dex/pairs/ethereum/0x9477460179f0a481c9d30d1af177ee865d54ca49";
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("\nResponse: " + responseBody);
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

