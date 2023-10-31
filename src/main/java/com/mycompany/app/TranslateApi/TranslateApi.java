package com.mycompany.app.TranslateApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateApi {
    private static final String TRANSLATE_API_URL = "https://script.google.com/macros/s/AKfycbxiQVsKyWiGXFDU8LeW-fi9KfS0ZIE01ovCpDUJkbJL0-3R6lw/exec";
    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     * Translate function interact to Google API
     * @param langFrom
     * @param langTo
     * @param text
     * @return
     * @throws IOException
     */
    public static String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = buildTranslationUrl(langFrom, langTo, text);
        try {
            URL url = new URL(urlStr);
            StringBuilder response = new StringBuilder();

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", USER_AGENT);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            return response.toString();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Base URL for Google API call
     * @param langFrom language want to translate
     * @param langTo wanting language
     * @param text the text want to translate
     * @return the text in wanting language
     * @throws IOException throws exception if occur error
     */
    private static String buildTranslationUrl(String langFrom, String langTo, String text) throws IOException {
        String encodedText = URLEncoder.encode(text, "UTF-8");
        String from, to;
        if (langFrom.equals("English")) {
            from = "en";
        } else {
            from = "vi";
        }
        if (langTo.equals("English")) {
            to = "en";
        } else {
            to = "vi";
        }
        return TRANSLATE_API_URL + "?q=" + encodedText + "&target=" + to + "&source=" + from;
    }
}
