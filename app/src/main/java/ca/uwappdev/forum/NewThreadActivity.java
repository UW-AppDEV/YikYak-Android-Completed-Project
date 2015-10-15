package ca.uwappdev.forum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;

public class NewThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText title = (EditText) findViewById(R.id.thread_title);
        final EditText content = (EditText) findViewById(R.id.thread_content);
        Button createButton = (Button) findViewById(R.id.create_thread_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = title.getText().toString();
                String contentText = content.getText().toString();

                if (titleText.isEmpty() || contentText.isEmpty()) {
                    Snackbar.make(v, "Please fill out all of the fields", Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    ParseObject thread = new ParseObject("Thread");
                    thread.put("title", titleText);
                    thread.put("content", contentText);
                    thread.saveInBackground();
                    finish();
                }
            }
        });
    }

}
