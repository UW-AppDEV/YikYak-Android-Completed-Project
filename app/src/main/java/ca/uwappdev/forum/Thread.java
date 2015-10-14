package ca.uwappdev.forum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Intent;
import android.widget.TextView;

public class Thread extends AppCompatActivity {
    public final static String threadId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String threadId = intent.getStringExtra(Thread.threadId);
        TextView threadTitle = (TextView) findViewById(R.id.textView);
        getSupportActionBar().setTitle("Thread");
        threadTitle.setText("Mock thread title");

    }

}
