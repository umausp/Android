package com.duckduckgo.mobile.android.duckduckgo.ui.bookmarks;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by fgei on 6/12/17.
 */

public interface BookmarksPresenter {
    void attachView(BookmarksView bookmarksView);

    void detachView();

    void load();

    void edit();

    void dismiss();

    void bookmarkSelected(int position);

    void bookmarkDeleted(int position);

    void bookmarksMoved(int fromPosition, int toPosition);

    void saveEditedBookmark(@NonNull BookmarkModel bookmarkModel);
}
