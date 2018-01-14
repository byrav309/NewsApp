package com.exmample.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsDetails>>, CustomItemClickListener {

    private NewsAdapter newsAdapter;
    private TextView syncErrorTextView;
    private boolean isSyncInProgress;
    private final int LOADER_ID = 100;

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
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
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
                    getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public Loader<List<NewsDetails>> onCreateLoader(int id, Bundle args) {
        isSyncInProgress = true;
        return new NewsAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsDetails>> loader, List<NewsDetails> data) {
        isSyncInProgress = false;
        if (data.size() <= 0) {
            showToastMessage(R.string.sync_error);
            syncErrorTextView.setVisibility(View.VISIBLE);
            return;
        }
        showToastMessage(R.string.sync_complete);
        syncErrorTextView.setVisibility(View.GONE);
        newsAdapter.setItems(data);
    }

    private void showToastMessage(int resourceId) {
        Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<List<NewsDetails>> loader) {

    }

    @Override
    public void onItemClick(NewsDetails newsDetails) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(newsDetails.getWebUrl()));
        startActivity(intent);
    }
}
