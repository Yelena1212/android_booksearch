package com.codepath.android.booksearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.adapters.BookAdapter;
import com.codepath.android.booksearch.models.Book;
import com.codepath.android.booksearch.services.BookService; // Ensure this is the correct path
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import okhttp3.Headers;

public class BookListActivity extends AppCompatActivity {
    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private ArrayList<Book> abooks;
    private BookService bookService; // Declare your BookService
    private EditText etSearch;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        rvBooks = findViewById(R.id.rvBooks);
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        abooks = new ArrayList<>();
        bookAdapter = new BookAdapter(this, abooks);
        rvBooks.setAdapter(bookAdapter);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        bookService = new BookService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book Search App");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearch.getText().toString().trim();
                if (!query.isEmpty()) {
                    searchBooks(query);
                } else {
                    Toast.makeText(BookListActivity.this, "Please enter a search term.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchBooks(String query) {
        bookService.searchBooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray docs = json.jsonObject.getJSONArray("docs");
                    ArrayList<Book> books = Book.fromJson(docs);
                    abooks.clear();
                    abooks.addAll(books);
                    bookAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("BookListActivity", "Failed to parse books", e);
                    Toast.makeText(BookListActivity.this, "Error fetching books", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String responseString, Throwable throwable) {
                Log.e("BookListActivity", "Book search failed", throwable);
                Toast.makeText(BookListActivity.this, "Failed to fetch books", Toast.LENGTH_SHORT).show();
            }
        });

        bookAdapter.setOnItemClickListener((itemView, position) -> {
            Book selectedBook = abooks.get(position);
            Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
            intent.putExtra("book", selectedBook);
            startActivity(intent);
        });

    }
}
