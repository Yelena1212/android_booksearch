//Yelena Baranchuk

package com.codepath.android.booksearch.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.models.Book;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Fetch views
        ivBookCover = findViewById(R.id.ivBookCover);
        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);

        // Extract book object from intent extras
        Book book = getIntent().getParcelableExtra("book");
        if (book != null) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            // Use Glide to load the cover image
            Glide.with(this).load(book.getCoverUrl()).into(ivBookCover);
        }

        Toolbar toolbar = findViewById(R.id.toolbar); // Make sure you have a Toolbar with the id 'toolbar'
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        // Checkpoint #6
        // Add Share Intent
        // see http://guides.codepath.org/android/Sharing-Content-with-Intents#shareactionprovider
        // (Bonus) Share book title and cover image using the same intent.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                // This ID represents the Home or Up button; in the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
