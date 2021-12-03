package com.ochando.menugithunter.utis;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    final static String QUERY_PARAM = "q";
    final static String SORT_PARAM = "sort";
    final static String SORT_BY = "starts";

    public static URL buildUrl(String githubSearchQuery) throws MalformedURLException {
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM,githubSearchQuery)
                        .appendQueryParameter(SORT_PARAM,SORT_BY)
                        .build();

        URL url = null;
        url = new URL(builtUri.toString());

        return  url;
    }

    public static String getResponseFromHttpURl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream in = urlConnection.getInputStream();

        Scanner sc = new Scanner(in);
        sc.useDelimiter("\\A");
        try {
            boolean hasInput = sc.hasNext();

            if (hasInput) {
                return sc.next();
            } else {
                return null;
            }

        } finally {
             urlConnection.disconnect();
        }
    }

}
