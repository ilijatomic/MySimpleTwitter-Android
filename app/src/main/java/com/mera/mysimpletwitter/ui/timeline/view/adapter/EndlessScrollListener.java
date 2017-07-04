package com.mera.mysimpletwitter.ui.timeline.view.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Util class for lazy loading implementation
 * Triggering event when user reaches end of list
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    // The minimum number of items to have below your current scroll position
    // before mIsLoading more.
    private int mVisibleThreshold = 10;
    // The total number of items in the dataset after the last load
    private int mPreviousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean mIsLoading = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < mPreviousTotalItemCount) {
            this.mPreviousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.mIsLoading = true;
            }
        }
        // If it's still mIsLoading, we check to see if the dataset count has
        // changed, if so we conclude it has finished mIsLoading and update the current page
        // number and total item count.
        if (mIsLoading && (totalItemCount > mPreviousTotalItemCount)) {
            mIsLoading = false;
            mPreviousTotalItemCount = totalItemCount;
        }

        // If it isn't currently mIsLoading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!mIsLoading && (firstVisibleItem + visibleItemCount + mVisibleThreshold) >= totalItemCount) {
            mIsLoading = true;
            onLoadMore();
        }
    }

    /**
     * Loading more tweets when user reaches end of list
     */
    public abstract void onLoadMore();

}
