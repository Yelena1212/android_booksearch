package com.codepath.android.booksearch.services;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

public class BookService {
    private static final String BASE_URL = "https://openlibrary.org/search.json?q=";

    public void searchBooks(String query, JsonHttpResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = BASE_URL + query;
        client.get(url, handler);
    }
}
