package com.mycompany.app.TranslateApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateApi {
    private int num_request = 0;
    public static void main(String[] args) throws IOException {
        String text = "Hello world!";
//        Translated text: Hallo Welt!
//        System.out.println("Translated text: " + translate("en", "vi", text));
    }

    public int getNum_request() {
        return num_request;
    }
    
    public String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        num_request += 1;
        String urlStr = "https://script.google.com/macros/s/AKfycbxiQVsKyWiGXFDU8LeW-fi9KfS0ZIE01ovCpDUJkbJL0-3R6lw/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        System.out.println(urlStr);
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response.toString();
    }

}