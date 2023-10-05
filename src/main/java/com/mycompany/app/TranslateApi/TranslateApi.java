package com.mycompany.app.TranslateApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateApi {
    private int numRequests = 0;
    private static final String TRANSLATE_API_URL = "https://script.google.com/macros/s/AKfycbxiQVsKyWiGXFDU8LeW-fi9KfS0ZIE01ovCpDUJkbJL0-3R6lw/exec";

    public static void main(String[] args) throws IOException {
        String text = "Hello world!";
        // Translated text: Hallo Welt!
        System.out.println("Translated text: " + translate("en", "vi", text));
    }

    public int getNumRequests() {
        return numRequests;
    }

    public static String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = buildTranslationUrl(langFrom, langTo, text);
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

    private static String buildTranslationUrl(String langFrom, String langTo, String text) throws IOException {
        String encodedText = URLEncoder.encode(text, "UTF-8");
        return TRANSLATE_API_URL + "?q=" + encodedText + "&target=" + langTo + "&source=" + langFrom;
    }
}
