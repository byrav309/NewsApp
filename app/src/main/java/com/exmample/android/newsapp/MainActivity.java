package com.exmample.android.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsFetchResultCallBack {

    private NewsAdapter newsAdapter;
    private TextView syncErrorTextView;
    private boolean isSyncInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView recyclerView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        syncErrorTextView = (TextView) findViewById(R.id.sync_error_tv);
        newsAdapter = new NewsAdapter(new ArrayList<NewsDetails>(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
        syncNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sync:
                if (!isSyncInProgress)
                    syncNews();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void syncNews() {
        isSyncInProgress = true;
        showToastMessage(R.string.sync_started);
        new NewsFetchTask(this).execute();
    }

    private void showToastMessage(int resourceId) {
        Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResult(List<NewsDetails> newsItems) {
        isSyncInProgress = false;
        if (newsItems.size() <= 0) {
            showToastMessage(R.string.sync_error);
            syncErrorTextView.setVisibility(View.VISIBLE);
            return;
        }
        showToastMessage(R.string.sync_complete);
        syncErrorTextView.setVisibility(View.GONE);
        newsAdapter.setItems(newsItems);
    }
}
