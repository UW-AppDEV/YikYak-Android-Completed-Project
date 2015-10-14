#Building a basic discussion forum app

## Dev Environment
Download and install android studio and android SDK

## Getting Started
1. Open android studio, click start new project.
2. Set minimum target API to 4.0.3 (API 15)
3. Choose Blank Activity

## Setting up views
### Main View
1. Declare some class properties in MainActivity

		private ListView listView;
		private ArrayAdapter<String> adapter;
		private ArrayList<String> arrayList;

2. Add a mock list to onCreate in MainActivity

		listView = (ListView) findViewById(R.id.listView);
		arrayList = new ArrayList<String>();
		arrayList.add("thread1");
		arrayList.add("thread2");
		arrayList.add("thread3");
		arrayList.add("thread4");
		adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
		listView.setAdapter(adapter);

3. Make a add item to list function

		public void addItem(String text) {
			arrayList.add(text);
			adapter.notifyDataSetChanged();
		}

### Thread View
1. Create new activity Thread, add behaviour to ListView in MainActivity:

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String threadId = (String) parent.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this, Thread.class);
				intent.putExtra(threadId, threadId);
				startActivity(intent);
			}
		});
2. In Thread class, add to onCreate function:

		Intent intent = getIntent();
		String threadId = intent.getStringExtra(Thread.threadId);
		TextView threadTitle = (TextView) findViewById(R.id.textView);
		getSupportActionBar().setTitle("Thread");
		threadTitle.setText("Mock thread title");

### New Thread view
1. In res > values > strings, add `<string name="action_new_thread">New Thread</string>`
2. In res > menu > menu_main.xml, add `<item android:id="@+id/action_new_thread" android:title="@string/action_new_thread" android:orderInCategory="100" app:showAsAction="never" />`
3. In MainActivity class, modify `onOptionsItemSelected` to this:

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			if (id == R.id.action_new_thread) {
					return true;
			}
			return super.onOptionsItemSelected(item);
		}

## Integrating Parse (Prebuilt backend)

Source: https://parse.com/apps/quickstart#parse_data/mobile/android/native/existing

1. Download parse SDK from https://parse.com/apps/quickstart#parse_data/mobile/android/native/existing
2. Drag the Parse-*.jar you downloaded into your existing app's "libs" folder and add the following to your build.gradle
3. Add to app module build.gradle: 

		dependencies {
			compile 'com.parse.bolts:bolts-android:1.+'
			compile fileTree(dir: 'libs', include: 'Parse-*.jar')
		}
4. Add to onCreate method in mainActivity:

		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "dbSRgndFtVlkxJUG1AcubhVaP814yfO0HAqrEjaU", "LWqPDlBCoLfhG2bG7ZvjKdNpvcbl8JARkLHcaoWf");
		populateMessages();

5. populateMessages method:

		public void populateMessages() {
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Thread");
			query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> messages, ParseException e) {
					for (ParseObject message: messages) {
						addItem(message.getString("title"));
					}
				}
			});
		}
6. Add to manifest file:
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

This method is NOT good it makes parse avaliable to only one activity. For any serious app building, MVC is needed.

## Next steps

Switching default listviews for custom listviews

Putting parse on a service (MVC)

Extra features

## Switching default listviews for custom listviews

The default listviews in android are not customizable. For any serious app, it is important to implement custom listviews.

#### Step 1
Add RecyclerView library to app gradle
	compile 'com.android.support:recyclerview-v7:+'

#### Step 2
Define your data class:
```java
class Post {
	private String mTitle, mContent;

	public Post(String title, String content) {
		mTitle = title;
		mContent = content;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}
}
```

Initialize your data in your activity:
```java
	protected void onCreate(Bundle savedInstanceState) {
		...

		ArrayList<Post> posts = new ArrayList<>();
		posts.add(new Post("some title", "some content"));
		posts.add(new Post("some title 2", "some content 2"));
		posts.add(new Post("some title 3", "some content 3"));
		posts.add(new Post("some title 4", "some content 4"));
	}
```

Define your item view (`layouts/post.xml`):
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
							xmlns:tools="http://schemas.android.com/tools"
							android:layout_width="match_parent"
							android:layout_height="match_parent"
							android:layout_marginBottom="8dp"
							android:layout_marginTop="8dp"
							android:orientation="vertical">

	<TextView
		android:id="@+id/title"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:textSize="20sp"
		tools:text="Title"/>

	<TextView
		android:id="@+id/content"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		tools:text="Content"/>
</LinearLayout>
```

#### Step 3
Create an adapter:
```java
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

	private List<Post> mPosts;

	public PostListAdapter(List<Post> posts) {
		mPosts = posts;
	}

	@Override
	public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
		PostViewHolder viewHolder = new PostViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(PostViewHolder holder, int position) {
		Post post = mPosts.get(position);
		holder.bind(post);
	}

	@Override
	public int getItemCount() {
		return mPosts.size();
	}

	public class PostViewHolder extends RecyclerView.ViewHolder {
		private TextView mTitle, mContent;
		private Post mPost;

		public PostViewHolder(final View itemView) {
				super(itemView);
				mTitle = (TextView) itemView.findViewById(R.id.title);
				mContent = (TextView) itemView.findViewById(R.id.content);
				itemView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								Toast.makeText(itemView.getContext(), "You clicked '" + mPost.getTitle() + "'", Toast.LENGTH_SHORT).show();
						}
				});
		}

		public void bind(Post post) {
				mPost = post;
				mTitle.setText(post.getTitle());
				mContent.setText(post.getContent());
		}
	}
}
```

#### Step 4
Add RecyclerView to layout
```xml
<android.support.v7.widget.RecyclerView
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:id="@+id/recycler_view"/>
```

Set it up in your activity
```java
	onCreate() {
		...
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(new PostListAdapter(posts));
	}
```

####	Sample Project
```
By Daniel Rampelt
https://drive.google.com/file/d/0BzW6GHHHAXgIS2tydEJNX20xZzA/view?usp=sharing
```
