package ca.uwappdev.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.thread_list_item, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String threadId = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ThreadActivity.class);
                intent.putExtra(threadId, threadId);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewThreadActivity.class);
                startActivity(intent);
            }
        });

        populateMessages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateMessages();
    }

    public void populateMessages() {
        ParseQuery<ParseObject> query = new ParseQuery<>("Thread");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> messages, ParseException e) {
                arrayList.clear();
                for (ParseObject message : messages) {
                    arrayList.add(message.getString("title"));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
