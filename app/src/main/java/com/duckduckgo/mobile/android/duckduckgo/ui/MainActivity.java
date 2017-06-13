package com.duckduckgo.mobile.android.duckduckgo.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.duckduckgo.mobile.android.duckduckgo.Injector;
import com.duckduckgo.mobile.android.duckduckgo.R;
import com.duckduckgo.mobile.android.duckduckgo.ui.bookmarks.BookmarkModel;
import com.duckduckgo.mobile.android.duckduckgo.ui.browser.BrowserFragment;
import com.duckduckgo.mobile.android.duckduckgo.ui.browser.BrowserPresenter;
import com.duckduckgo.mobile.android.duckduckgo.ui.editbookmark.EditBookmarkDialogFragment;

public class MainActivity extends AppCompatActivity implements EditBookmarkDialogFragment.OnEditBookmarkListener {

    private static final int ACTIVITY_CONTAINER = android.R.id.content;

    private BrowserPresenter browserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browserPresenter = Injector.injectBrowserPresenter();

        BrowserFragment browserFragment = (BrowserFragment) getSupportFragmentManager().findFragmentByTag(BrowserFragment.TAG);
        if (browserFragment == null) browserFragment = BrowserFragment.newInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(ACTIVITY_CONTAINER, browserFragment, BrowserFragment.TAG)
                    .commit();
        }

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onBackPressed() {
        if (browserPresenter.handleBackHistory()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onBookmarkEdited(BookmarkModel bookmark) {
        browserPresenter.saveBookmark(bookmark);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case Intent.ACTION_VIEW:
                handleActionView(intent);
                break;
            case Intent.ACTION_WEB_SEARCH:
                handleActionWebSearch(intent);
                break;
            case Intent.ACTION_ASSIST:
                handleActionAssist();
                break;
        }
    }

    private void handleActionView(Intent intent) {
        String url = intent.getDataString();
        browserPresenter.requestSearch(url);
    }

    private void handleActionWebSearch(Intent intent) {
        String query = intent.getStringExtra(SearchManager.QUERY);
        browserPresenter.requestSearch(query);
    }

    private void handleActionAssist() {
        browserPresenter.requestAssist();
    }
}
