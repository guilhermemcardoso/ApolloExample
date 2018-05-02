package cardoso.guilherme.graphql;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import javax.annotation.Nonnull;

import cardoso.guilherme.graphql.api.GetFeedQuery;

public class MainActivity extends AppCompatActivity {

    public final static String BASE_URL = "http://172.16.0.83:3000/graphql";
    private ApolloClient apolloClient;

    FloatingActionButton addFab;
    RecyclerView feedRecyclerView;
    FeedAdapter feedAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFab = findViewById(R.id.addFab);
        feedRecyclerView = findViewById(R.id.feed_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        feedRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        feedRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        feedAdapter = new FeedAdapter(this);
        feedRecyclerView.setAdapter(feedAdapter);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        apolloClient = ApolloClient
                .builder()
                .serverUrl(BASE_URL)
                .build();

        apolloClient.query(GetFeedQuery.builder().build()).enqueue(new ApolloCall.Callback<GetFeedQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<GetFeedQuery.Data> response) {
                Log.d("FEEDQUERY SUCCESS", response.data().toString());
                updateList(response.data().feed());
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.d("FEEDQUERY FAILED", e.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //apolloClient.subscribe()
    }

    private void updateList(final List<GetFeedQuery.Feed> feed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                feedAdapter.updateList(feed);
            }
        });

    }
}
