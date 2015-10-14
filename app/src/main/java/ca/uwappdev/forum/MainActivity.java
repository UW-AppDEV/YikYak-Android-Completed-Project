package ca.uwappdev.forum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private List<ParseObject> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        arrayList = new ArrayList<String>();
        arrayList.add("thread1");
        arrayList.add("thread2");
        arrayList.add("thread3");
        arrayList.add("thread4");
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String threadId = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, Thread.class);
                intent.putExtra(threadId, threadId);
                startActivity(intent);
            }
        });
//        Parse.enableLocalDatastore(this);
//        Parse.initialize(this, "dbSRgndFtVlkxJUG1AcubhVaP814yfO0HAqrEjaU", "LWqPDlBCoLfhG2bG7ZvjKdNpvcbl8JARkLHcaoWf");
//        populateMessages();
    }

    public void addItem(String text) {
        arrayList.add(text);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_thread) {
            Intent intent = new Intent(this, NewThread.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void populateMessages() {
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Thread");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> messages, ParseException e) {
//                for (ParseObject message: messages) {
//                    addItem(message.getString("title"));
//                }
//            }
//        });
//    }
}
